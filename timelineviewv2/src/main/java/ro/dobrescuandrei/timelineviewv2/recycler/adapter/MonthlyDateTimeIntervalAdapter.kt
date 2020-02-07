package ro.dobrescuandrei.timelineviewv2.recycler.adapter

import android.content.Context
import ro.dobrescuandrei.timelineviewv2.R

class MonthlyDateTimeIntervalAdapter : InfiniteScrollingTimelineRecyclerViewAdapter
{
    constructor(context: Context?) : super(context)

    override fun getCellWidthInPixels() =
        100*context.resources.getDimensionPixelSize(R.dimen.one_dp)
}
