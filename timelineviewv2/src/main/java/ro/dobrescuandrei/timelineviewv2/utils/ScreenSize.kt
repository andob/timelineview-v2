package ro.dobrescuandrei.timelineviewv2.utils

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

object ScreenSize
{
    @JvmStatic
    fun get(context : Context) : Point
    {
        val windowManager=context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display=windowManager.defaultDisplay
        val size= Point()
        display.getSize(size)
        return size
    }

    @JvmStatic
    fun width(context : Context) = get(context).x

    @JvmStatic
    fun height(context : Context) = get(context).y
}
