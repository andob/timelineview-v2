package ro.dobrescuandrei.timelineviewv2.recycler

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

class TimelineRecyclerViewHolder : RecyclerView.ViewHolder
{
    constructor(context : Context) : super(TimelineRecyclerViewCell(context))

    fun getCellView() = itemView as TimelineRecyclerViewCell
}
