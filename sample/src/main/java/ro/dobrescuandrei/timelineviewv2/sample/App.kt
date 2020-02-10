package ro.dobrescuandrei.timelineviewv2.sample

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid
import org.joda.time.DateTimeZone
import ro.dobrescuandrei.timelineviewv2.TimelineViewDefaults
import java.util.*

class App : Application()
{
    override fun onCreate()
    {
        super.onCreate()

        JodaTimeAndroid.init(this)

        TimelineViewDefaults.timezone=DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Bucharest"))
    }
}
