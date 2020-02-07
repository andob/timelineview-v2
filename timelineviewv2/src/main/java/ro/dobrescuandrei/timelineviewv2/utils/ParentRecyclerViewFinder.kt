package ro.dobrescuandrei.timelineviewv2.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun View.getParentRecyclerView() : RecyclerView?
{
    if (parent!=null)
    {
        if (parent is RecyclerView)
            return parent as RecyclerView

        return (parent as View).getParentRecyclerView()
    }

    return null
}
