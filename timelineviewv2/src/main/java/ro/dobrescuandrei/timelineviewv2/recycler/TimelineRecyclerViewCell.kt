package ro.dobrescuandrei.timelineviewv2.recycler

import java.time.ZonedDateTime
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import ro.dobrescuandrei.timelineviewv2.R
import ro.dobrescuandrei.timelineviewv2.TimelineViewAppearance
import ro.dobrescuandrei.timelineviewv2.base.BaseCustomView
import ro.dobrescuandrei.timelineviewv2.model.CustomDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.InfiniteDateTimeInterval

class TimelineRecyclerViewCell
(
    context : Context,
    val appearance : TimelineViewAppearance
) : BaseCustomView(context)
{
    fun interface Transformer { fun transform(cell : TimelineRecyclerViewCell, interval : DateTimeInterval) }

    private var _isSelected = false

    override fun getLayoutId() = R.layout.timeline_recycler_view_cell

    fun setDateTimeInterval(dateTimeInterval : DateTimeInterval)
    {
        intervalDescriptionLabel.text = dateTimeInterval.toString(resources = context.resources)
    }

    override fun isSelected() : Boolean
    {
        return _isSelected
    }

    fun setIsSelected(isSelected : Boolean)
    {
        _isSelected = isSelected

        if (_isSelected)
        {
            intervalDescriptionLabel.setTextColor(appearance.selectedCellTextColor)
            intervalDescriptionLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, appearance.selectedCellTextSizeInPx.toFloat())
            cell.setBackgroundColor(appearance.selectedCellBackgroundColor)
            selectedIndicatorView.setBackgroundColor(appearance.selectedCellIndicatorColor)
            selectedIndicatorView.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                appearance.selectedCellIndicatorHeightInPx)
            selectedIndicatorView.visibility = View.VISIBLE
        }
        else
        {
            intervalDescriptionLabel.setTextColor(appearance.unselectedCellTextColor)
            intervalDescriptionLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, appearance.unselectedCellTextSizeInPx.toFloat())
            cell.setBackgroundColor(appearance.unselectedCellBackgroundColor)
            selectedIndicatorView.visibility = View.GONE
        }
    }

    fun setWidthInPixels(width : Int)
    {
        itemsContainer.layoutParams = FrameLayout.LayoutParams(width,
            FrameLayout.LayoutParams.MATCH_PARENT)
    }

    private val cell get() = findViewById<FrameLayout>(R.id.tv___cell)!!
    private val itemsContainer get() = findViewById<FrameLayout>(R.id.tv___itemsContainer)!!
    private val intervalDescriptionLabel get() = findViewById<TextView>(R.id.tv___intervalDescriptionLabel)!!
    private val selectedIndicatorView get() = findViewById<View>(R.id.tv___selectedIndicatorView)!!

    companion object
    {
        fun createTransformerDisablingPast() = createTransformerDisablingPastOrFuture(disablePast = true, disableFuture = false)
        fun createTransformerDisablingFuture() = createTransformerDisablingPastOrFuture(disablePast = false, disableFuture = true)

        fun createTransformerDisablingPastOrFuture(disablePast : Boolean, disableFuture : Boolean) =
        Transformer { cellView, dateTimeInterval ->
            if (dateTimeInterval !is CustomDateTimeInterval && dateTimeInterval !is InfiniteDateTimeInterval)
            {
                val presentInterval = dateTimeInterval::class.java.constructors.find { constructor ->
                    ZonedDateTime::class.java in constructor.parameterTypes
                }!!.newInstance(ZonedDateTime.now(DateTimeInterval.defaultTimezone)) as DateTimeInterval

                if (disablePast && dateTimeInterval.fromDateTime.isBefore(presentInterval.fromDateTime))
                    cellView.setOnClickListener(null)
                if (disableFuture && dateTimeInterval.toDateTime.isAfter(presentInterval.toDateTime))
                    cellView.setOnClickListener(null)
            }
        }
    }
}
