package ro.dobrescuandrei.timelineviewv2

import android.content.Context
import android.util.AttributeSet
import android.view.View
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
            setDateTimeIntervalTypeChangeFlow(TimelineViewDefaults.dateTimeIntervalTypeChangeFlow)
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
        recyclerView.adapter?.selectedDateTimeInterval=dateTimeInterval

        recyclerView.adapter?.setOnSelectedDateTimeIntervalChangedListener { dateTimeInterval ->
            super.setDateTimeInterval(dateTimeInterval)
        }
    }

    fun setDateTimeIntervalTypeChangeFlow(flow : DateTimeIntervalTypeChangeFlow)
    {
        dateTimeInterval=flow.getFirstFlowNode().newInstance()

        updateUiFromIntervalTypeChangeFlowUi(flow)

        decrementDateIntervalTypeButton.setOnClickListener {
            flow.previousNode()?.let { type ->
                dateTimeInterval=dateTimeInterval.toDateTimeInterval(type = type)

                updateUiFromIntervalTypeChangeFlowUi(flow)
            }
        }

        incrementDateIntervalTypeButton.setOnClickListener {
            flow.nextNode()?.let { type ->
                dateTimeInterval=dateTimeInterval.toDateTimeInterval(type = type)

                updateUiFromIntervalTypeChangeFlowUi(flow)
            }
        }
    }

    private fun updateUiFromIntervalTypeChangeFlowUi(flow : DateTimeIntervalTypeChangeFlow)
    {
        if (flow.hasPreviousNode())
        {
            decrementDateIntervalTypeButton.visibility=View.VISIBLE
            changeDateIntervalTypeLeftButton.visibility=View.GONE
        }
        else
        {
            decrementDateIntervalTypeButton.visibility=View.GONE
            changeDateIntervalTypeLeftButton.visibility=View.VISIBLE
        }

        if (flow.hasNextNode())
        {
            incrementDateIntervalTypeButton.visibility=View.VISIBLE
            changeDateIntervalTypeRightButton.visibility=View.GONE
        }
        else
        {
            incrementDateIntervalTypeButton.visibility=View.GONE
            changeDateIntervalTypeRightButton.visibility=View.VISIBLE
        }
    }

    override fun onDetachedFromWindow()
    {
        setOnDateTimeIntervalChangedListener(null)

        recyclerView.adapter?.dispose()
        recyclerView.adapter=null

        super.onDetachedFromWindow()
    }
}
