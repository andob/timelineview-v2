package ro.dobrescuandrei.timelineviewv2.model

import android.content.res.Resources
import ro.dobrescuandrei.timelineviewv2.R
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.InfiniteDateTimeIntervalAdapter
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

class InfiniteDateTimeInterval : DateTimeInterval
(
    fromDateTime = ZonedDateTime.of(LocalDateTime.of(1970, 1, 1, 0, 0, 0), ZoneOffset.UTC),
    toDateTime = ZonedDateTime.of(LocalDateTime.of(4000, 1, 1, 0, 0, 0), ZoneOffset.UTC),
)
{
    override fun getPreviousDateTimeInterval() : InfiniteDateTimeInterval? = null
    override fun getNextDateTimeInterval() : InfiniteDateTimeInterval? = null
    override fun getShiftedDateTimeInterval(amount : Long) : InfiniteDateTimeInterval? = null

    override fun toString(resources : Resources) =
        resources.getString(R.string.all_time)

    override fun toRecyclerViewAdapter(timelineView : TimelineView) =
        InfiniteDateTimeIntervalAdapter(context = timelineView.context, timelineView = timelineView)

    override fun clone() = InfiniteDateTimeInterval()
}
