package ro.dobrescuandrei.timelineviewv2.recycler.adapter

import android.content.Context
import ro.dobrescuandrei.timelineviewv2.R

class WeeklyDateIntervalAdapter : InfiniteScrollingTimelineRecyclerViewAdapter
{
    constructor(context: Context?) : super(context)

    override fun getCellWidthInPixels() =
        150*context.resources.getDimensionPixelSize(R.dimen.one_dp)
}
