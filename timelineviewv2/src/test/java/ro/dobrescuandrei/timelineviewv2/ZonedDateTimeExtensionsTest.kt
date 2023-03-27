package ro.dobrescuandrei.timelineviewv2

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import ro.dobrescuandrei.timelineviewv2.utils.*
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ZonedDateTimeExtensionsTest
{
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS")

    @Before
    fun setup() = setupUnitTests()

    @Test
    fun testDateTimeAtBeginningOfDay()
    {
        val dateTime=ZonedDateTime.of(LocalDateTime.of(2021, 1, 17, 16, 19, 0, 0), DateTimeInterval.defaultTimezone)
        assertEquals("17.01.2021 16:19:00.000", dateTimeFormatter.format(dateTime))
        assertEquals("17.01.2021 00:00:00.000", dateTimeFormatter.format(dateTime.atBeginningOfDay()))
    }

    @Test
    fun testDateTimeAtEndOfDay()
    {
        val dateTime=ZonedDateTime.of(LocalDateTime.of(2021, 1, 17, 16, 21, 0, 0), DateTimeInterval.defaultTimezone)
        assertEquals("17.01.2021 16:21:00.000", dateTimeFormatter.format(dateTime))
        assertEquals("17.01.2021 23:59:59.999", dateTimeFormatter.format(dateTime.atEndOfDay()))
    }

    @Test
    fun testMinimumFromDateTimes()
    {
        val dateTime1=ZonedDateTime.of(LocalDateTime.of(2021, 1, 16, 16, 21, 0, 0), DateTimeInterval.defaultTimezone)
        val dateTime2=ZonedDateTime.of(LocalDateTime.of(2021, 1, 17, 16, 21, 0, 0), DateTimeInterval.defaultTimezone)
        val dateTime3=ZonedDateTime.of(LocalDateTime.of(2021, 1, 18, 16, 21, 0, 0), DateTimeInterval.defaultTimezone)

        assertEquals(dateTime1, min(dateTime1, dateTime2))
        assertEquals(dateTime1, min(dateTime1, dateTime3))
        assertEquals(dateTime2, min(dateTime2, dateTime3))
    }

    @Test
    fun testMaximumFromDateTimes()
    {
        val dateTime1=ZonedDateTime.of(LocalDateTime.of(2021, 1, 16, 16, 21, 0, 0), DateTimeInterval.defaultTimezone)
        val dateTime2=ZonedDateTime.of(LocalDateTime.of(2021, 1, 17, 16, 21, 0, 0), DateTimeInterval.defaultTimezone)
        val dateTime3=ZonedDateTime.of(LocalDateTime.of(2021, 1, 18, 16, 21, 0, 0), DateTimeInterval.defaultTimezone)

        assertEquals(dateTime2, max(dateTime1, dateTime2))
        assertEquals(dateTime3, max(dateTime1, dateTime3))
        assertEquals(dateTime3, max(dateTime2, dateTime3))
    }
}
