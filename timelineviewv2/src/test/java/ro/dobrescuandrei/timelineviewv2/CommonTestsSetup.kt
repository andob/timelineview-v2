package ro.dobrescuandrei.timelineviewv2

import android.content.res.Resources
import org.joda.time.DateTimeZone
import org.mockito.Mockito
import ro.dobrescuandrei.timelineviewv2.model.*
import java.text.SimpleDateFormat
import java.util.*

private var areUnitTestsInitialized = false

val Int.secondsInMills get() = this*1000
val Int.minutesInMills get() = (this*60).secondsInMills
val Int.hoursInMills get() = (this*60).minutesInMills
val Int.daysInMills get() = (this*24).hoursInMills

lateinit var mockResources : Resources

fun newSimpleDateFormat(pattern : String) : SimpleDateFormat
{
    val simpleDateFormat=SimpleDateFormat(pattern)
    simpleDateFormat.timeZone=TimelineViewDefaults.timezone.toTimeZone()
    return simpleDateFormat
}

fun setupUnitTests()
{
    if (areUnitTestsInitialized)
        return

    TimelineViewDefaults.timezone=DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Bucharest"))

    TimelineViewDefaults.dateTimeIntervalTypeChangeFlowFactory=factory@ {
        return@factory DateTimeIntervalTypeChangeFlow.build {
            from(DailyDateTimeInterval::class.java)
                .to(WeeklyDateTimeInterval::class.java)
                .to(MonthlyDateTimeInterval::class.java)
                .to(YearlyDateTimeInterval::class.java)
                .to(InfiniteDateTimeInterval::class.java)
        }
    }

    mockResources=Mockito.mock(Resources::class.java)
    Mockito.`when`(mockResources.getString(R.string.all_time)).thenReturn("All time")
    Mockito.`when`(mockResources.getString(R.string.yesterday)).thenReturn("Yesterday")
    Mockito.`when`(mockResources.getString(R.string.today)).thenReturn("Today")
    Mockito.`when`(mockResources.getString(R.string.tomorrow)).thenReturn("Tomorrow")
    Mockito.`when`(mockResources.getString(R.string.change_interval_type)).thenReturn("Change interval type…")
    Mockito.`when`(mockResources.getString(R.string.daily)).thenReturn("Daily")
    Mockito.`when`(mockResources.getString(R.string.weekly)).thenReturn("Weekly")
    Mockito.`when`(mockResources.getString(R.string.monthly)).thenReturn("Monthly")
    Mockito.`when`(mockResources.getString(R.string.yearly)).thenReturn("Yearly")
    Mockito.`when`(mockResources.getString(R.string.custom_interval)).thenReturn("Custom interval")
    Mockito.`when`(mockResources.getString(R.string.choose_interval_start_date)).thenReturn("Choose interval start date")
    Mockito.`when`(mockResources.getString(R.string.choose_interval_end_date)).thenReturn("Choose interval end date")

    areUnitTestsInitialized=true
}