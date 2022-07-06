package ro.dobrescuandrei.timelineviewv2

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ro.dobrescuandrei.timelineviewv2.model.CustomDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval

class CustomDateTimeIntervalTest
{
    private val dateTimeInterval : DateTimeInterval = CustomDateTimeInterval(
        fromDateTime = DateTime(2022, 1, 12, 0, 0, 0, 0),
        toDateTime = DateTime(2022, 1, 26, 0, 0, 0, 0))

    private val dateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss.SSS")!!

    @Before
    fun setup() = setupUnitTests()

    @Test
    fun testIntervalRange()
    {
        assertEquals("12.01.2022 00:00:00.000", dateTimeFormatter.print(dateTimeInterval.fromDateTime))
        assertEquals("26.01.2022 23:59:59.999", dateTimeFormatter.print(dateTimeInterval.toDateTime))
    }

    @Test
    fun testIntervalShifting()
    {
        assert(dateTimeInterval.getPreviousDateTimeInterval()==null)
        assert(dateTimeInterval.getNextDateTimeInterval()==null)
        assert((-100..100).map { dateTimeInterval.getShiftedDateTimeInterval(it) }.all { it==null })
    }

    @Test
    fun testIntervalToString()
    {
        assertEquals("12 Jan - 26 Jan", dateTimeInterval.toString(mockResources))
    }

    @Test
    fun testInvertedIntervalEqualsOriginalInterval()
    {
        val invertedDateTimeInterval=CustomDateTimeInterval(
            fromDateTime = DateTime(2022, 1, 26, 0, 0, 0, 0),
            toDateTime = DateTime(2022, 1, 12, 0, 0, 0, 0))

        assertEquals(dateTimeInterval.fromDateTime, invertedDateTimeInterval.fromDateTime)
        assertEquals(dateTimeInterval.toDateTime, invertedDateTimeInterval.toDateTime)
    }
}
