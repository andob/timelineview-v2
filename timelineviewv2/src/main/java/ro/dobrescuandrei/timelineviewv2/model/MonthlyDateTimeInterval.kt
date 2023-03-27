package ro.dobrescuandrei.timelineviewv2.model

import android.annotation.SuppressLint
import android.content.res.Resources
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.MonthlyDateTimeIntervalAdapter
import ro.dobrescuandrei.timelineviewv2.utils.atBeginningOfDay
import ro.dobrescuandrei.timelineviewv2.utils.atEndOfDay
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

class MonthlyDateTimeInterval : DateTimeInterval
{
    constructor(referenceDateTime : ZonedDateTime) : super(
        fromDateTime = referenceDateTime.with(TemporalAdjusters.firstDayOfMonth()).atBeginningOfDay(),
        toDateTime = referenceDateTime.with(TemporalAdjusters.lastDayOfMonth()).atEndOfDay())

    constructor(referenceDateTime : LocalDateTime) : this(
        referenceDateTime = referenceDateTime.atZone(defaultTimezone))

    companion object
    {
        @JvmStatic
        fun aroundToday() = MonthlyDateTimeInterval(
            referenceDateTime = ZonedDateTime.now(defaultTimezone))
    }

    override fun getPreviousDateTimeInterval() =
        MonthlyDateTimeInterval(referenceDateTime = fromDateTime.minusMonths(1))

    override fun getNextDateTimeInterval() =
        MonthlyDateTimeInterval(referenceDateTime = fromDateTime.plusMonths(1))

    override fun getShiftedDateTimeInterval(amount : Long) =
        MonthlyDateTimeInterval(referenceDateTime = fromDateTime.plusMonths(amount))

    @SuppressLint("SimpleDateFormat")
    override fun toString(resources : Resources) : String
    {
        val now=ZonedDateTime.now(defaultTimezone)!!

        val dateFormatter=
            if (fromDateTime.year!=now.year)
                DateTimeFormatter.ofPattern("MMM yyyy")!!
            else DateTimeFormatter.ofPattern("MMM")!!
        return dateFormatter.format(fromDateTime)!!
    }

    override fun toRecyclerViewAdapter(timelineView : TimelineView) =
        MonthlyDateTimeIntervalAdapter(context = timelineView.context, timelineView = timelineView)

    override fun clone() = MonthlyDateTimeInterval(referenceDateTime = fromDateTime)
}
