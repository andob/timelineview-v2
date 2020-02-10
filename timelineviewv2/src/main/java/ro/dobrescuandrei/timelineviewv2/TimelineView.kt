package ro.dobrescuandrei.timelineviewv2

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.View
import kotlinx.android.synthetic.main.timeline_view.view.*
import org.joda.time.DateTime
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import ro.dobrescuandrei.timelineviewv2.base.BaseTimelineView
import ro.dobrescuandrei.timelineviewv2.dialog.ChangeDateTimeIntervalTypeDialog
import ro.dobrescuandrei.timelineviewv2.model.CustomDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DailyDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DateTimeIntervalConverter

class TimelineView : BaseTimelineView
{
    internal val dateTimeIntervalConverter = DateTimeIntervalConverter()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun getLayoutId() = R.layout.timeline_view

    override fun resolveAttributeSetAfterOnCreate(attributeSet : AttributeSet) {}

    override fun onCreate()
    {
        this.dateTimeIntervalTypeChangeFlow=TimelineViewDefaults.dateTimeIntervalTypeChangeFlow

        changeDateIntervalTypeLeftButton.setOnClickListener { ChangeDateTimeIntervalTypeDialog.show(timelineView = this) }
        changeDateIntervalTypeRightButton.setOnClickListener { ChangeDateTimeIntervalTypeDialog.show(timelineView = this) }
    }

    override fun onWindowFocusChanged(windowHasFocus : Boolean)
    {
        super.onWindowFocusChanged(windowHasFocus)

        if (windowHasFocus)
        {
            //on activity resumed, refresh if the day has passed in the meantime...
            if (dateTimeInterval is DailyDateTimeInterval&&(dateTimeInterval as DailyDateTimeInterval).isToday&&
                DateTime.now(TimelineViewDefaults.timezone).toLocalDate()!=dateTimeInterval.fromDateTime.toLocalDate())
                dateTimeInterval=DailyDateTimeInterval.today()
        }
    }

    override fun onConfigurationChanged(newConfig : Configuration?)
    {
        super.onConfigurationChanged(newConfig)

        //maybe screen orientation has changed???
        recyclerView.scrollMiddleCellToMiddleOfTheScreen()
    }

    override fun setOnDateTimeIntervalChangedListener(listener : OnDateTimeIntervalChangedListener?)
    {
        super.setOnDateTimeIntervalChangedListener(listener)

        recyclerView.adapter?.setOnSelectedDateTimeIntervalChangedListener(listener)
    }

    override fun setDateTimeInterval(dateTimeInterval : DateTimeInterval<*>)
    {
        if (dateTimeInterval::class.java !in dateTimeIntervalTypeChangeFlow.toList()||
           (dateTimeInterval is CustomDateTimeInterval&&isCustomDateTimeIntervalSupported))
            throw RuntimeException("Invalid interval type!")

        if (this.dateTimeInterval!=dateTimeInterval)
        {
            dateTimeIntervalTypeChangeFlow.seekToNode(dateTimeInterval::class.java)
            updateUiFromIntervalTypeChangeFlow()
        }

        super.setDateTimeInterval(dateTimeInterval)

        recyclerView.adapter?.dispose()

        recyclerView.adapter=dateTimeInterval.toRecyclerViewAdapter(context = context)
        recyclerView.adapter?.selectedDateTimeInterval=dateTimeInterval

        recyclerView.adapter?.setOnSelectedDateTimeIntervalChangedListener { dateTimeInterval ->
            super.setDateTimeInterval(dateTimeInterval)
        }
    }

    override fun setDateTimeIntervalTypeChangeFlow(flow : DateTimeIntervalTypeChangeFlow)
    {
        super.setDateTimeIntervalTypeChangeFlow(flow)

        val todayAndNow=DateTime(TimelineViewDefaults.timezone)

        dateTimeInterval=flow.getFirstNode().constructors.find { constructor ->
            DateTime::class.java in constructor.parameterTypes
        }!!.newInstance(todayAndNow) as DateTimeInterval<*>

        updateUiFromIntervalTypeChangeFlow()

        decrementDateIntervalTypeButton.setOnClickListener {
            flow.previousNode()?.let { type ->
                dateTimeInterval=dateTimeIntervalConverter.convert(from = dateTimeInterval, to = type)
                updateUiFromIntervalTypeChangeFlow()
            }
        }

        incrementDateIntervalTypeButton.setOnClickListener {
            flow.nextNode()?.let { type ->
                dateTimeInterval=dateTimeIntervalConverter.convert(from = dateTimeInterval, to = type)
                updateUiFromIntervalTypeChangeFlow()
            }
        }
    }

    private fun updateUiFromIntervalTypeChangeFlow()
    {
        val flow=dateTimeIntervalTypeChangeFlow
        if (flow.hasPreviousNode()&&flow.hasNextNode())
        {
            incrementDateIntervalTypeButton.visibility=View.VISIBLE
            decrementDateIntervalTypeButton.visibility=View.VISIBLE
            changeDateIntervalTypeLeftButton.visibility=View.GONE
            changeDateIntervalTypeRightButton.visibility=View.GONE
        }
        else if (flow.hasPreviousNode())
        {
            incrementDateIntervalTypeButton.visibility=View.GONE
            decrementDateIntervalTypeButton.visibility=View.VISIBLE
            changeDateIntervalTypeLeftButton.visibility=View.GONE
            changeDateIntervalTypeRightButton.visibility=View.VISIBLE
        }
        else if (flow.hasNextNode())
        {
            incrementDateIntervalTypeButton.visibility=View.VISIBLE
            decrementDateIntervalTypeButton.visibility=View.GONE
            changeDateIntervalTypeLeftButton.visibility=View.VISIBLE
            changeDateIntervalTypeRightButton.visibility=View.GONE
        }
        else
        {
            incrementDateIntervalTypeButton.visibility=View.GONE
            decrementDateIntervalTypeButton.visibility=View.GONE
            changeDateIntervalTypeLeftButton.visibility=View.VISIBLE
            changeDateIntervalTypeRightButton.visibility=View.GONE
        }

        if (flow.toList().size<=1&&!isCustomDateTimeIntervalSupported)
        {
            changeDateIntervalTypeLeftButton.visibility=View.GONE
            changeDateIntervalTypeRightButton.visibility=View.GONE
        }
    }

    override fun setCustomDateTimeIntervalSupported(isSupported : Boolean)
    {
        super.setCustomDateTimeIntervalSupported(isSupported)
        updateUiFromIntervalTypeChangeFlow()
    }

    override fun onDetachedFromWindow()
    {
        setOnDateTimeIntervalChangedListener(null)

        recyclerView.adapter?.dispose()
        recyclerView.adapter=null

        super.onDetachedFromWindow()
    }
}