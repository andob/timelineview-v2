package ro.dobrescuandrei.timelineviewv2.dialog

import android.app.DatePickerDialog
import android.content.Context
import org.joda.time.DateTime
import ro.dobrescuandrei.timelineviewv2.TimelineViewDefaults

object JodaDatePickerDialog
{
    @JvmStatic
    fun show(context : Context,
             title : String? = null,
             initialSelectedDateTime : DateTime = DateTime.now(TimelineViewDefaults.timezone),
             onDateTimeSelected : ((DateTime) -> (Unit))? = null)
    {
        val pickerDialog=DatePickerDialog(context,
            { datePicker, year, month, dayOfMonth ->
                val dateTime=DateTime(year, month+1, dayOfMonth, 0, 0, TimelineViewDefaults.timezone)
                onDateTimeSelected?.invoke(dateTime)
            },
            initialSelectedDateTime.year,
            initialSelectedDateTime.monthOfYear-1,
            initialSelectedDateTime.dayOfMonth)

        if (title!=null)
            pickerDialog.setTitle(title)

        pickerDialog.show()
    }
}
