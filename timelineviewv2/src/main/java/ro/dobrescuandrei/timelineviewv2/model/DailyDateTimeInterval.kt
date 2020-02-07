package ro.dobrescuandrei.timelineviewv2.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import org.joda.time.DateTime
import org.joda.time.Days
import ro.dobrescuandrei.timelineviewv2.R
import ro.dobrescuandrei.timelineviewv2.TimelineViewDefaults
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.DailyDateTimeIntervalAdapter
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
        DailyDateTimeInterval(referenceDateTime = fromDateTime.minusDays(1))

    override fun getNextDateTimeInterval() =
        DailyDateTimeInterval(referenceDateTime = fromDateTime.plusDays(1))

    override fun getShiftedDateTimeInterval(amount : Int) =
        DailyDateTimeInterval(referenceDateTime = fromDateTime.plusDays(amount))

    override fun minus(another : DateTimeInterval<*>) =
        Days.daysBetween(fromDateTime.toLocalDate(), another.fromDateTime.toLocalDate()).days

    @SuppressLint("SimpleDateFormat")
    override fun toString(resources : Resources) : String
    {
        val now=DateTime.now(TimelineViewDefaults.timezone)!!

        if (fromDateTime.year==now.year&&fromDateTime.monthOfYear==now.monthOfYear)
        {
            if (fromDateTime.dayOfMonth==now.dayOfMonth-1)
                return resources.getString(R.string.yesterday)
            if (fromDateTime.dayOfMonth==now.dayOfMonth)
                return resources.getString(R.string.today)
            if (fromDateTime.dayOfMonth==now.dayOfMonth+1)
                return resources.getString(R.string.tomorrow)
        }

        val dateFormatter=
            if (fromDateTime.year!=now.year)
                SimpleDateFormat("dd MMM yyyy")
            else SimpleDateFormat("dd MMM")
        dateFormatter.timeZone=TimelineViewDefaults.timezone.toTimeZone()!!
        return dateFormatter.formatJodaDateTime(fromDateTime)
    }

    override fun toRecyclerViewAdapter(context : Context) =
        DailyDateTimeIntervalAdapter(context)
}
