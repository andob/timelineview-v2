package ro.dobrescuandrei.timelineviewv2.model

import android.annotation.SuppressLint
import android.content.res.Resources
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.TimelineViewDefaults
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.MonthlyDateTimeIntervalAdapter
import ro.dobrescuandrei.timelineviewv2.utils.atBeginningOfDay
import ro.dobrescuandrei.timelineviewv2.utils.atEndOfDay

class MonthlyDateTimeInterval
(
    referenceDateTime : DateTime
) : DateTimeInterval
(
    fromDateTime = referenceDateTime
        .dayOfMonth().withMinimumValue()
        .atBeginningOfDay(),

    toDateTime = referenceDateTime
        .dayOfMonth().withMaximumValue()
        .atEndOfDay()
)
{
    companion object
    {
        @JvmStatic
        fun aroundToday() = MonthlyDateTimeInterval(
            referenceDateTime = DateTime.now(TimelineViewDefaults.timezone))
    }

    override fun getPreviousDateTimeInterval() =
        MonthlyDateTimeInterval(referenceDateTime = fromDateTime.minusMonths(1))

    override fun getNextDateTimeInterval() =
        MonthlyDateTimeInterval(referenceDateTime = fromDateTime.plusMonths(1))

    override fun getShiftedDateTimeInterval(amount : Int) =
        MonthlyDateTimeInterval(referenceDateTime = fromDateTime.plusMonths(amount))

    @SuppressLint("SimpleDateFormat")
    override fun toString(resources : Resources) : String
    {
        val now=DateTime.now(TimelineViewDefaults.timezone)!!

        val dateFormatter=
            if (fromDateTime.year!=now.year)
                DateTimeFormat.forPattern("MMM yyyy")!!
            else DateTimeFormat.forPattern("MMM")!!
        return dateFormatter.print(fromDateTime)
    }

    override fun toRecyclerViewAdapter(timelineView : TimelineView) =
        MonthlyDateTimeIntervalAdapter(context = timelineView.context, timelineView = timelineView)

    override fun clone() = MonthlyDateTimeInterval(referenceDateTime = fromDateTime)
}
