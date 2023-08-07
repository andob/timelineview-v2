package ro.dobrescuandrei.timelineviewv2

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ro.dobrescuandrei.timelineviewv2.model.DailyDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DateTimeIntervalConverter
import ro.dobrescuandrei.timelineviewv2.model.WeeklyDateTimeInterval
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class WeeklyDateTimeIntervalTest
{
    private val dateTimeInterval : DateTimeInterval = WeeklyDateTimeInterval(
        referenceDateTime = LocalDateTime.of(2006, 1, 20, 0, 0, 0, 0))

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS")

    @Before
    fun setup() = setupUnitTests()

    @Test
    fun testIntervalRange()
    {
        assertEquals("16.01.2006 00:00:00.000", dateTimeFormatter.format(dateTimeInterval.fromDateTime))
        assertEquals("22.01.2006 23:59:59.999", dateTimeFormatter.format(dateTimeInterval.toDateTime))
    }

    @Test
    fun testIntervalShifting()
    {
        assertEquals("16 - 22 Jan 2006", dateTimeInterval.toString(mockResources))

        val nextWeek = dateTimeInterval.getNextDateTimeInterval()!!
        assertEquals("23.01.2006 00:00:00.000", dateTimeFormatter.format(nextWeek.fromDateTime))
        assertEquals("29.01.2006 23:59:59.999", dateTimeFormatter.format(nextWeek.toDateTime))
        assertEquals("23 - 29 Jan 2006", nextWeek.toString(mockResources))

        val nextNextWeek = dateTimeInterval.getShiftedDateTimeInterval(2)!!
        assertEquals("30.01.2006 00:00:00.000", dateTimeFormatter.format(nextNextWeek.fromDateTime))
        assertEquals("05.02.2006 23:59:59.999", dateTimeFormatter.format(nextNextWeek.toDateTime))
        assertEquals("30 Jan - 05 Feb 2006", nextNextWeek.toString(mockResources))

        val previousWeek = dateTimeInterval.getPreviousDateTimeInterval()!!
        assertEquals("09.01.2006 00:00:00.000", dateTimeFormatter.format(previousWeek.fromDateTime))
        assertEquals("15.01.2006 23:59:59.999", dateTimeFormatter.format(previousWeek.toDateTime))
        assertEquals("09 - 15 Jan 2006", previousWeek.toString(mockResources))

        val prevPreviousWeek = dateTimeInterval.getShiftedDateTimeInterval(-2)!!
        assertEquals("02.01.2006 00:00:00.000", dateTimeFormatter.format(prevPreviousWeek.fromDateTime))
        assertEquals("08.01.2006 23:59:59.999", dateTimeFormatter.format(prevPreviousWeek.toDateTime))
        assertEquals("02 - 08 Jan 2006", prevPreviousWeek.toString(mockResources))
    }

    @Test
    fun testIntervalAroundToday()
    {
        fun Int.withLeadingZero() = if(this<10) "0$this" else "$this"

        fun ZonedDateTime.formatted() = "${dayOfMonth.withLeadingZero()}.${monthValue.withLeadingZero()}.$year"

        val formattedMinDay = DateTimeIntervalConverter().convert(
            from = DailyDateTimeInterval.today(),
            to = WeeklyDateTimeInterval::class.java
        ).fromDateTime.formatted()

        val formattedMaxDay = DateTimeIntervalConverter().convert(
            from = DailyDateTimeInterval.today(),
            to = WeeklyDateTimeInterval::class.java
        ).toDateTime.formatted()

        val interval = WeeklyDateTimeInterval.aroundToday()
        assertEquals("$formattedMinDay 00:00:00.000", dateTimeFormatter.format(interval.fromDateTime))
        assertEquals("$formattedMaxDay 23:59:59.999", dateTimeFormatter.format(interval.toDateTime))
    }
}
