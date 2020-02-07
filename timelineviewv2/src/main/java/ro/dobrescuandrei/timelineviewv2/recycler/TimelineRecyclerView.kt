package ro.dobrescuandrei.timelineviewv2.recycler

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.timeline_view.view.*
import ro.dobrescuandrei.timelineviewv2.base.BaseTimelineRecyclerViewAdapter
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.InfiniteScrollingTimelineRecyclerViewAdapter

class TimelineRecyclerView : RecyclerView
{
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    {
        recyclerView.layoutManager=LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun setAdapter(adapter : Adapter<*>?)
    {
        if (adapter!=null&&adapter !is BaseTimelineRecyclerViewAdapter<*>)
            throw RuntimeException("Please use BaseTimelineRecyclerViewAdapter!!!")

        super.setAdapter(adapter)

        if (adapter is InfiniteScrollingTimelineRecyclerViewAdapter)
            scrollSelectedCellToMiddleOfTheScreen()
    }

    override fun getAdapter() : BaseTimelineRecyclerViewAdapter<*>?
    {
        val adapter=super.getAdapter()?:return null
        return adapter as BaseTimelineRecyclerViewAdapter<*>
    }

    fun scrollSelectedCellToMiddleOfTheScreen()
    {
        if (adapter!=null&&adapter is InfiniteScrollingTimelineRecyclerViewAdapter)
            (layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(
                /*position*/ adapter!!.itemCount/2,
                /*offset*/ measuredWidth/2-adapter!!.cellWidthInPixels/2)
    }
}
