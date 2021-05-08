package ro.dobrescuandrei.timelineviewv2

import org.joda.time.format.DateTimeFormat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.InfiniteDateTimeInterval

class InfiniteDateTimeIntervalTest
{
    private val dateTimeInterval : DateTimeInterval = InfiniteDateTimeInterval()
    private val dateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss.SSS")

    @Before
    fun setup() = setupUnitTests()

    @Test
    fun testIntervalRange()
    {
        assertEquals("01.01.1970 02:00:00.001", dateTimeFormatter.print(dateTimeInterval.fromDateTime))
        assertEquals("01.01.4000 02:00:00.000", dateTimeFormatter.print(dateTimeInterval.toDateTime))
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
        assertEquals("All time", dateTimeInterval.toString(mockResources))
    }
}
