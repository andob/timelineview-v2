package ro.dobrescuandrei.timelineviewv2.utils

import android.view.View
import android.view.ViewTreeObserver

fun View.setOnFirstMeasureListener(delegatedListener : () -> (Unit))
{
    val listener=object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            delegatedListener.invoke()
        }
    }

    viewTreeObserver.addOnGlobalLayoutListener(listener)
}
