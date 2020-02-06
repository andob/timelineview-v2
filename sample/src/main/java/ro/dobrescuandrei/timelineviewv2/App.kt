package ro.dobrescuandrei.timelineviewv2

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid
import org.joda.time.DateTimeZone
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
