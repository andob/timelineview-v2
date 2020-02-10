package ro.dobrescuandrei.timelineviewv2.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import org.joda.time.DateTime
import ro.dobrescuandrei.timelineviewv2.TimelineViewDefaults
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.CustomDateTimeIntervalAdapter
import ro.dobrescuandrei.timelineviewv2.utils.*
import java.text.SimpleDateFormat

class CustomDateTimeInterval : DateTimeInterval<CustomDateTimeInterval>
{
    constructor(fromDateTime : DateTime, toDateTime : DateTime) : super(
        fromDateTime = min(fromDateTime, toDateTime).atBeginningOfDay(),
        toDateTime = max(fromDateTime, toDateTime).atEndOfDay())

    override fun getPreviousDateTimeInterval() : CustomDateTimeInterval? = null
    override fun getNextDateTimeInterval() : CustomDateTimeInterval? = null
    override fun getShiftedDateTimeInterval(amount : Int) : CustomDateTimeInterval? = null

    override fun minus(another : DateTimeInterval<*>) = 0

    @SuppressLint("SimpleDateFormat")
    override fun toString(resources : Resources) : String
    {
        val now=DateTime.now(TimelineViewDefaults.timezone)!!

        val startDateTimeFormatter=
            if (fromDateTime.year!=now.year)
                SimpleDateFormat("dd MMM yyyy")
            else SimpleDateFormat("dd MMM")
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

    override fun toRecyclerViewAdapter(context : Context) =
        CustomDateTimeIntervalAdapter(context)
}
