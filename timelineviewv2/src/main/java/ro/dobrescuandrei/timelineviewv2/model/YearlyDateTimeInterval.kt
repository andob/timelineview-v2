package ro.dobrescuandrei.timelineviewv2.model

import android.content.res.Resources
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.YearlyDateIntervalAdapter
import ro.dobrescuandrei.timelineviewv2.utils.atBeginningOfDay
import ro.dobrescuandrei.timelineviewv2.utils.atEndOfDay
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.temporal.TemporalAdjusters

class YearlyDateTimeInterval : DateTimeInterval
{
    constructor(referenceDateTime : ZonedDateTime) : super(
        fromDateTime = referenceDateTime.with(TemporalAdjusters.firstDayOfYear()).atBeginningOfDay(),
        toDateTime = referenceDateTime.with(TemporalAdjusters.lastDayOfYear()).atEndOfDay())

    constructor(referenceDateTime : LocalDateTime) : this(
        referenceDateTime = referenceDateTime.atZone(defaultTimezone))

    companion object
    {
        @JvmStatic
        fun aroundToday() = YearlyDateTimeInterval(
            referenceDateTime = ZonedDateTime.now(defaultTimezone))
    }

    override fun getPreviousDateTimeInterval() =
        YearlyDateTimeInterval(referenceDateTime = fromDateTime.minusYears(1))

    override fun getNextDateTimeInterval() =
        YearlyDateTimeInterval(referenceDateTime = fromDateTime.plusYears(1))

    override fun getShiftedDateTimeInterval(amount : Long) =
        YearlyDateTimeInterval(referenceDateTime = fromDateTime.plusYears(amount))

    override fun toString(resources : Resources) =
        fromDateTime.year.toString()

    override fun toRecyclerViewAdapter(timelineView : TimelineView) =
        YearlyDateIntervalAdapter(context = timelineView.context, timelineView = timelineView)

    override fun clone() = YearlyDateTimeInterval(referenceDateTime = fromDateTime)
}
