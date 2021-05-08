package ro.dobrescuandrei.timelineviewv2.model

import android.annotation.SuppressLint
import android.content.res.Resources
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.TimelineViewDefaults
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.CustomDateTimeIntervalAdapter
import ro.dobrescuandrei.timelineviewv2.utils.*

class CustomDateTimeInterval : DateTimeInterval
{
    constructor(fromDateTime : DateTime, toDateTime : DateTime) : super(
        fromDateTime = min(fromDateTime, toDateTime).atBeginningOfDay(),
        toDateTime = max(fromDateTime, toDateTime).atEndOfDay())

    override fun getPreviousDateTimeInterval() : CustomDateTimeInterval? = null
    override fun getNextDateTimeInterval() : CustomDateTimeInterval? = null
    override fun getShiftedDateTimeInterval(amount : Int) : CustomDateTimeInterval? = null

    @SuppressLint("SimpleDateFormat")
    override fun toString(resources : Resources) : String
    {
        val now=DateTime.now(TimelineViewDefaults.timezone)!!

        val startDateTimeFormatter=
            if (fromDateTime.year!=now.year)
                DateTimeFormat.forPattern("dd MMM yyyy")!!
            else DateTimeFormat.forPattern("dd MMM")!!
        val startDateStr=startDateTimeFormatter.print(fromDateTime)

        val endDateTimeFormatter=
            if (toDateTime.year!=now.year)
                DateTimeFormat.forPattern("dd MMM yyyy")!!
            else DateTimeFormat.forPattern("dd MMM")!!
        val endDateStr=endDateTimeFormatter.print(toDateTime)

        return "$startDateStr - $endDateStr"
    }

    override fun toRecyclerViewAdapter(timelineView : TimelineView) =
        CustomDateTimeIntervalAdapter(context = timelineView.context, timelineView = timelineView)

    override fun clone() = CustomDateTimeInterval(fromDateTime, toDateTime)
}
