package ro.dobrescuandrei.timelineviewv2

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.InfiniteDateTimeInterval
import java.time.format.DateTimeFormatter

class InfiniteDateTimeIntervalTest
{
    private val dateTimeInterval : DateTimeInterval = InfiniteDateTimeInterval()
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS")

    @Before
    fun setup() = setupUnitTests()

    @Test
    fun testIntervalRange()
    {
        assertEquals("01.01.1970 00:00:00.000", dateTimeFormatter.format(dateTimeInterval.fromDateTime))
        assertEquals("01.01.4000 00:00:00.000", dateTimeFormatter.format(dateTimeInterval.toDateTime))
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
        assertEquals("All time", dateTimeInterval.toString(mockResources))
    }
}
