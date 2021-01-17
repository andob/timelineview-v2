package ro.dobrescuandrei.timelineviewv2

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses
(
    CustomDateTimeIntervalTest::class,
    DateTimeIntervalConverterTests::class,
    DateTimeIntervalTypeChangeFlowTest::class,
    DailyDateTimeIntervalTests::class,
    InfiniteDateTimeIntervalTest::class,
    JodaTimeExtensionsTest::class,
    MonthlyDateTimeIntervalTest::class,
    OnDateTimeIntervalChangedEventsLockTest::class,
    YearlyDateTimeIntervalTest::class,
    WeeklyDateTimeIntervalTest::class,
)
class AllTestsSuite
