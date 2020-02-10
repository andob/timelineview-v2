package ro.dobrescuandrei.timelineviewv2.recycler.adapter

import android.content.Context
import ro.dobrescuandrei.timelineviewv2.R
import ro.dobrescuandrei.timelineviewv2.TimelineViewAppearance

class DailyDateTimeIntervalAdapter : InfiniteScrollingTimelineRecyclerViewAdapter
{
    constructor(context: Context?, appearance: TimelineViewAppearance?) : super(context, appearance)

    override fun getCellWidthInPixels() =
        100*context.resources.getDimensionPixelSize(R.dimen.one_dp)
}
