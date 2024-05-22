package ro.dobrescuandrei.timelineviewv2.recycler.adapter

import android.content.Context
import ro.dobrescuandrei.timelineviewv2.InvalidDateTimeIntervalTypeException
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.base.BaseTimelineRecyclerViewAdapter
import ro.dobrescuandrei.timelineviewv2.dialog.ChangeDateTimeIntervalTypeDialog
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import ro.dobrescuandrei.timelineviewv2.recycler.TimelineRecyclerViewHolder

abstract class InfiniteScrollingTimelineRecyclerViewAdapter : BaseTimelineRecyclerViewAdapter<DateTimeInterval>
{
    constructor(context : Context?, timelineView : TimelineView?) : super(context, timelineView)

    override fun onBindViewHolder(holder : TimelineRecyclerViewHolder, position : Int)
    {
        (position-itemCount/2).let { normalizedPosition ->
            val dateTimeInterval = referenceDateTimeInterval.getShiftedDateTimeInterval(normalizedPosition.toLong()) as DateTimeInterval

            val cellView = holder.getCellView()
            cellView.setDateTimeInterval(dateTimeInterval)
            cellView.setIsSelected(normalizedPosition==0)

            cellView.setOnClickListener {
                if (cellView.isSelected)
                {
                    if (timelineView.appearance.isDateTimeIntervalTypeChangerDialogSupported)
                        ChangeDateTimeIntervalTypeDialog.show(timelineView = timelineView)
                }
                else
                {
                    try { this.timelineView.dateTimeInterval = dateTimeInterval }
                    catch (_ : InvalidDateTimeIntervalTypeException) {}
                }
            }

            timelineView.timelineRecyclerViewCellTransformer?.transform(cellView, dateTimeInterval)
        }
    }

    override fun getItemCount() = Int.MAX_VALUE
}
