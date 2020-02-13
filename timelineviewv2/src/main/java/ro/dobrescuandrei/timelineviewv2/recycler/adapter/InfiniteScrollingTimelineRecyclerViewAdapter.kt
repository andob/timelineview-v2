package ro.dobrescuandrei.timelineviewv2.recycler.adapter

import android.content.Context
import ro.dobrescuandrei.timelineviewv2.InvalidDateTimeIntervalTypeException
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.base.BaseTimelineRecyclerViewAdapter
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import ro.dobrescuandrei.timelineviewv2.recycler.TimelineRecyclerViewHolder

abstract class InfiniteScrollingTimelineRecyclerViewAdapter : BaseTimelineRecyclerViewAdapter<DateTimeInterval>
{
    constructor(context: Context?, timelineView: TimelineView?) : super(context, timelineView)

    override fun onBindViewHolder(holder : TimelineRecyclerViewHolder, position : Int)
    {
        (position-itemCount/2).let { position ->
            val dateTimeInterval=referenceDateTimeInterval.getShiftedDateTimeInterval(position) as DateTimeInterval

            val cellView=holder.getCellView()
            cellView.setDateTimeInterval(dateTimeInterval)
            cellView.setIsSelected(position==0)

            cellView.setOnClickListener { cellView ->
                try { this.timelineView.dateTimeInterval=dateTimeInterval }
                catch (ex : InvalidDateTimeIntervalTypeException) {}
            }

            timelineView.timelineRecyclerViewCellTransformer?.transform(
                cellView = cellView, dateTimeInterval = dateTimeInterval)
        }
    }

    override fun getItemCount() = Int.MAX_VALUE
}
