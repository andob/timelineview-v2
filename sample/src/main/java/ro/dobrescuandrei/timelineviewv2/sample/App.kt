package ro.dobrescuandrei.timelineviewv2.sample

import android.app.Application
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import java.time.ZoneId

class App : Application()
{
    override fun onCreate()
    {
        super.onCreate()

        DateTimeInterval.defaultTimezone = ZoneId.of("Europe/Bucharest")
    }
}
