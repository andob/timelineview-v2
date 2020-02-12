package ro.dobrescuandrei.timelineviewv2.model

import android.content.res.Resources
import org.joda.time.DateTime
import org.joda.time.Years
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.TimelineViewDefaults
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.YearlyDateIntervalAdapter
import ro.dobrescuandrei.timelineviewv2.utils.atBeginningOfDay
import ro.dobrescuandrei.timelineviewv2.utils.atEndOfDay

class YearlyDateTimeInterval
(
    referenceDateTime : DateTime
) : DateTimeInterval
(
    fromDateTime = referenceDateTime
        .dayOfYear().withMinimumValue()
        .atBeginningOfDay(),

    toDateTime = referenceDateTime
        .dayOfYear().withMaximumValue()
        .atEndOfDay()
)
{
    companion object
    {
        @JvmStatic
        fun aroundToday() = YearlyDateTimeInterval(
            referenceDateTime = DateTime.now(TimelineViewDefaults.timezone))
    }

    override fun getPreviousDateTimeInterval() =
        YearlyDateTimeInterval(referenceDateTime = fromDateTime.minusYears(1))

    override fun getNextDateTimeInterval() =
        YearlyDateTimeInterval(referenceDateTime = fromDateTime.plusYears(1))

    override fun getShiftedDateTimeInterval(amount : Int) =
        YearlyDateTimeInterval(referenceDateTime = fromDateTime.plusYears(amount))

    override fun minus(another : DateTimeInterval) =
        Years.yearsBetween(fromDateTime.toLocalDate(), another.fromDateTime.toLocalDate()).years

    override fun toString(resources : Resources) =
        fromDateTime.year.toString()

    override fun toRecyclerViewAdapter(timelineView : TimelineView) =
        YearlyDateIntervalAdapter(context = timelineView.context, timelineView = timelineView)
}
