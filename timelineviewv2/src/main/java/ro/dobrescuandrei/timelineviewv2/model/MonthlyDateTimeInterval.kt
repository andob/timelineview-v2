package ro.dobrescuandrei.timelineviewv2.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import org.joda.time.DateTime
import org.joda.time.Months
import ro.dobrescuandrei.timelineviewv2.TimelineViewAppearance
import ro.dobrescuandrei.timelineviewv2.TimelineViewDefaults
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.MonthlyDateTimeIntervalAdapter
import ro.dobrescuandrei.timelineviewv2.utils.atBeginningOfDay
import ro.dobrescuandrei.timelineviewv2.utils.atEndOfDay
import ro.dobrescuandrei.timelineviewv2.utils.formatJodaDateTime
import java.text.SimpleDateFormat

class MonthlyDateTimeInterval
(
    val referenceDateTime : DateTime
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
    companion object
    {
        @JvmStatic
        fun aroundToday() = MonthlyDateTimeInterval(
            referenceDateTime = DateTime.now(TimelineViewDefaults.timezone))
    }

    override fun getPreviousDateTimeInterval() =
        MonthlyDateTimeInterval(referenceDateTime = fromDateTime.minusYears(1))

    override fun getNextDateTimeInterval() =
        MonthlyDateTimeInterval(referenceDateTime = fromDateTime.plusYears(1))

    override fun getShiftedDateTimeInterval(amount : Int) =
        MonthlyDateTimeInterval(referenceDateTime = fromDateTime.plusYears(amount))

    override fun minus(another : DateTimeInterval<*>) =
        Months.monthsBetween(fromDateTime.toLocalDate(), another.fromDateTime.toLocalDate()).months

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

    override fun toRecyclerViewAdapter(context : Context, appearance : TimelineViewAppearance) =
        MonthlyDateTimeIntervalAdapter(context, appearance)
}
