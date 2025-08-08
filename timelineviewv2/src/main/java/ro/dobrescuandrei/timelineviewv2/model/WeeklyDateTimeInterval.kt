package ro.dobrescuandrei.timelineviewv2.model

import android.annotation.SuppressLint
import android.content.res.Resources
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.WeeklyDateIntervalAdapter
import ro.dobrescuandrei.timelineviewv2.utils.atBeginningOfDay
import ro.dobrescuandrei.timelineviewv2.utils.atEndOfDay
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class WeeklyDateTimeInterval : DateTimeInterval
{
    constructor(referenceDateTime : ZonedDateTime) : super(
        fromDateTime = referenceDateTime.with(DayOfWeek.MONDAY).atBeginningOfDay(),
        toDateTime = referenceDateTime.with(DayOfWeek.SUNDAY).atEndOfDay())

    constructor(referenceDateTime : LocalDateTime) : this(
        referenceDateTime = referenceDateTime.atZone(defaultTimezone))

    companion object
    {
        @JvmStatic
        fun aroundToday() = WeeklyDateTimeInterval(
            referenceDateTime = ZonedDateTime.now(defaultTimezone))
    }

    override fun getPreviousDateTimeInterval() =
        WeeklyDateTimeInterval(referenceDateTime = fromDateTime.minusWeeks(1))

    override fun getNextDateTimeInterval() =
        WeeklyDateTimeInterval(referenceDateTime = fromDateTime.plusWeeks(1))

    override fun getShiftedDateTimeInterval(amount : Long) =
        WeeklyDateTimeInterval(referenceDateTime = fromDateTime.plusWeeks(amount))

    @SuppressLint("SimpleDateFormat")
    override fun toString(resources : Resources) : String
    {
        val now = ZonedDateTime.now(fromDateTime.zone)!!

        val startDateTimeFormatter = 
            if (fromDateTime.monthValue != toDateTime.monthValue)
                DateTimeFormatter.ofPattern("dd MMM")!!
            else DateTimeFormatter.ofPattern("dd")!!
        val startDateStr = startDateTimeFormatter.format(fromDateTime)!!

        val endDateTimeFormatter = 
            if (toDateTime.year != now.year)
                DateTimeFormatter.ofPattern("dd MMM yyyy")!!
            else DateTimeFormatter.ofPattern("dd MMM")!!
        val endDateStr = endDateTimeFormatter.format(toDateTime)!!

        return "$startDateStr - $endDateStr"
    }

    override fun toRecyclerViewAdapter(timelineView : TimelineView) =
        WeeklyDateIntervalAdapter(context = timelineView.context, timelineView = timelineView)

    override fun clone() = WeeklyDateTimeInterval(referenceDateTime = fromDateTime)
}
