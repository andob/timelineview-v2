package ro.dobrescuandrei.timelineviewv2.dialog

import android.app.DatePickerDialog
import android.content.Context
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

object ZonedDateTimePickerDialog
{
    @JvmStatic
    fun show
    (
        context : Context,
        title : String? = null,
        timezone : ZoneId = DateTimeInterval.defaultTimezone,
        initialSelectedDateTime : ZonedDateTime = ZonedDateTime.now(timezone),
        onDateTimeSelected : ((ZonedDateTime) -> Unit)? = null,
    )
    {
        val pickerDialog = DatePickerDialog(context,
            { _, year, month, dayOfMonth ->
                val localDateTime = LocalDateTime.of(year, month+1, dayOfMonth, 0, 0, 0)
                val zonedDateTime = ZonedDateTime.of(localDateTime, timezone)
                onDateTimeSelected?.invoke(zonedDateTime)
            },
            initialSelectedDateTime.year,
            initialSelectedDateTime.monthValue-1,
            initialSelectedDateTime.dayOfMonth)

        if (title!=null)
            pickerDialog.setTitle(title)

        pickerDialog.show()
    }
}
