package ro.dobrescuandrei.timelineviewv2.recycler.adapter

import android.content.Context
import ro.dobrescuandrei.timelineviewv2.TimelineViewAppearance
import ro.dobrescuandrei.timelineviewv2.base.BaseTimelineRecyclerViewAdapter
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import ro.dobrescuandrei.timelineviewv2.recycler.TimelineRecyclerView
import ro.dobrescuandrei.timelineviewv2.recycler.TimelineRecyclerViewHolder
import ro.dobrescuandrei.timelineviewv2.utils.getParentRecyclerView

abstract class InfiniteScrollingTimelineRecyclerViewAdapter : BaseTimelineRecyclerViewAdapter<DateTimeInterval<*>>
{
    constructor(context: Context?, appearance: TimelineViewAppearance?) : super(context, appearance)

    override fun onBindViewHolder(holder : TimelineRecyclerViewHolder, position : Int)
    {
        (position-itemCount/2+(referenceDateTimeInterval-selectedDateTimeInterval)).let { position ->
            val dateTimeInterval=referenceDateTimeInterval.getShiftedDateTimeInterval(position) as DateTimeInterval<*>

            val cellView=holder.getCellView()
            cellView.setDateTimeInterval(dateTimeInterval)
            cellView.setIsSelected(dateTimeInterval==selectedDateTimeInterval)

            cellView.setOnClickListener { cellView ->
                this.selectedDateTimeInterval=dateTimeInterval
                this.onSelectedDateTimeIntervalChangedListener?.invoke(dateTimeInterval)

                (cellView.getParentRecyclerView() as? TimelineRecyclerView)
                    ?.scrollMiddleCellToMiddleOfTheScreen()

                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount() = Int.MAX_VALUE
}
