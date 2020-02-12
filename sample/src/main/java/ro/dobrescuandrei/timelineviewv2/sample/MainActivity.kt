package ro.dobrescuandrei.timelineviewv2.sample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.model.DailyDateTimeInterval

class MainActivity : AppCompatActivity()
{
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timelineView=findViewById<TimelineView>(R.id.timelineView)!!
        val timeIntervalLabel=findViewById<TextView>(R.id.timeIntervalLabel)!!

//        timelineView.dateTimeIntervalTypeChangeFlow=DateTimeIntervalTypeChangeFlow.build {
//            from(DailyDateTimeInterval::class.java)
//        }
//
//        timelineView.isCustomDateTimeIntervalSupported=false
//
//        timelineView.timelineRecyclerViewCellTransformer=object : TimelineRecyclerViewCell.Transformer {
//            override fun transform(cellView : TimelineRecyclerViewCell, dateTimeInterval : DateTimeInterval) {
//                val todayAndNow=DateTime(TimelineViewDefaults.timezone)
//                if (dateTimeInterval.fromDateTime.isAfter(todayAndNow))
//                    cellView.setOnClickListener(null)
//                if (dateTimeInterval.toDateTime.isBefore(todayAndNow))
//                    cellView.setOnClickListener(null)
//            }
//        }

        timelineView.setOnDateTimeIntervalChangedListener { dateTimeInterval ->
            timeIntervalLabel.text=dateTimeInterval.toString(resources = resources)
        }

        timelineView.dateTimeInterval=DailyDateTimeInterval.today()
    }
}
