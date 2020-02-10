package ro.dobrescuandrei.timelineviewv2.recycler

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.timeline_recycler_view_cell.view.*
import ro.dobrescuandrei.timelineviewv2.R
import ro.dobrescuandrei.timelineviewv2.base.BaseCustomView
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval

class TimelineRecyclerViewCell : BaseCustomView
{
    private var _isSelected = false

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun getLayoutId() = R.layout.timeline_recycler_view_cell

    override fun resolveAttributeSetAfterOnCreate(attributeSet : AttributeSet) {}

    override fun onCreate() {}

    fun setDateTimeInterval(dateTimeInterval : DateTimeInterval<*>)
    {
        intervalDescriptionLabel.text=dateTimeInterval.toString(resources = context.resources)
    }

    override fun isSelected() : Boolean
    {
        return _isSelected
    }

    fun setIsSelected(isSelected : Boolean)
    {
        _isSelected=isSelected

        if (_isSelected)
            selectedIndicatorView.visibility=View.VISIBLE
        else selectedIndicatorView.visibility=View.GONE
    }

    fun setWidthInPixels(width : Int)
    {
        itemsContainer.layoutParams=FrameLayout.LayoutParams(width,
            FrameLayout.LayoutParams.MATCH_PARENT)
    }
}
