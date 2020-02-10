package ro.dobrescuandrei.timelineviewv2.sample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ro.dobrescuandrei.timelineviewv2.DateTimeIntervalTypeChangeFlow
import ro.dobrescuandrei.timelineviewv2.model.DailyDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.MonthlyDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.YearlyDateTimeInterval
import java.time.Month

class MainActivity : AppCompatActivity()
{
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        timelineView.dateTimeIntervalTypeChangeFlow=DateTimeIntervalTypeChangeFlow.build {
//            from(DailyDateTimeInterval::class.java)
//                .to(MonthlyDateTimeInterval::class.java)
//                .to(YearlyDateTimeInterval::class.java)
//        }
//
//        timelineView.dateTimeInterval=MonthlyDateTimeInterval.aroundToday()

        timelineView.setOnDateTimeIntervalChangedListener { dateTimeInterval ->
            timeIntervalLabel.text=dateTimeInterval.toString(resources = resources)
        }
    }
}
