package ro.dobrescuandrei.timelineviewv2.dialog

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.change_date_time_interval_type_dialog_view.view.*
import ro.dobrescuandrei.timelineviewv2.R
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.base.BaseCustomView
import ro.dobrescuandrei.timelineviewv2.model.*

class ChangeDateTimeIntervalTypeDialogView : BaseCustomView
{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun getLayoutId() = R.layout.change_date_time_interval_type_dialog_view

    override fun resolveAttributeSetAfterOnCreate(attributeSet : AttributeSet) {}

    override fun onCreate() {}

    fun setup(timelineView : TimelineView, dialog : AlertDialog)
    {
        val radioButtonsToIntervalTypes=mapOf(
            dailyRadioButton to DailyDateTimeInterval::class.java as Class<DateTimeInterval<*>>,
            weeklyRadioButton to WeeklyDateTimeInterval::class.java as Class<DateTimeInterval<*>>,
            monthlyRadioButton to MonthlyDateTimeInterval::class.java as Class<DateTimeInterval<*>>,
            yearlyRadioButton to YearlyDateTimeInterval::class.java as Class<DateTimeInterval<*>>,
            allTimeRadioButton to InfiniteDateTimeInterval::class.java as Class<DateTimeInterval<*>>,
            customIntervalRadioButton to CustomDateTimeInterval::class.java as Class<DateTimeInterval<*>>)

        if (timelineView.isCustomDateTimeIntervalSupported)
            customIntervalButtonContainer.visibility=View.VISIBLE
        else customIntervalButtonContainer.visibility=View.GONE

        val supportedIntervalTypes=timelineView.dateTimeIntervalTypeChangeFlow.toList()

        for ((radioButton, intervalType) in radioButtonsToIntervalTypes)
        {
            if (supportedIntervalTypes.find { it==intervalType }!=null
               ||intervalType==CustomDateTimeInterval::class.java)
                radioButton.visibility=View.VISIBLE
            else radioButton.visibility=View.GONE

            if (intervalType==timelineView.dateTimeInterval::class.java)
                radioButton.isChecked=true

            radioButton.setOnCheckedChangeListener { radioButton, isChecked ->
                if (radioButton.isChecked)
                {
                    val selectedIntervalType=radioButtonsToIntervalTypes[radioButton]!!

                    timelineView.dateTimeInterval=
                        timelineView.dateTimeIntervalConverter.convert(
                            from = timelineView.dateTimeInterval,
                            to = selectedIntervalType)

                    dialog.dismiss()
                }
            }

            customIntervalButtonOverlayView.setOnClickListener {
                dialog.dismiss()

                val referenceDailyDateTimeInterval=
                    timelineView.dateTimeIntervalConverter.convert(
                        from = timelineView.dateTimeInterval,
                        to = DailyDateTimeInterval::class.java)

                JodaDatePickerDialog.show(
                    context = radioButton.context,
                    title = radioButton.context.getString(R.string.choose_interval_start_date),
                    initialSelectedDateTime = referenceDailyDateTimeInterval.fromDateTime,
                    onDateTimeSelected = { fromDateTime ->

                        JodaDatePickerDialog.show(
                            context = radioButton.context,
                            title = radioButton.context.getString(R.string.choose_interval_end_date),
                            initialSelectedDateTime = fromDateTime,
                            onDateTimeSelected = { toDateTime ->
                                timelineView.dateTimeInterval=CustomDateTimeInterval(fromDateTime, toDateTime)
                            })
                    })
            }
        }
    }
}
