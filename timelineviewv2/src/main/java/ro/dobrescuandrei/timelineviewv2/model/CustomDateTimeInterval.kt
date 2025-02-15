package ro.dobrescuandrei.timelineviewv2.model

import android.annotation.SuppressLint
import android.content.res.Resources
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.CustomDateTimeIntervalAdapter
import ro.dobrescuandrei.timelineviewv2.utils.*
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CustomDateTimeInterval : DateTimeInterval
{
    constructor(fromDateTime : ZonedDateTime, toDateTime : ZonedDateTime) : super(
        fromDateTime = min(fromDateTime, toDateTime).atBeginningOfDay(),
        toDateTime = max(fromDateTime, toDateTime).atEndOfDay())

    constructor(fromDateTime : LocalDateTime, toDateTime : LocalDateTime) : this(
        fromDateTime = fromDateTime.atZone(defaultTimezone),
        toDateTime = toDateTime.atZone(defaultTimezone))

    override fun getPreviousDateTimeInterval() : CustomDateTimeInterval? = null
    override fun getNextDateTimeInterval() : CustomDateTimeInterval? = null
    override fun getShiftedDateTimeInterval(amount : Long) : CustomDateTimeInterval? = null

    @SuppressLint("SimpleDateFormat")
    override fun toString(resources : Resources) : String
    {
        val now = ZonedDateTime.now(defaultTimezone)!!

        val startDateTimeFormatter = 
            if (fromDateTime.year != now.year)
                DateTimeFormatter.ofPattern("dd MMM yyyy")!!
            else DateTimeFormatter.ofPattern("dd MMM")!!
        val startDateStr = startDateTimeFormatter.format(fromDateTime)!!

        val endDateTimeFormatter = 
            if (toDateTime.year != now.year)
                DateTimeFormatter.ofPattern("dd MMM yyyy")!!
            else DateTimeFormatter.ofPattern("dd MMM")!!
        val endDateStr = endDateTimeFormatter.format(toDateTime)!!

        return "$startDateStr - $endDateStr"
    }

    override fun toRecyclerViewAdapter(timelineView : TimelineView) =
        CustomDateTimeIntervalAdapter(context = timelineView.context, timelineView = timelineView)

    override fun clone() = CustomDateTimeInterval(fromDateTime, toDateTime)
}
