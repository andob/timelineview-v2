package ro.dobrescuandrei.timelineviewv2.utils

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowInsets
import android.view.WindowManager

object ScreenSizeDetector
{
    @JvmStatic
    fun getScreenSize(context : Context) : Point
    {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.R)
        {
            val windowMetrics = (context as Activity).windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            val width = windowMetrics.bounds.width()-insets.left-insets.right
            val height = windowMetrics.bounds.height()-insets.top-insets.bottom
            return Point(width, height)
        }

        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay!!
        val size = Point()
        display.getSize(size)
        return size
    }
}
