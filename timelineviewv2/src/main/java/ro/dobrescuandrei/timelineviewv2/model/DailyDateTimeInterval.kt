package ro.dobrescuandrei.timelineviewv2.model

import android.annotation.SuppressLint
import android.content.res.Resources
import org.joda.time.DateTime
import ro.dobrescuandrei.timelineviewv2.TimelineViewDefaults
import ro.dobrescuandrei.timelineviewv2.utils.atBeginningOfDay
import ro.dobrescuandrei.timelineviewv2.utils.atEndOfDay
import ro.dobrescuandrei.timelineviewv2.utils.formatJodaDateTime
import java.text.SimpleDateFormat

class DailyDateTimeInterval
(
    val referenceDateTime : DateTime = DateTime.now(TimelineViewDefaults.timezone)
) : DateTimeInterval<DailyDateTimeInterval>
(
    fromDateTime = referenceDateTime.atBeginningOfDay(),
    toDateTime = referenceDateTime.atEndOfDay()
)
{
    override fun getPreviousDateTimeInterval() =
        DailyDateTimeInterval(referenceDateTime = fromDateTime.withDayOfMonth(fromDateTime.dayOfMonth-1))

    override fun getNextDateTimeInterval() =
        DailyDateTimeInterval(referenceDateTime = fromDateTime.withDayOfMonth(fromDateTime.monthOfYear+1))

    @SuppressLint("SimpleDateFormat")
    override fun toString(resources : Resources) : String
    {
        val now=DateTime.now(TimelineViewDefaults.timezone)!!

        val dateFormatter=
            if (fromDateTime.year!=now.year)
                SimpleDateFormat("dd MMM yyyy")
            else SimpleDateFormat("dd MMM")
        dateFormatter.timeZone=TimelineViewDefaults.timezone.toTimeZone()!!
        return dateFormatter.formatJodaDateTime(fromDateTime)
    }
}
