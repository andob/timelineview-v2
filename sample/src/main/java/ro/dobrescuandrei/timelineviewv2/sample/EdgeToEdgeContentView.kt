package ro.dobrescuandrei.timelineviewv2.sample

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@SuppressLint("DiscouragedApi", "InternalInsetResource", "RestrictedApi")
class EdgeToEdgeContentView : LinearLayout
{
    companion object
    {
        private const val NAVIGATION_BAR_STYLE_THREE_BUTTONS = 0
        private const val NAVIGATION_BAR_STYLE_GESTURE = 2
    }
    
    constructor(context : Context) : this(context, View(context))

    constructor(context : Context, layoutResourceId : Int) :
        this(context, LayoutInflater.from(context).inflate(layoutResourceId, null))
    
    constructor(context : Context, contentView : View) : super(context)
    {
        val statusBarHeight = resources.getStatusBarHeight()
        val navigationBarHeight = resources.getNavigationBarHeight()
        val isGestureNavigationEnabled = resources.isGestureNavigationEnabled()
        
        val verticalLayout = this
        verticalLayout.orientation = VERTICAL

        val topStatusBarCoveringView = View(context)
        topStatusBarCoveringView.visibility = View.VISIBLE
        topStatusBarCoveringView.setBackgroundColor(Color.BLACK)
        topStatusBarCoveringView.setLayoutParams(LayoutParams(
            LayoutParams.MATCH_PARENT, statusBarHeight))
        verticalLayout.addView(topStatusBarCoveringView)

        contentView.setLayoutParams(LayoutParams(
            LayoutParams.MATCH_PARENT, 0, 1f))
        verticalLayout.addView(contentView)

        val bottomNavigationBarCoveringView = View(context)
        bottomNavigationBarCoveringView.visibility = View.GONE
        bottomNavigationBarCoveringView.setBackgroundColor(Color.BLACK)
        bottomNavigationBarCoveringView.setLayoutParams(LayoutParams(
            LayoutParams.MATCH_PARENT, navigationBarHeight))
        verticalLayout.addView(bottomNavigationBarCoveringView)

        ViewCompat.setOnApplyWindowInsetsListener(this) { _, windowInsets ->

            val navigationBarInsets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
            if (isGestureNavigationEnabled)
            {
                bottomNavigationBarCoveringView.visibility = View.GONE
            }
            else if (navigationBarInsets.bottom>0)
            {
                //three button navigation mode, portrait
                bottomNavigationBarCoveringView.visibility = View.VISIBLE
            }
            else
            {
                //three button navigation mode, landscape
                bottomNavigationBarCoveringView.visibility = View.GONE
            }

            return@setOnApplyWindowInsetsListener windowInsets
        }
    }

    private fun Resources.getStatusBarHeight() : Int
    {
        return try { getInteger(getIdentifier("status_bar_height", "dimen", "android")) }
        catch (ignored : Throwable) { 60 }
    }
    
    private fun Resources.getNavigationBarHeight() : Int
    {
        return try { getInteger(getIdentifier("navigation_bar_height", "dimen", "android")) }
        catch (ignored : Throwable) { 125 }
    }
    
    private fun Resources.getNavigationBarStyle() : Int
    {
        return try { getInteger(getIdentifier("config_navBarInteractionMode", "integer", "android")) }
        catch (ignored : Throwable) { NAVIGATION_BAR_STYLE_THREE_BUTTONS }
    }
    
    private fun Resources.isGestureNavigationEnabled() : Boolean
    {
        return getNavigationBarStyle() == NAVIGATION_BAR_STYLE_GESTURE
    }
}
