package ro.dobrescuandrei.timelineviewv2

import android.content.Context
import android.content.res.TypedArray
import androidx.core.content.ContextCompat

class TimelineViewAppearance
{
    val selectedCellTextColor : Int
    val selectedCellTextSizeInPx : Int
    val selectedCellBackgroundColor : Int
    val selectedCellIndicatorColor : Int
    val selectedCellIndicatorWidthInPx : Int

    val unselectedCellTextColor : Int
    val unselectedCellTextSizeInPx : Int
    val unselectedCellBackgroundColor : Int

    val upIconResourceId : Int
    val downIconResourceId : Int
    val calendarIconResourceId : Int
    val leftButtonsContainerBackgroundResourceId : Int
    val rightButtonsContainerBackgroundResourceId : Int

    constructor(context : Context)
    {
        selectedCellTextColor=ContextCompat.getColor(context, R.color.default_selected_cell_text_color)
        selectedCellTextSizeInPx=context.resources.getDimensionPixelSize(R.dimen.default_selected_cell_text_size)
        selectedCellBackgroundColor=ContextCompat.getColor(context, R.color.default_selected_cell_background_color)
        selectedCellIndicatorColor=ContextCompat.getColor(context, R.color.default_selected_cell_indicator_color)
        selectedCellIndicatorWidthInPx=context.resources.getDimensionPixelSize(R.dimen.default_selected_cell_indicator_width)

        unselectedCellTextColor=ContextCompat.getColor(context, R.color.default_unselected_cell_text_color)
        unselectedCellTextSizeInPx=context.resources.getDimensionPixelSize(R.dimen.default_unselected_cell_text_size)
        unselectedCellBackgroundColor=ContextCompat.getColor(context, R.color.default_unselected_cell_background_color)

        upIconResourceId=R.drawable.ic_arrow_up_white_18dp
        downIconResourceId=R.drawable.ic_arrow_down_white_18dp
        calendarIconResourceId=R.drawable.ic_calendar_range_outline_white_18dp
        leftButtonsContainerBackgroundResourceId=R.drawable.fading_right_gradient_background
        rightButtonsContainerBackgroundResourceId=R.drawable.fading_left_gradient_background
    }

    constructor(context : Context, attributes : TypedArray)
    {
        selectedCellTextColor=attributes.getColor(R.styleable.TimelineView_tv_selected_cell_text_color,
            ContextCompat.getColor(context, R.color.default_selected_cell_text_color))

        selectedCellTextSizeInPx=attributes.getDimensionPixelSize(R.styleable.TimelineView_tv_selected_cell_text_size,
            context.resources.getDimensionPixelSize(R.dimen.default_selected_cell_text_size))

        selectedCellBackgroundColor=attributes.getColor(R.styleable.TimelineView_tv_selected_cell_background_color,
            ContextCompat.getColor(context, R.color.default_selected_cell_background_color))

        selectedCellIndicatorColor=attributes.getColor(R.styleable.TimelineView_tv_selected_cell_indicator_color,
            ContextCompat.getColor(context, R.color.default_selected_cell_indicator_color))

        selectedCellIndicatorWidthInPx=attributes.getDimensionPixelSize(R.styleable.TimelineView_tv_selected_cell_indicator_width,
            context.resources.getDimensionPixelSize(R.dimen.default_selected_cell_indicator_width))

        unselectedCellTextColor=attributes.getColor(R.styleable.TimelineView_tv_unselected_cell_text_color,
            ContextCompat.getColor(context, R.color.default_unselected_cell_text_color))

        unselectedCellTextSizeInPx=attributes.getDimensionPixelSize(R.styleable.TimelineView_tv_unselected_cell_text_size,
            context.resources.getDimensionPixelSize(R.dimen.default_unselected_cell_text_size))

        unselectedCellBackgroundColor=attributes.getColor(R.styleable.TimelineView_tv_unselected_cell_background_color,
            ContextCompat.getColor(context, R.color.default_unselected_cell_background_color))

        upIconResourceId=attributes.getResourceId(R.styleable.TimelineView_tv_up_icon,
            R.drawable.ic_arrow_up_white_18dp)

        downIconResourceId=attributes.getResourceId(R.styleable.TimelineView_tv_down_icon,
            R.drawable.ic_arrow_down_white_18dp)

        calendarIconResourceId=attributes.getResourceId(R.styleable.TimelineView_tv_calendar_icon,
            R.drawable.ic_calendar_range_outline_white_18dp)

        leftButtonsContainerBackgroundResourceId=attributes.getResourceId(R.styleable.TimelineView_tv_left_buttons_container_background,
            R.drawable.fading_right_gradient_background)

        rightButtonsContainerBackgroundResourceId=attributes.getResourceId(R.styleable.TimelineView_tv_right_buttons_container_background,
            R.drawable.fading_left_gradient_background)
    }
}
