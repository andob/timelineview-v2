package ro.dobrescuandrei.timelineviewv2.model

import android.content.res.Resources
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.base.BaseTimelineRecyclerViewAdapter
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

abstract class DateTimeInterval
(
    val fromDateTime : ZonedDateTime,
    val toDateTime : ZonedDateTime,
) : Serializable
{
    constructor(fromDateTime : LocalDateTime, toDateTime : LocalDateTime) : this(
        fromDateTime = fromDateTime.atZone(defaultTimezone),
        toDateTime = toDateTime.atZone(defaultTimezone))

    companion object
    {
        @JvmStatic
        var defaultTimezone : ZoneId = ZoneOffset.UTC!!
    }

    abstract fun getPreviousDateTimeInterval() : DateTimeInterval?
    abstract fun getNextDateTimeInterval() : DateTimeInterval?
    abstract fun getShiftedDateTimeInterval(amount : Long) : DateTimeInterval?

    fun contains(dateTime : ZonedDateTime) = dateTime in fromDateTime..toDateTime

    abstract fun toRecyclerViewAdapter(timelineView : TimelineView) : BaseTimelineRecyclerViewAdapter<*>

    override fun toString() = "${this::class.java.simpleName} $fromDateTime - $toDateTime"
    abstract fun toString(resources : Resources) : String

    override fun equals(other : Any?) =
        other != null && other is DateTimeInterval &&
        other::class.java == this::class.java &&
        other.fromDateTime == this.fromDateTime &&
        other.toDateTime == this.toDateTime

    override fun hashCode() = fromDateTime.toInstant()!!.toEpochMilli().toInt()

    abstract fun clone() : DateTimeInterval
}
