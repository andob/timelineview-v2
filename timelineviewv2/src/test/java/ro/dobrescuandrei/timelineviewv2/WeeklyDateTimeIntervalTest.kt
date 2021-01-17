package ro.dobrescuandrei.timelineviewv2

import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ro.dobrescuandrei.timelineviewv2.model.DailyDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DateTimeIntervalConverter
import ro.dobrescuandrei.timelineviewv2.model.WeeklyDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.utils.formatJodaDateTime

class WeeklyDateTimeIntervalTest
{
    private val dateTimeInterval : DateTimeInterval = WeeklyDateTimeInterval(
        referenceDateTime = DateTime(2006, 1, 20, 0, 0, 0, 0))

    private val dateTimeFormatter = newSimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS")

    @Before
    fun setup() = setupUnitTests()

    @Test
    fun testIntervalRange()
    {
        assertEquals("16.01.2006 00:00:00.000", dateTimeFormatter.formatJodaDateTime(dateTimeInterval.fromDateTime))
        assertEquals("22.01.2006 23:59:59.999", dateTimeFormatter.formatJodaDateTime(dateTimeInterval.toDateTime))
    }

    @Test
    fun testIntervalShifting()
    {
        assertEquals("16 - 22 Jan 2006", dateTimeInterval.toString(mockResources))

        val nextWeek=dateTimeInterval.getNextDateTimeInterval()!!
        assertEquals("23.01.2006 00:00:00.000", dateTimeFormatter.formatJodaDateTime(nextWeek.fromDateTime))
        assertEquals("29.01.2006 23:59:59.999", dateTimeFormatter.formatJodaDateTime(nextWeek.toDateTime))
        assertEquals("23 - 29 Jan 2006", nextWeek.toString(mockResources))

        val nextNextWeek=dateTimeInterval.getShiftedDateTimeInterval(2)!!
        assertEquals("30.01.2006 00:00:00.000", dateTimeFormatter.formatJodaDateTime(nextNextWeek.fromDateTime))
        assertEquals("05.02.2006 23:59:59.999", dateTimeFormatter.formatJodaDateTime(nextNextWeek.toDateTime))
        assertEquals("30 - 05 Feb 2006", nextNextWeek.toString(mockResources))

        val previousWeek=dateTimeInterval.getPreviousDateTimeInterval()!!
        assertEquals("09.01.2006 00:00:00.000", dateTimeFormatter.formatJodaDateTime(previousWeek.fromDateTime))
        assertEquals("15.01.2006 23:59:59.999", dateTimeFormatter.formatJodaDateTime(previousWeek.toDateTime))
        assertEquals("09 - 15 Jan 2006", previousWeek.toString(mockResources))

        val prevPreviousWeek=dateTimeInterval.getShiftedDateTimeInterval(-2)!!
        assertEquals("02.01.2006 00:00:00.000", dateTimeFormatter.formatJodaDateTime(prevPreviousWeek.fromDateTime))
        assertEquals("08.01.2006 23:59:59.999", dateTimeFormatter.formatJodaDateTime(prevPreviousWeek.toDateTime))
        assertEquals("02 - 08 Jan 2006", prevPreviousWeek.toString(mockResources))
    }

    @Test
    fun testIntervalAroundToday()
    {
        val year=DailyDateTimeInterval.today().fromDateTime.year
        val month=DailyDateTimeInterval.today().fromDateTime.monthOfYear
            .let { month -> if (month<10) "0$month" else month.toString() }

        val minDayFromWeek=DateTimeIntervalConverter().convert(
            from = DailyDateTimeInterval.today(),
            to = WeeklyDateTimeInterval::class.java
        ).fromDateTime.dayOfMonth

        val maxDayFromWeek=DateTimeIntervalConverter().convert(
            from = DailyDateTimeInterval.today(),
            to = WeeklyDateTimeInterval::class.java
        ).toDateTime.dayOfMonth

        val interval=WeeklyDateTimeInterval.aroundToday()
        assertEquals("$minDayFromWeek.$month.$year 00:00:00.000", dateTimeFormatter.formatJodaDateTime(interval.fromDateTime))
        assertEquals("$maxDayFromWeek.$month.$year 23:59:59.999", dateTimeFormatter.formatJodaDateTime(interval.toDateTime))
    }
}
