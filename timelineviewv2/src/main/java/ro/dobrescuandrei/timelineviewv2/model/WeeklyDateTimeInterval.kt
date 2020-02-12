package ro.dobrescuandrei.timelineviewv2.model

import android.annotation.SuppressLint
import android.content.res.Resources
import org.joda.time.DateTime
import org.joda.time.Months
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.TimelineViewDefaults
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.WeeklyDateIntervalAdapter
import ro.dobrescuandrei.timelineviewv2.utils.atBeginningOfDay
import ro.dobrescuandrei.timelineviewv2.utils.atEndOfDay
import ro.dobrescuandrei.timelineviewv2.utils.formatJodaDateTime
import java.text.SimpleDateFormat

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

    override fun minus(another : DateTimeInterval) =
        Months.monthsBetween(fromDateTime.toLocalDate(), another.fromDateTime.toLocalDate()).months

    @SuppressLint("SimpleDateFormat")
    override fun toString(resources : Resources) : String
    {
        val now=DateTime.now(TimelineViewDefaults.timezone)!!

        val startDateTimeFormatter=
            if (fromDateTime.monthOfYear!=now.monthOfYear)
                SimpleDateFormat("dd MMM")
            else SimpleDateFormat("dd")
        startDateTimeFormatter.timeZone=TimelineViewDefaults.timezone.toTimeZone()!!
        val startDateStr=startDateTimeFormatter.formatJodaDateTime(fromDateTime)

        val endDateTimeFormatter=
            if (toDateTime.year!=now.year)
                SimpleDateFormat("dd MMM yyyy")
            else SimpleDateFormat("dd MMM")
        startDateTimeFormatter.timeZone=TimelineViewDefaults.timezone.toTimeZone()!!
        val endDateStr=endDateTimeFormatter.formatJodaDateTime(toDateTime)

        return "$startDateStr - $endDateStr"
    }

    override fun toRecyclerViewAdapter(timelineView : TimelineView) =
        WeeklyDateIntervalAdapter(context = timelineView.context, timelineView = timelineView)
}
