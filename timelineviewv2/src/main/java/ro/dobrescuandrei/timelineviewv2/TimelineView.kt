package ro.dobrescuandrei.timelineviewv2

import android.content.Context
import android.util.AttributeSet
import kotlinx.android.synthetic.main.timeline_view.view.*
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import ro.dobrescuandrei.timelineviewv2.base.BaseTimelineView
import ro.dobrescuandrei.timelineviewv2.utils.setOnFirstMeasureListener

class TimelineView : BaseTimelineView
{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun getLayoutId() = R.layout.timeline_view

    override fun resolveAttributeSetOnCreate(attributeSet : AttributeSet) {}

    override fun onCreate()
    {
        setOnFirstMeasureListener {
            dateTimeInterval=TimelineViewDefaults.dateTimeIntervalFactory.invoke()
        }
    }

    override fun setOnDateTimeIntervalChangedListener(listener : OnDateTimeIntervalChangedListener?)
    {
        super.setOnDateTimeIntervalChangedListener(listener)

        recyclerView.adapter?.setOnSelectedDateTimeIntervalChangedListener(listener)
    }

    override fun setDateTimeInterval(dateTimeInterval : DateTimeInterval<*>)
    {
        super.setDateTimeInterval(dateTimeInterval)

        recyclerView.adapter?.dispose()

        recyclerView.adapter=dateTimeInterval.toRecyclerViewAdapter(context = context)
        recyclerView.adapter?.setOnSelectedDateTimeIntervalChangedListener(onDateTimeIntervalChangedListener)
        recyclerView.adapter?.selectedDateTimeInterval=dateTimeInterval
    }

    override fun onDetachedFromWindow()
    {
        setOnDateTimeIntervalChangedListener(null)

        recyclerView.adapter?.dispose()
        recyclerView.adapter=null

        super.onDetachedFromWindow()
    }
}
