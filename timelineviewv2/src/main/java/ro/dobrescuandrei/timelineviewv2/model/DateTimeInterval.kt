package ro.dobrescuandrei.timelineviewv2.model

import android.content.Context
import android.content.res.Resources
import org.joda.time.DateTime
import ro.dobrescuandrei.timelineviewv2.base.BaseTimelineRecyclerViewAdapter

abstract class DateTimeInterval<SELF>
(
    val fromDateTime : DateTime,
    val toDateTime : DateTime
)
{
    abstract fun getPreviousDateTimeInterval() : SELF?
    abstract fun getNextDateTimeInterval() : SELF?
    abstract fun getShiftedDateTimeInterval(amount : Int) : SELF?

    abstract operator fun minus(another : DateTimeInterval<*>) : Int

    fun contains(dateTime : DateTime) = dateTime in fromDateTime..toDateTime

    abstract fun toRecyclerViewAdapter(context : Context) : BaseTimelineRecyclerViewAdapter<*>

    override fun toString() = "${this::class.java.simpleName} $fromDateTime - $toDateTime"
    abstract fun toString(resources : Resources) : String

    override fun equals(other : Any?) =
        (other as? DateTimeInterval<*>)?.fromDateTime==fromDateTime&&
        (other as? DateTimeInterval<*>)?.toDateTime==toDateTime

    override fun hashCode() = fromDateTime.millis.toInt()
}
