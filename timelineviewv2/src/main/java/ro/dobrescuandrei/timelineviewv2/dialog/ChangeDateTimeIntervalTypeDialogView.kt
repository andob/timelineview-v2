package ro.dobrescuandrei.timelineviewv2.dialog

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RadioButton
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import ro.dobrescuandrei.timelineviewv2.R
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.base.BaseCustomView
import ro.dobrescuandrei.timelineviewv2.model.*

class ChangeDateTimeIntervalTypeDialogView : BaseCustomView
{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun getLayoutId() = R.layout.change_date_time_interval_type_dialog_view

    fun setup(timelineView : TimelineView, dialog : AlertDialog)
    {
        setupRadioButtons(timelineView, dialog)

        if (timelineView.appearance.isCustomDateTimeIntervalSupported)
        {
            customIntervalButtonContainer.visibility=View.VISIBLE
            setupCustomIntervalButton(timelineView, dialog)
        }
        else
        {
            customIntervalButtonContainer.visibility=View.GONE
            customIntervalButtonOverlayView.setOnClickListener(null)
        }
    }

    private fun setupRadioButtons(timelineView : TimelineView, dialog : AlertDialog)
    {
        val radioButtonsToIntervalTypes=mapOf(
            dailyRadioButton to DailyDateTimeInterval::class.java as Class<DateTimeInterval>,
            weeklyRadioButton to WeeklyDateTimeInterval::class.java as Class<DateTimeInterval>,
            monthlyRadioButton to MonthlyDateTimeInterval::class.java as Class<DateTimeInterval>,
            yearlyRadioButton to YearlyDateTimeInterval::class.java as Class<DateTimeInterval>,
            allTimeRadioButton to InfiniteDateTimeInterval::class.java as Class<DateTimeInterval>,
            customIntervalRadioButton to CustomDateTimeInterval::class.java as Class<DateTimeInterval>)

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
                        DateTimeIntervalConverter().convert(
                            from = timelineView.dateTimeInterval,
                            to = selectedIntervalType)

                    dialog.dismiss()
                }
            }
        }
    }

    private fun setupCustomIntervalButton(timelineView : TimelineView, dialog : AlertDialog)
    {
        customIntervalButtonOverlayView.setOnClickListener {
            dialog.dismiss()

            val referenceDailyDateTimeInterval=
                DateTimeIntervalConverter().convert(
                    from = timelineView.dateTimeInterval,
                    to = DailyDateTimeInterval::class.java)

            ZonedDateTimePickerDialog.show(
                context = timelineView.context,
                title = timelineView.context.getString(R.string.choose_interval_start_date),
                initialSelectedDateTime = referenceDailyDateTimeInterval.fromDateTime,
                onDateTimeSelected = { fromDateTime ->

                    ZonedDateTimePickerDialog.show(
                        context = timelineView.context,
                        title = timelineView.context.getString(R.string.choose_interval_end_date),
                        initialSelectedDateTime = fromDateTime,
                        onDateTimeSelected = { toDateTime ->
                            timelineView.dateTimeInterval=CustomDateTimeInterval(fromDateTime, toDateTime)
                        })
                })
        }
    }

    private val dailyRadioButton get() = findViewById<RadioButton>(R.id.tv___dailyRadioButton)!!
    private val weeklyRadioButton get() = findViewById<RadioButton>(R.id.tv___weeklyRadioButton)!!
    private val monthlyRadioButton get() = findViewById<RadioButton>(R.id.tv___monthlyRadioButton)!!
    private val yearlyRadioButton get() = findViewById<RadioButton>(R.id.tv___yearlyRadioButton)!!
    private val allTimeRadioButton get() = findViewById<RadioButton>(R.id.tv___allTimeRadioButton)!!
    private val customIntervalButtonContainer get() = findViewById<RelativeLayout>(R.id.tv___customIntervalButtonContainer)!!
    private val customIntervalRadioButton get() = findViewById<RadioButton>(R.id.tv___customIntervalRadioButton)!!
    private val customIntervalButtonOverlayView get() = findViewById<View>(R.id.tv___customIntervalButtonOverlayView)!!
}
