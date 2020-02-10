package ro.dobrescuandrei.timelineviewv2.recycler

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import ro.dobrescuandrei.timelineviewv2.TimelineViewAppearance

class TimelineRecyclerViewHolder : RecyclerView.ViewHolder
{
    constructor(context : Context, appearance : TimelineViewAppearance) :
            super(TimelineRecyclerViewCell(context, appearance))

    fun getCellView() = itemView as TimelineRecyclerViewCell
}
