package ro.dobrescuandrei.timelineviewv2.model

import android.content.res.Resources
import org.joda.time.DateTime
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.base.BaseTimelineRecyclerViewAdapter
import java.io.Serializable

abstract class DateTimeInterval
(
    val fromDateTime : DateTime,
    val toDateTime : DateTime
) : Serializable
{
    abstract fun getPreviousDateTimeInterval() : DateTimeInterval?
    abstract fun getNextDateTimeInterval() : DateTimeInterval?
    abstract fun getShiftedDateTimeInterval(amount : Int) : DateTimeInterval?

    fun contains(dateTime : DateTime) = dateTime in fromDateTime..toDateTime

    abstract fun toRecyclerViewAdapter(timelineView : TimelineView) : BaseTimelineRecyclerViewAdapter<*>

    override fun toString() = "${this::class.java.simpleName} $fromDateTime - $toDateTime"
    abstract fun toString(resources : Resources) : String

    override fun equals(other : Any?) =
        other!=null&&other is DateTimeInterval&&
        other::class.java==this::class.java&&
        other.fromDateTime==this.fromDateTime&&
        other.toDateTime==this.toDateTime

    override fun hashCode() = fromDateTime.millis.toInt()
}
