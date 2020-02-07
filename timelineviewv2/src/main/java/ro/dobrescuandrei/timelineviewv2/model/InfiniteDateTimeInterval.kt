package ro.dobrescuandrei.timelineviewv2.model

import android.content.Context
import android.content.res.Resources
import org.joda.time.DateTime
import ro.dobrescuandrei.timelineviewv2.R
import ro.dobrescuandrei.timelineviewv2.TimelineViewDefaults
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.InfiniteDateTimeIntervalAdapter

class InfiniteDateTimeInterval : DateTimeInterval<InfiniteDateTimeInterval>
(
    fromDateTime = DateTime(0, TimelineViewDefaults.timezone),            //01.01.1970 00:00:00.000
    toDateTime = DateTime(64060588800000L, TimelineViewDefaults.timezone) //01.01.4000 00:00:00.000
)
{
    override fun getPreviousDateTimeInterval() : InfiniteDateTimeInterval? = null
    override fun getNextDateTimeInterval() : InfiniteDateTimeInterval? = null
    override fun getShiftedDateTimeInterval(amount : Int) : InfiniteDateTimeInterval? = null

    override fun minus(another : DateTimeInterval<*>) = 0

    override fun toString(resources : Resources) =
        resources.getString(R.string.all_time)

    override fun toRecyclerViewAdapter(context : Context) =
        InfiniteDateTimeIntervalAdapter(context)
}
