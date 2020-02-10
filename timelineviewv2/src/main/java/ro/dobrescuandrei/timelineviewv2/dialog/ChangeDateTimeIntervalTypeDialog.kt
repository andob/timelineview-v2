package ro.dobrescuandrei.timelineviewv2.dialog

import androidx.appcompat.app.AlertDialog
import ro.dobrescuandrei.timelineviewv2.R
import ro.dobrescuandrei.timelineviewv2.TimelineView

object ChangeDateTimeIntervalTypeDialog
{
    @JvmStatic
    fun show(timelineView : TimelineView)
    {
        val context=timelineView.context!!

        val dialogView=ChangeDateTimeIntervalTypeDialogView(context)
        
        val dialog=AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.change_interval_type))
            .setView(dialogView)
            .show()!!

        dialogView.setup(timelineView = timelineView, dialog = dialog)
    }
}
