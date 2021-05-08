package ro.dobrescuandrei.timelineviewv2.model

import android.annotation.SuppressLint
import android.content.res.Resources
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import ro.dobrescuandrei.timelineviewv2.R
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.TimelineViewDefaults
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.DailyDateTimeIntervalAdapter
import ro.dobrescuandrei.timelineviewv2.utils.atBeginningOfDay
import ro.dobrescuandrei.timelineviewv2.utils.atEndOfDay

class DailyDateTimeInterval
(
    referenceDateTime : DateTime
) : DateTimeInterval
(
    fromDateTime = referenceDateTime.atBeginningOfDay(),
    toDateTime = referenceDateTime.atEndOfDay()
)
{
    val isToday : Boolean

    init
    {
        val todayAndNow=DateTime.now(TimelineViewDefaults.timezone)!!
        this.isToday=todayAndNow in fromDateTime..toDateTime
    }

    companion object
    {
        @JvmStatic
        fun today() = DailyDateTimeInterval(
            referenceDateTime = DateTime.now(TimelineViewDefaults.timezone))
    }

    override fun getPreviousDateTimeInterval() =
        DailyDateTimeInterval(referenceDateTime = fromDateTime.minusDays(1))

    override fun getNextDateTimeInterval() =
        DailyDateTimeInterval(referenceDateTime = fromDateTime.plusDays(1))

    override fun getShiftedDateTimeInterval(amount : Int) =
        DailyDateTimeInterval(referenceDateTime = fromDateTime.plusDays(amount))

    @SuppressLint("SimpleDateFormat")
    override fun toString(resources : Resources) : String
    {
        val now=DateTime.now(TimelineViewDefaults.timezone)!!

        if (fromDateTime.toLocalDate()==now.minusDays(1).toLocalDate())
            return resources.getString(R.string.yesterday)
        if (fromDateTime.toLocalDate()==now.toLocalDate())
            return resources.getString(R.string.today)
        if (fromDateTime.toLocalDate()==now.plusDays(1).toLocalDate())
            return resources.getString(R.string.tomorrow)

        val dateFormatter=
            if (fromDateTime.year!=now.year)
                DateTimeFormat.forPattern("dd MMM yyyy")!!
            else DateTimeFormat.forPattern("dd MMM")!!
        return dateFormatter.print(fromDateTime)
    }

    override fun toRecyclerViewAdapter(timelineView : TimelineView) =
        DailyDateTimeIntervalAdapter(context = timelineView.context, timelineView = timelineView)

    override fun clone() = DailyDateTimeInterval(referenceDateTime = fromDateTime)
}
