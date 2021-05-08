package ro.dobrescuandrei.timelineviewv2

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ro.dobrescuandrei.timelineviewv2.utils.*

class JodaTimeExtensionsTest
{
    private val dateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss.SSS")

    @Before
    fun setup() = setupUnitTests()

    @Test
    fun testDateTimeAtBeginningOfDay()
    {
        val dateTime=DateTime(2021, 1, 17, 16, 19, 0, 0)
        assertEquals("17.01.2021 16:19:00.000", dateTimeFormatter.print(dateTime))
        assertEquals("17.01.2021 00:00:00.000", dateTimeFormatter.print(dateTime.atBeginningOfDay()))
    }

    @Test
    fun testDateTimeAtEndOfDay()
    {
        val dateTime=DateTime(2021, 1, 17, 16, 21, 0, 0)
        assertEquals("17.01.2021 16:21:00.000", dateTimeFormatter.print(dateTime))
        assertEquals("17.01.2021 23:59:59.999", dateTimeFormatter.print(dateTime.atEndOfDay()))
    }

    @Test
    fun testMinimumFromDateTimes()
    {
        val dateTime1=DateTime(2021, 1, 16, 16, 21, 0, 0)
        val dateTime2=DateTime(2021, 1, 17, 16, 21, 0, 0)
        val dateTime3=DateTime(2021, 1, 18, 16, 21, 0, 0)

        assertEquals(dateTime1, min(dateTime1, dateTime2))
        assertEquals(dateTime1, min(dateTime1, dateTime3))
        assertEquals(dateTime2, min(dateTime2, dateTime3))
    }

    @Test
    fun testMaximumFromDateTimes()
    {
        val dateTime1=DateTime(2021, 1, 16, 16, 21, 0, 0)
        val dateTime2=DateTime(2021, 1, 17, 16, 21, 0, 0)
        val dateTime3=DateTime(2021, 1, 18, 16, 21, 0, 0)

        assertEquals(dateTime2, max(dateTime1, dateTime2))
        assertEquals(dateTime3, max(dateTime1, dateTime3))
        assertEquals(dateTime3, max(dateTime2, dateTime3))
    }
}
