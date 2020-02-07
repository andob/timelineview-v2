package ro.dobrescuandrei.timelineviewv2

import org.joda.time.DateTimeZone
import ro.dobrescuandrei.timelineviewv2.model.DailyDateTimeInterval

object TimelineViewDefaults
{
    var timezone : DateTimeZone = DateTimeZone.UTC

    var dateTimeIntervalFactory = { DailyDateTimeInterval() }
}
