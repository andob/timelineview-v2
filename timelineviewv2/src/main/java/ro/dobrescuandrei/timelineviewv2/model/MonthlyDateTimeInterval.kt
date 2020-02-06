package ro.dobrescuandrei.timelineviewv2.model

import android.annotation.SuppressLint
import android.content.res.Resources
import org.joda.time.DateTime
import ro.dobrescuandrei.timelineviewv2.TimelineViewDefaults
import ro.dobrescuandrei.timelineviewv2.utils.atBeginningOfDay
import ro.dobrescuandrei.timelineviewv2.utils.atEndOfDay
import ro.dobrescuandrei.timelineviewv2.utils.formatJodaDateTime
import java.text.SimpleDateFormat

class MonthlyDateTimeInterval
(
    val referenceDateTime : DateTime = DateTime.now(TimelineViewDefaults.timezone)
) : DateTimeInterval<MonthlyDateTimeInterval>
(
    fromDateTime = referenceDateTime
        .dayOfMonth().withMinimumValue()
        .atBeginningOfDay(),

    toDateTime = referenceDateTime
        .dayOfMonth().withMaximumValue()
        .atEndOfDay()
)
{
    override fun getPreviousDateTimeInterval() =
        MonthlyDateTimeInterval(referenceDateTime = fromDateTime.withMonthOfYear(fromDateTime.monthOfYear-1))

    override fun getNextDateTimeInterval() =
        MonthlyDateTimeInterval(referenceDateTime = fromDateTime.withMonthOfYear(fromDateTime.monthOfYear+1))

    @SuppressLint("SimpleDateFormat")
    override fun toString(resources : Resources) : String
    {
        val now=DateTime.now(TimelineViewDefaults.timezone)!!

        val dateFormatter=
            if (fromDateTime.year!=now.year)
                SimpleDateFormat("MMM yyyy")
            else SimpleDateFormat("MMM")
        dateFormatter.timeZone=TimelineViewDefaults.timezone.toTimeZone()!!
        return dateFormatter.formatJodaDateTime(fromDateTime)
    }
}
