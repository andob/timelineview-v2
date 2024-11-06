package ro.dobrescuandrei.timelineviewv2

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ro.dobrescuandrei.timelineviewv2.model.CustomDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class CustomDateTimeIntervalTest
{
    private val currentYear = Calendar.getInstance()[Calendar.YEAR]

    private val dateTimeInterval : DateTimeInterval = CustomDateTimeInterval(
        fromDateTime = LocalDateTime.of(currentYear, 1, 12, 0, 0, 0, 0),
        toDateTime = LocalDateTime.of(currentYear, 1, 26, 0, 0, 0, 0))

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS")!!

    @Before
    fun setup() = setupUnitTests()

    @Test
    fun testIntervalRange()
    {
        assertEquals("12.01.$currentYear 00:00:00.000", dateTimeFormatter.format(dateTimeInterval.fromDateTime))
        assertEquals("26.01.$currentYear 23:59:59.999", dateTimeFormatter.format(dateTimeInterval.toDateTime))
    }

    @Test
    fun testIntervalShifting()
    {
        assert(dateTimeInterval.getPreviousDateTimeInterval()==null)
        assert(dateTimeInterval.getNextDateTimeInterval()==null)
        assert((-100..100).map { dateTimeInterval.getShiftedDateTimeInterval(it.toLong()) }.all { it==null })
    }

    @Test
    fun testIntervalToString()
    {
        assertEquals("12 Jan - 26 Jan", dateTimeInterval.toString(mockResources))
    }

    @Test
    fun testInvertedIntervalEqualsOriginalInterval()
    {
        val invertedDateTimeInterval = CustomDateTimeInterval(
            fromDateTime = LocalDateTime.of(currentYear, 1, 26, 0, 0, 0, 0),
            toDateTime = LocalDateTime.of(currentYear, 1, 12, 0, 0, 0, 0))

        assertEquals(dateTimeInterval.fromDateTime, invertedDateTimeInterval.fromDateTime)
        assertEquals(dateTimeInterval.toDateTime, invertedDateTimeInterval.toDateTime)
    }
}
