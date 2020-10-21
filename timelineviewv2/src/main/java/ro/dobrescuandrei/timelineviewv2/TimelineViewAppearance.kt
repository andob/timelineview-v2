package ro.dobrescuandrei.timelineviewv2

import android.content.Context
import android.content.res.TypedArray
import androidx.core.content.ContextCompat

class TimelineViewAppearance
{
    var selectedCellTextColor : Int
    var selectedCellTextSizeInPx : Int
    var selectedCellBackgroundColor : Int
    var selectedCellIndicatorColor : Int
    var selectedCellIndicatorHeightInPx : Int

    var unselectedCellTextColor : Int
    var unselectedCellTextSizeInPx : Int
    var unselectedCellBackgroundColor : Int

    var upIconResourceId : Int
    var downIconResourceId : Int
    var calendarIconResourceId : Int
    var leftButtonsContainerBackgroundResourceId : Int
    var rightButtonsContainerBackgroundResourceId : Int

    constructor(context : Context)
    {
        selectedCellTextColor=ContextCompat.getColor(context, R.color.default_selected_cell_text_color)
        selectedCellTextSizeInPx=context.resources.getDimensionPixelSize(R.dimen.default_selected_cell_text_size)
        selectedCellBackgroundColor=ContextCompat.getColor(context, R.color.default_selected_cell_background_color)
        selectedCellIndicatorColor=ContextCompat.getColor(context, R.color.default_selected_cell_indicator_color)
        selectedCellIndicatorHeightInPx=context.resources.getDimensionPixelSize(R.dimen.default_selected_cell_indicator_height)

        unselectedCellTextColor=ContextCompat.getColor(context, R.color.default_unselected_cell_text_color)
        unselectedCellTextSizeInPx=context.resources.getDimensionPixelSize(R.dimen.default_unselected_cell_text_size)
        unselectedCellBackgroundColor=ContextCompat.getColor(context, R.color.default_unselected_cell_background_color)

        upIconResourceId=R.drawable.ic_arrow_up_white_24dp
        downIconResourceId=R.drawable.ic_arrow_down_white_24dp
        calendarIconResourceId=R.drawable.ic_calendar_range_outline_white_24dp
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

        selectedCellIndicatorHeightInPx=attributes.getDimensionPixelSize(R.styleable.TimelineView_tv_selected_cell_indicator_width,
            context.resources.getDimensionPixelSize(R.dimen.default_selected_cell_indicator_height))

        unselectedCellTextColor=attributes.getColor(R.styleable.TimelineView_tv_unselected_cell_text_color,
            ContextCompat.getColor(context, R.color.default_unselected_cell_text_color))

        unselectedCellTextSizeInPx=attributes.getDimensionPixelSize(R.styleable.TimelineView_tv_unselected_cell_text_size,
            context.resources.getDimensionPixelSize(R.dimen.default_unselected_cell_text_size))

        unselectedCellBackgroundColor=attributes.getColor(R.styleable.TimelineView_tv_unselected_cell_background_color,
            ContextCompat.getColor(context, R.color.default_unselected_cell_background_color))

        upIconResourceId=attributes.getResourceId(R.styleable.TimelineView_tv_up_icon,
            R.drawable.ic_arrow_up_white_24dp)

        downIconResourceId=attributes.getResourceId(R.styleable.TimelineView_tv_down_icon,
            R.drawable.ic_arrow_down_white_24dp)

        calendarIconResourceId=attributes.getResourceId(R.styleable.TimelineView_tv_calendar_icon,
            R.drawable.ic_calendar_range_outline_white_24dp)

        leftButtonsContainerBackgroundResourceId=attributes.getResourceId(R.styleable.TimelineView_tv_left_buttons_container_background,
            R.drawable.fading_right_gradient_background)

        rightButtonsContainerBackgroundResourceId=attributes.getResourceId(R.styleable.TimelineView_tv_right_buttons_container_background,
            R.drawable.fading_left_gradient_background)
    }
}
