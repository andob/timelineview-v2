package ro.dobrescuandrei.timelineviewv2

import org.joda.time.DateTimeZone
import ro.dobrescuandrei.timelineviewv2.model.*

object TimelineViewDefaults
{
    var timezone : DateTimeZone = DateTimeZone.UTC

    var dateTimeIntervalTypeChangeFlow =
        DateTimeIntervalTypeChangeFlow.build {
            from(DailyDateTimeInterval::class.java)
                .to(WeeklyDateTimeInterval::class.java)
                .to(MonthlyDateTimeInterval::class.java)
                .to(YearlyDateTimeInterval::class.java)
                .to(InfiniteDateTimeInterval::class.java)
        }
}
