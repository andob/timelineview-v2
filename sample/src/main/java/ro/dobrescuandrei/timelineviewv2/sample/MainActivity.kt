package ro.dobrescuandrei.timelineviewv2.sample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.graphics.Color
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import ro.dobrescuandrei.timelineviewv2.TimelineView
import ro.dobrescuandrei.timelineviewv2.model.DailyDateTimeInterval

class MainActivity : AppCompatActivity()
{
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState : Bundle?)
    {
        val statusBarStyle = SystemBarStyle.dark(ContextCompat.getColor(this, R.color.colorPrimary))
        val navigationBarStyle = SystemBarStyle.dark(Color.BLACK)
        enableEdgeToEdge(statusBarStyle, navigationBarStyle)

        super.onCreate(savedInstanceState)
        setContentView(EdgeToEdgeContentView(this, R.layout.activity_main))

        val timelineView = findViewById<TimelineView>(R.id.timelineView)!!
        val timeIntervalLabel = findViewById<TextView>(R.id.timeIntervalLabel)!!

        timelineView.dateTimeInterval = DailyDateTimeInterval.today()
        timelineView.setOnDateTimeIntervalChangedListener { dateTimeInterval ->
            timeIntervalLabel.text = dateTimeInterval.toString(resources = resources)
        }
    }
}
