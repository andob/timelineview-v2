package ro.dobrescuandrei.timelineviewv2

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ro.dobrescuandrei.timelineviewv2.model.DailyDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.MonthlyDateTimeInterval
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

class MonthlyDateTimeIntervalTest
{
    private val dateTimeInterval : DateTimeInterval = MonthlyDateTimeInterval(
        referenceDateTime = LocalDateTime.of(2006, 1, 20, 0, 0, 0, 0))

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS")

    @Before
    fun setup() = setupUnitTests()

    @Test
    fun testIntervalRange()
    {
        assertEquals("01.01.2006 00:00:00.000", dateTimeFormatter.format(dateTimeInterval.fromDateTime))
        assertEquals("31.01.2006 23:59:59.999", dateTimeFormatter.format(dateTimeInterval.toDateTime))
    }

    @Test
    fun testIntervalShifting()
    {
        assertEquals("Jan 2006", dateTimeInterval.toString(mockResources))

        val february2006=dateTimeInterval.getNextDateTimeInterval()!!
        assertEquals("01.02.2006 00:00:00.000", dateTimeFormatter.format(february2006.fromDateTime))
        assertEquals("28.02.2006 23:59:59.999", dateTimeFormatter.format(february2006.toDateTime))
        assertEquals("Feb 2006", february2006.toString(mockResources))

        val march2006=dateTimeInterval.getShiftedDateTimeInterval(2)!!
        assertEquals("01.03.2006 00:00:00.000", dateTimeFormatter.format(march2006.fromDateTime))
        assertEquals("31.03.2006 23:59:59.999", dateTimeFormatter.format(march2006.toDateTime))
        assertEquals("Mar 2006", march2006.toString(mockResources))

        val december2005=dateTimeInterval.getPreviousDateTimeInterval()!!
        assertEquals("01.12.2005 00:00:00.000", dateTimeFormatter.format(december2005.fromDateTime))
        assertEquals("31.12.2005 23:59:59.999", dateTimeFormatter.format(december2005.toDateTime))
        assertEquals("Dec 2005", december2005.toString(mockResources))

        val november2005=dateTimeInterval.getShiftedDateTimeInterval(-2)!!
        assertEquals("01.11.2005 00:00:00.000", dateTimeFormatter.format(november2005.fromDateTime))
        assertEquals("30.11.2005 23:59:59.999", dateTimeFormatter.format(november2005.toDateTime))
        assertEquals("Nov 2005", november2005.toString(mockResources))
    }

    @Test
    fun testIntervalAroundToday()
    {
        val year=DailyDateTimeInterval.today().fromDateTime.year
        val month=DailyDateTimeInterval.today().fromDateTime.monthValue
            .let { month -> if (month<10) "0$month" else month.toString() }
        val maxDayFromMonth=DailyDateTimeInterval.today().fromDateTime.toLocalDate()!!
            .with(TemporalAdjusters.lastDayOfMonth()).dayOfMonth

        val interval=MonthlyDateTimeInterval.aroundToday()
        assertEquals("01.$month.$year 00:00:00.000", dateTimeFormatter.format(interval.fromDateTime))
        assertEquals("$maxDayFromMonth.$month.$year 23:59:59.999", dateTimeFormatter.format(interval.toDateTime))

        val humanReadableMonth=DateTimeFormatter.ofPattern("MMM").format(DailyDateTimeInterval.today().fromDateTime)
        assertEquals(humanReadableMonth, interval.toString(mockResources))
    }

    @Test
    fun testIntervalToString()
    {
        val currentYear=DailyDateTimeInterval.today().fromDateTime.year

        val monthsOnCurrentYear=(1..12)
            .map { month -> LocalDateTime.of(currentYear, month, 1, 0, 0, 0, 0) }
            .map { dateTime -> MonthlyDateTimeInterval(dateTime).toString(mockResources) }
            .joinToString(separator = " ")

        val monthsStr="Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec"
        assertEquals(monthsStr, monthsOnCurrentYear)

        val previousYear=DailyDateTimeInterval.today().fromDateTime.year-1

        val monthsOnPreviousYear=(1..12)
            .map { month -> LocalDateTime.of(previousYear, month, 1, 0, 0, 0, 0) }
            .map { dateTime -> MonthlyDateTimeInterval(dateTime).toString(mockResources) }
            .joinToString(separator = " ")

        assertEquals(monthsStr.replace(" ", " $previousYear ")+" $previousYear", monthsOnPreviousYear)

        val nextYear=DailyDateTimeInterval.today().fromDateTime.year+1

        val monthsOnNextYear=(1..12)
            .map { month -> LocalDateTime.of(nextYear, month, 1, 0, 0, 0, 0) }
            .map { dateTime -> MonthlyDateTimeInterval(dateTime).toString(mockResources) }
            .joinToString(separator = " ")

        assertEquals(monthsStr.replace(" ", " $nextYear ")+" $nextYear", monthsOnNextYear)
    }
}
