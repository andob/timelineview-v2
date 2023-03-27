package ro.dobrescuandrei.timelineviewv2

import android.content.res.Resources
import org.mockito.Mockito
import ro.dobrescuandrei.timelineviewv2.model.*
import java.time.ZoneId

private var areUnitTestsInitialized = false

val Int.minutesInSeconds get() = this*60
val Int.hoursInSeconds get() = (this*60).minutesInSeconds

lateinit var mockResources : Resources

fun setupUnitTests()
{
    if (areUnitTestsInitialized)
        return

    DateTimeInterval.defaultTimezone=ZoneId.of("Europe/Bucharest")

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