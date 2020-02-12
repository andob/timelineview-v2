package ro.dobrescuandrei.timelineviewv2.recycler.adapter

import android.content.Context
import ro.dobrescuandrei.timelineviewv2.InvalidDateTimeIntervalTypeException
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.base.BaseTimelineRecyclerViewAdapter
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import ro.dobrescuandrei.timelineviewv2.recycler.TimelineRecyclerView
import ro.dobrescuandrei.timelineviewv2.recycler.TimelineRecyclerViewHolder
import ro.dobrescuandrei.timelineviewv2.utils.getParentRecyclerView

abstract class InfiniteScrollingTimelineRecyclerViewAdapter : BaseTimelineRecyclerViewAdapter<DateTimeInterval>
{
    constructor(context: Context?, timelineView: TimelineView?) : super(context, timelineView)

    override fun onBindViewHolder(holder : TimelineRecyclerViewHolder, position : Int)
    {
        (position-itemCount/2+(referenceDateTimeInterval-selectedDateTimeInterval)).let { position ->
            val dateTimeInterval=referenceDateTimeInterval.getShiftedDateTimeInterval(position) as DateTimeInterval

            val cellView=holder.getCellView()
            cellView.setDateTimeInterval(dateTimeInterval)
            cellView.setIsSelected(dateTimeInterval==selectedDateTimeInterval)

            cellView.setOnClickListener { cellView ->
                try
                {
                    this.onSelectedDateTimeIntervalChangedListener?.invoke(dateTimeInterval)
                    this.selectedDateTimeInterval=dateTimeInterval

                    (cellView.getParentRecyclerView() as? TimelineRecyclerView)
                        ?.scrollMiddleCellToMiddleOfTheScreen()

                    notifyDataSetChanged()
                }
                catch (ex : InvalidDateTimeIntervalTypeException) {}
            }

            timelineView.timelineRecyclerViewCellTransformer?.transform(
                cellView = cellView, dateTimeInterval = dateTimeInterval)
        }
    }

    override fun getItemCount() = Int.MAX_VALUE
}
