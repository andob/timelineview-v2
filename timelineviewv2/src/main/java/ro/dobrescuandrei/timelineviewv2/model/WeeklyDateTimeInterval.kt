package ro.dobrescuandrei.timelineviewv2.model

import android.annotation.SuppressLint
import android.content.res.Resources
import org.joda.time.DateTime
import ro.dobrescuandrei.timelineviewv2.TimelineViewDefaults
import ro.dobrescuandrei.timelineviewv2.utils.atBeginningOfDay
import ro.dobrescuandrei.timelineviewv2.utils.atEndOfDay
import ro.dobrescuandrei.timelineviewv2.utils.formatJodaDateTime
import java.text.SimpleDateFormat

class WeeklyDateTimeInterval
(
    val referenceDateTime : DateTime = DateTime.now(TimelineViewDefaults.timezone)
) : DateTimeInterval<WeeklyDateTimeInterval>
(
    fromDateTime = referenceDateTime
        .dayOfWeek().withMinimumValue()
        .atBeginningOfDay(),

    toDateTime = referenceDateTime
        .dayOfWeek().withMaximumValue()
        .atEndOfDay()
)
{
    override fun getPreviousDateTimeInterval() =
        WeeklyDateTimeInterval(referenceDateTime = fromDateTime.withDayOfMonth(fromDateTime.dayOfMonth-7))

    override fun getNextDateTimeInterval() =
        WeeklyDateTimeInterval(referenceDateTime = fromDateTime.withDayOfMonth(fromDateTime.monthOfYear+7))

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
}
