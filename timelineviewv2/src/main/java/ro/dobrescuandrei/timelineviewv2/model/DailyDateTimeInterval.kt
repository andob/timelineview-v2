package ro.dobrescuandrei.timelineviewv2.model

import android.annotation.SuppressLint
import android.content.res.Resources
import ro.dobrescuandrei.timelineviewv2.R
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.DailyDateTimeIntervalAdapter
import ro.dobrescuandrei.timelineviewv2.utils.atBeginningOfDay
import ro.dobrescuandrei.timelineviewv2.utils.atEndOfDay
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class DailyDateTimeInterval : DateTimeInterval
{
    val isToday : Boolean

    constructor(referenceDateTime : ZonedDateTime) : super(
        fromDateTime = referenceDateTime.atBeginningOfDay(),
        toDateTime = referenceDateTime.atEndOfDay())

    constructor(referenceDateTime : LocalDateTime) : this(
        referenceDateTime = referenceDateTime.atZone(defaultTimezone))

    init
    {
        val todayAndNow = ZonedDateTime.now(defaultTimezone)!!
        this.isToday = todayAndNow in fromDateTime..toDateTime
    }

    companion object
    {
        @JvmStatic
        fun today() = DailyDateTimeInterval(
            referenceDateTime = ZonedDateTime.now(defaultTimezone))

        @JvmStatic
        fun around(date : LocalDate) = DailyDateTimeInterval(
            referenceDateTime = date.atTime(LocalTime.now(defaultTimezone)))
    }

    override fun getPreviousDateTimeInterval() =
        DailyDateTimeInterval(referenceDateTime = fromDateTime.minusDays(1))

    override fun getNextDateTimeInterval() =
        DailyDateTimeInterval(referenceDateTime = fromDateTime.plusDays(1))

    override fun getShiftedDateTimeInterval(amount : Long) =
        DailyDateTimeInterval(referenceDateTime = fromDateTime.plusDays(amount))

    @SuppressLint("SimpleDateFormat")
    override fun toString(resources : Resources) : String
    {
        val now = ZonedDateTime.now(defaultTimezone)!!

        if (fromDateTime.toLocalDate()==now.minusDays(1).toLocalDate())
            return resources.getString(R.string.yesterday)
        if (fromDateTime.toLocalDate()==now.toLocalDate())
            return resources.getString(R.string.today)
        if (fromDateTime.toLocalDate()==now.plusDays(1).toLocalDate())
            return resources.getString(R.string.tomorrow)

        val dateFormatter = 
            if (fromDateTime.year != now.year)
                DateTimeFormatter.ofPattern("dd MMM yyyy")!!
            else DateTimeFormatter.ofPattern("dd MMM")!!
        return dateFormatter.format(fromDateTime)!!
    }

    override fun toRecyclerViewAdapter(timelineView : TimelineView) =
        DailyDateTimeIntervalAdapter(context = timelineView.context, timelineView = timelineView)

    override fun clone() = DailyDateTimeInterval(referenceDateTime = fromDateTime)
}
