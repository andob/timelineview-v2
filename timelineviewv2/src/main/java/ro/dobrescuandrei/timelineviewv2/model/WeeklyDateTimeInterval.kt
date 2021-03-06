package ro.dobrescuandrei.timelineviewv2.model

import android.annotation.SuppressLint
import android.content.res.Resources
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.TimelineViewDefaults
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.WeeklyDateIntervalAdapter
import ro.dobrescuandrei.timelineviewv2.utils.atBeginningOfDay
import ro.dobrescuandrei.timelineviewv2.utils.atEndOfDay

class WeeklyDateTimeInterval
(
    referenceDateTime : DateTime
) : DateTimeInterval
(
    fromDateTime = referenceDateTime
        .dayOfWeek().withMinimumValue()
        .atBeginningOfDay(),

    toDateTime = referenceDateTime
        .dayOfWeek().withMaximumValue()
        .atEndOfDay()
)
{
    companion object
    {
        @JvmStatic
        fun aroundToday() = WeeklyDateTimeInterval(
            referenceDateTime = DateTime.now(TimelineViewDefaults.timezone))
    }

    override fun getPreviousDateTimeInterval() =
        WeeklyDateTimeInterval(referenceDateTime = fromDateTime.minusWeeks(1))

    override fun getNextDateTimeInterval() =
        WeeklyDateTimeInterval(referenceDateTime = fromDateTime.plusWeeks(1))

    override fun getShiftedDateTimeInterval(amount : Int) =
        WeeklyDateTimeInterval(referenceDateTime = fromDateTime.plusWeeks(amount))

    @SuppressLint("SimpleDateFormat")
    override fun toString(resources : Resources) : String
    {
        val now=DateTime.now(TimelineViewDefaults.timezone)!!

        val startDateTimeFormatter=
            if (fromDateTime.monthOfYear!=toDateTime.monthOfYear)
                DateTimeFormat.forPattern("dd MMM")!!
            else DateTimeFormat.forPattern("dd")!!
        val startDateStr=startDateTimeFormatter.print(fromDateTime)

        val endDateTimeFormatter=
            if (toDateTime.year!=now.year)
                DateTimeFormat.forPattern("dd MMM yyyy")!!
            else DateTimeFormat.forPattern("dd MMM")!!
        val endDateStr=endDateTimeFormatter.print(toDateTime)

        return "$startDateStr - $endDateStr"
    }

    override fun toRecyclerViewAdapter(timelineView : TimelineView) =
        WeeklyDateIntervalAdapter(context = timelineView.context, timelineView = timelineView)

    override fun clone() = WeeklyDateTimeInterval(referenceDateTime = fromDateTime)
}
