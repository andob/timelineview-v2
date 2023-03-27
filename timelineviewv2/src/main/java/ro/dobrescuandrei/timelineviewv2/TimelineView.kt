package ro.dobrescuandrei.timelineviewv2

import android.content.Context
import android.content.res.Configuration
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import ro.dobrescuandrei.timelineviewv2.base.BaseCustomView
import ro.dobrescuandrei.timelineviewv2.dialog.ChangeDateTimeIntervalTypeDialog
import ro.dobrescuandrei.timelineviewv2.model.CustomDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DailyDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DateTimeIntervalConverter
import ro.dobrescuandrei.timelineviewv2.recycler.TimelineRecyclerView
import ro.dobrescuandrei.timelineviewv2.recycler.TimelineRecyclerViewCell
import java.time.ZonedDateTime

open class TimelineView : BaseCustomView
{
    val appearance : TimelineViewAppearance

    var onDateTimeIntervalChangedListener : OnDateTimeIntervalChangedListener? = null

    var timelineRecyclerViewCellTransformer : ((TimelineRecyclerViewCell, DateTimeInterval) -> Unit)? = null ; private set

    constructor(context : Context, attributeSet : AttributeSet) : super(context, attributeSet)
    {
        if (!isInEditMode)
        {
            val attributes=context.obtainStyledAttributes(attributeSet, R.styleable.TimelineView)

            this.appearance=TimelineViewAppearance(context, attributes)
            decrementDateIntervalTypeButton.setImageResource(appearance.downIconResourceId)
            incrementDateIntervalTypeButton.setImageResource(appearance.upIconResourceId)
            changeDateIntervalTypeLeftButton.setImageResource(appearance.calendarIconResourceId)
            changeDateIntervalTypeRightButton.setImageResource(appearance.calendarIconResourceId)
            leftButtonsContainer.setBackgroundResource(appearance.leftButtonsContainerBackgroundResourceId)
            rightButtonsContainer.setBackgroundResource(appearance.rightButtonsContainerBackgroundResourceId)
            rootContainer.setBackgroundColor(appearance.unselectedCellBackgroundColor)

            this.dateTimeIntervalTypeChangeFlow=this.appearance.createDateTimeIntervalTypeChangeFlow()

            changeDateIntervalTypeLeftButton.setOnClickListener { ChangeDateTimeIntervalTypeDialog.show(timelineView = this) }
            changeDateIntervalTypeRightButton.setOnClickListener { ChangeDateTimeIntervalTypeDialog.show(timelineView = this) }

            val shouldDisablePast=attributes.hasValue(R.styleable.TimelineView_disable_clicking_on_past_intervals)
            val shouldDisableFuture=attributes.hasValue(R.styleable.TimelineView_disable_clicking_on_future_intervals)
            if (shouldDisablePast||shouldDisableFuture)
            {
                timelineRecyclerViewCellTransformer={ cellView, dateTimeInterval ->
                    val now=ZonedDateTime.now(DateTimeInterval.defaultTimezone)
                    if (shouldDisablePast&&dateTimeInterval.fromDateTime.isBefore(now))
                        cellView.setOnClickListener(null)
                    else if (shouldDisableFuture&&dateTimeInterval.toDateTime.isAfter(now))
                        cellView.setOnClickListener(null)
                }
            }

            attributes.recycle()
        }
        else this.appearance=TimelineViewAppearance(context)
    }

    override fun getLayoutId() = R.layout.timeline_view

    override fun onWindowFocusChanged(windowHasFocus : Boolean)
    {
        super.onWindowFocusChanged(windowHasFocus)

        if (windowHasFocus)
        {
            //on activity resumed, refresh if the day has passed in the meantime...
            if (dateTimeInterval is DailyDateTimeInterval&&(dateTimeInterval as DailyDateTimeInterval).isToday&&
                ZonedDateTime.now(DateTimeInterval.defaultTimezone).toLocalDate()!=dateTimeInterval.fromDateTime.toLocalDate())
                dateTimeInterval=DailyDateTimeInterval.today()
        }
    }

    override fun onConfigurationChanged(newConfig : Configuration?)
    {
        super.onConfigurationChanged(newConfig)

        //maybe screen orientation has changed???
        recyclerView.scrollMiddleCellToMiddleOfTheScreen()
    }

    var firstTimeDateTimeIntervalSetterWasCalled = true
    var dateTimeInterval : DateTimeInterval = DailyDateTimeInterval.today()
    set(dateTimeInterval)
    {
        if (!Looper.getMainLooper().isCurrentThread)
            throw RuntimeException("This method must be called on UI thread!")

        if (dateTimeInterval !is CustomDateTimeInterval&&!dateTimeIntervalTypeChangeFlow.toList().contains(dateTimeInterval::class.java))
            throw RuntimeException("Cannot use ${dateTimeInterval::class.java.simpleName}")

        if (dateTimeInterval is CustomDateTimeInterval&&!appearance.isCustomDateTimeIntervalSupported)
            throw InvalidDateTimeIntervalTypeException("Cannot use CustomDateTimeInterval!")

        if (field!=dateTimeInterval||firstTimeDateTimeIntervalSetterWasCalled)
        {
            if (field::class.java!=dateTimeInterval::class.java)
            {
                dateTimeIntervalTypeChangeFlow.seekToNode(dateTimeInterval::class.java)
                updateUiFromIntervalTypeChangeFlow()
            }

            field=dateTimeInterval
            this.onDateTimeIntervalChangedListener?.invoke(dateTimeInterval)

            recyclerView.adapter?.dispose()

            recyclerView.adapter=dateTimeInterval.toRecyclerViewAdapter(timelineView = this)

            firstTimeDateTimeIntervalSetterWasCalled=false
        }
    }

    fun setOnDateTimeIntervalChangedListener(listener : (DateTimeInterval) -> Unit)
    {
        this.onDateTimeIntervalChangedListener=OnDateTimeIntervalChangedListener { listener(it) }
    }

    var dateTimeIntervalTypeChangeFlow : DateTimeIntervalTypeChangeFlow =
        DateTimeIntervalTypeChangeFlow.build { from(DailyDateTimeInterval::class.java) }
    set(flow)
    {
        field=flow

        val todayAndNow=ZonedDateTime.now(DateTimeInterval.defaultTimezone)

        this.dateTimeInterval=flow.getFirstNode().constructors.find { constructor ->
            ZonedDateTime::class.java in constructor.parameterTypes
        }!!.newInstance(todayAndNow) as DateTimeInterval

        updateUiFromIntervalTypeChangeFlow()

        decrementDateIntervalTypeButton.setOnClickListener {
            flow.previousNode()?.let { type ->
                this.dateTimeInterval=DateTimeIntervalConverter().convert(from = dateTimeInterval, to = type)
                updateUiFromIntervalTypeChangeFlow()
            }
        }

        incrementDateIntervalTypeButton.setOnClickListener {
            flow.nextNode()?.let { type ->
                this.dateTimeInterval=DateTimeIntervalConverter().convert(from = dateTimeInterval, to = type)
                updateUiFromIntervalTypeChangeFlow()
            }
        }
    }

    private fun updateUiFromIntervalTypeChangeFlow()
    {
        if (!Looper.getMainLooper().isCurrentThread)
            throw RuntimeException("This method must be called on UI thread!")

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

        if (!appearance.isDateTimeIntervalTypeChangerDialogSupported)
        {
            changeDateIntervalTypeLeftButton.visibility=View.GONE
            changeDateIntervalTypeRightButton.visibility=View.GONE
        }
    }

    override fun onDetachedFromWindow()
    {
        onDateTimeIntervalChangedListener=null

        recyclerView.adapter?.dispose()
        recyclerView.adapter=null

        super.onDetachedFromWindow()
    }

    fun preventFiringIntervalChangedEvents(toRun : Runnable)
    {
        if (!Looper.getMainLooper().isCurrentThread)
            throw RuntimeException("This method must be called on UI thread!")

        val aux=onDateTimeIntervalChangedListener
        onDateTimeIntervalChangedListener=null
        toRun.run()
        onDateTimeIntervalChangedListener=aux
    }

    private val rootContainer get() = findViewById<View>(R.id.rootContainer)!!
    private val recyclerView get() = findViewById<TimelineRecyclerView>(R.id.recyclerView)!!
    private val leftButtonsContainer get() = findViewById<LinearLayout>(R.id.leftButtonsContainer)!!
    private val decrementDateIntervalTypeButton get() = findViewById<ImageView>(R.id.decrementDateIntervalTypeButton)!!
    private val changeDateIntervalTypeLeftButton get() = findViewById<ImageView>(R.id.changeDateIntervalTypeLeftButton)!!
    private val rightButtonsContainer get() = findViewById<LinearLayout>(R.id.rightButtonsContainer)!!
    private val incrementDateIntervalTypeButton get() = findViewById<ImageView>(R.id.incrementDateIntervalTypeButton)!!
    private val changeDateIntervalTypeRightButton get() = findViewById<ImageView>(R.id.changeDateIntervalTypeRightButton)!!
}
