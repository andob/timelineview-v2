package ro.dobrescuandrei.timelineviewv2.recycler.adapter

import android.content.Context
import android.view.ViewGroup
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.base.BaseTimelineRecyclerViewAdapter
import ro.dobrescuandrei.timelineviewv2.dialog.ChangeDateTimeIntervalTypeDialog
import ro.dobrescuandrei.timelineviewv2.model.CustomDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.recycler.TimelineRecyclerViewHolder

class CustomDateTimeIntervalAdapter : BaseTimelineRecyclerViewAdapter<CustomDateTimeInterval>
{
    constructor(context: Context?, timelineView: TimelineView?) : super(context, timelineView)

    override fun onBindViewHolder(holder : TimelineRecyclerViewHolder, position : Int)
    {
        val cellView=holder.getCellView()
        cellView.setDateTimeInterval(referenceDateTimeInterval)
        cellView.setIsSelected(false)

        cellView.setOnClickListener { cellView ->
            if (timelineView.appearance.isDateTimeIntervalTypeChangerDialogSupported)
                ChangeDateTimeIntervalTypeDialog.show(timelineView = timelineView)
        }

        timelineView.timelineRecyclerViewCellTransformer?.invoke(cellView, referenceDateTimeInterval)
    }

    override fun getItemCount() = 1

    override fun getCellWidthInPixels() = ViewGroup.LayoutParams.MATCH_PARENT
}
