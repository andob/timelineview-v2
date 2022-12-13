package ro.dobrescuandrei.timelineviewv2.recycler

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ro.dobrescuandrei.timelineviewv2.base.BaseTimelineRecyclerViewAdapter
import ro.dobrescuandrei.timelineviewv2.recycler.adapter.InfiniteScrollingTimelineRecyclerViewAdapter
import ro.dobrescuandrei.timelineviewv2.utils.ScreenSize

class TimelineRecyclerView : RecyclerView
{
    constructor(context : Context) : super(context)
    constructor(context : Context, attrs : AttributeSet) : super(context, attrs)

    init
    {
        if (!isInEditMode)
        {
            layoutManager=LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun setAdapter(adapter : Adapter<*>?)
    {
        if (!isInEditMode)
        {
            if (adapter!=null&&adapter !is BaseTimelineRecyclerViewAdapter<*>)
                throw RuntimeException("Please use BaseTimelineRecyclerViewAdapter!!!")

            super.setAdapter(adapter)

            if (adapter is InfiniteScrollingTimelineRecyclerViewAdapter)
                scrollMiddleCellToMiddleOfTheScreen()
        }
    }

    override fun getAdapter() : BaseTimelineRecyclerViewAdapter<*>?
    {
        val adapter=super.getAdapter()?:return null
        return adapter as BaseTimelineRecyclerViewAdapter<*>
    }

    internal fun scrollMiddleCellToMiddleOfTheScreen()
    {
        if (adapter!=null&&adapter is InfiniteScrollingTimelineRecyclerViewAdapter)
            (layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(
                /*position*/ adapter!!.itemCount/2,
                /*offset*/ (ScreenSize.width(context = context)-adapter!!.cellWidthInPixels)/2)
    }
}
