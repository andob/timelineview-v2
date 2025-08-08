package ro.dobrescuandrei.timelineviewv2

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ro.dobrescuandrei.timelineviewv2.model.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeIntervalConverterTests
{
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS")

    @Before
    fun setup() = setupUnitTests()

    @Test
    @Suppress("NAME_SHADOWING")
    fun testUpwardConversions()
    {
        val day = DailyDateTimeInterval(LocalDateTime.of(2006, 1, 18, 0, 0, 0, 0))
        val week = DateTimeIntervalConverter.convert(day).to(WeeklyDateTimeInterval::class.java)
        val month = DateTimeIntervalConverter.convert(day).to(MonthlyDateTimeInterval::class.java)
        val year = DateTimeIntervalConverter.convert(day).to(YearlyDateTimeInterval::class.java)

        assertEquals("16.01.2006 00:00:00.000", dateTimeFormatter.format(week.fromDateTime))
        assertEquals("22.01.2006 23:59:59.999", dateTimeFormatter.format(week.toDateTime))

        for (originalDateTimeInterval in listOf(day, week))
        {
            val month = DateTimeIntervalConverter.convert(originalDateTimeInterval).to(MonthlyDateTimeInterval::class.java)
            assertEquals("01.01.2006 00:00:00.000", dateTimeFormatter.format(month.fromDateTime))
            assertEquals("31.01.2006 23:59:59.999", dateTimeFormatter.format(month.toDateTime))
        }

        for (originalDateTimeInterval in listOf(day, week, month))
        {
            val year = DateTimeIntervalConverter.convert(originalDateTimeInterval).to(YearlyDateTimeInterval::class.java)
            assertEquals("01.01.2006 00:00:00.000", dateTimeFormatter.format(year.fromDateTime))
            assertEquals("31.12.2006 23:59:59.999", dateTimeFormatter.format(year.toDateTime))
        }

        for (originalDateTimeInterval in listOf(day, week, month, year))
        {
            val infiniteInterval = DateTimeIntervalConverter.convert(originalDateTimeInterval).to(InfiniteDateTimeInterval::class.java)
            assertEquals("01.01.1970 00:00:00.000", dateTimeFormatter.format(infiniteInterval.fromDateTime))
            assertEquals("01.01.4000 00:00:00.000", dateTimeFormatter.format(infiniteInterval.toDateTime))
        }
    }

    @Test
    fun testDownwardsConversionsFromInfiniteIntervalType()
    {
        val infiniteInterval = InfiniteDateTimeInterval()
        val year = DateTimeIntervalConverter.convert(infiniteInterval).to(YearlyDateTimeInterval::class.java)
        val month = DateTimeIntervalConverter.convert(infiniteInterval).to(MonthlyDateTimeInterval::class.java)
        val week = DateTimeIntervalConverter.convert(infiniteInterval).to(WeeklyDateTimeInterval::class.java)
        val day = DateTimeIntervalConverter.convert(infiniteInterval).to(DailyDateTimeInterval::class.java)

        assertEquals(YearlyDateTimeInterval.aroundToday().fromDateTime, year.fromDateTime)
        assertEquals(YearlyDateTimeInterval.aroundToday().toDateTime, year.toDateTime)

        assertEquals(MonthlyDateTimeInterval.aroundToday().fromDateTime, month.fromDateTime)
        assertEquals(MonthlyDateTimeInterval.aroundToday().toDateTime, month.toDateTime)

        assertEquals(WeeklyDateTimeInterval.aroundToday().fromDateTime, week.fromDateTime)
        assertEquals(WeeklyDateTimeInterval.aroundToday().toDateTime, week.toDateTime)

        assertEquals(DailyDateTimeInterval.today().fromDateTime, day.fromDateTime)
        assertEquals(DailyDateTimeInterval.today().toDateTime, day.toDateTime)
    }

    @Test
    fun testDownwardsConversionsFromYearlyIntervalTypeComingTowardsToday()
    {
        val year = YearlyDateTimeInterval.aroundToday()
        val month = DateTimeIntervalConverter.convert(year).to(MonthlyDateTimeInterval::class.java)
        val week = DateTimeIntervalConverter.convert(year).to(WeeklyDateTimeInterval::class.java)
        val day = DateTimeIntervalConverter.convert(year).to(DailyDateTimeInterval::class.java)

        assertEquals(MonthlyDateTimeInterval.aroundToday().fromDateTime, month.fromDateTime)
        assertEquals(MonthlyDateTimeInterval.aroundToday().toDateTime, month.toDateTime)

        assertEquals(WeeklyDateTimeInterval.aroundToday().fromDateTime, week.fromDateTime)
        assertEquals(WeeklyDateTimeInterval.aroundToday().toDateTime, week.toDateTime)

        assertEquals(DailyDateTimeInterval.today().fromDateTime, day.fromDateTime)
        assertEquals(DailyDateTimeInterval.today().toDateTime, day.toDateTime)
    }

    @Test
    fun testDownwardsConversionsFromMonthlyIntervalTypeComingTowardsToday()
    {
        val month = MonthlyDateTimeInterval.aroundToday()
        val week = DateTimeIntervalConverter.convert(month).to(WeeklyDateTimeInterval::class.java)
        val day = DateTimeIntervalConverter.convert(month).to(DailyDateTimeInterval::class.java)

        assertEquals(WeeklyDateTimeInterval.aroundToday().fromDateTime, week.fromDateTime)
        assertEquals(WeeklyDateTimeInterval.aroundToday().toDateTime, week.toDateTime)

        assertEquals(DailyDateTimeInterval.today().fromDateTime, day.fromDateTime)
        assertEquals(DailyDateTimeInterval.today().toDateTime, day.toDateTime)
    }

    @Test
    fun testDownwardsConversionsFromWeeklyIntervalTypeComingTowardsToday()
    {
        val week = WeeklyDateTimeInterval.aroundToday()
        val day = DateTimeIntervalConverter.convert(week).to(DailyDateTimeInterval::class.java)

        assertEquals(DailyDateTimeInterval.today().fromDateTime, day.fromDateTime)
        assertEquals(DailyDateTimeInterval.today().toDateTime, day.toDateTime)
    }

    @Test
    fun testDownwardsConversionsFromYearlyIntervalTypeComingTowardsADayFromPast()
    {
        val dateTimeFromPast = LocalDateTime.of(2006, 1, 1, 0, 0, 0, 0)

        val year = YearlyDateTimeInterval(dateTimeFromPast)
        val month = DateTimeIntervalConverter.convert(year).to(MonthlyDateTimeInterval::class.java)
        val week = DateTimeIntervalConverter.convert(year).to(WeeklyDateTimeInterval::class.java)
        val day = DateTimeIntervalConverter.convert(year).to(DailyDateTimeInterval::class.java)

        assertEquals(MonthlyDateTimeInterval(dateTimeFromPast).fromDateTime, month.fromDateTime)
        assertEquals(MonthlyDateTimeInterval(dateTimeFromPast).toDateTime, month.toDateTime)

        assertEquals(WeeklyDateTimeInterval(dateTimeFromPast).fromDateTime, week.fromDateTime)
        assertEquals(WeeklyDateTimeInterval(dateTimeFromPast).toDateTime, week.toDateTime)

        assertEquals(DailyDateTimeInterval(dateTimeFromPast).fromDateTime, day.fromDateTime)
        assertEquals(DailyDateTimeInterval(dateTimeFromPast).toDateTime, day.toDateTime)
    }

    @Test
    fun testDownwardsConversionsFromMonthlyIntervalTypeComingTowardsADayFromPast()
    {
        val dateTimeFromPast = LocalDateTime.of(2006, 1, 1, 0, 0, 0, 0)

        val month = MonthlyDateTimeInterval(dateTimeFromPast)
        val week = DateTimeIntervalConverter.convert(month).to(WeeklyDateTimeInterval::class.java)
        val day = DateTimeIntervalConverter.convert(month).to(DailyDateTimeInterval::class.java)

        assertEquals(WeeklyDateTimeInterval(dateTimeFromPast).fromDateTime, week.fromDateTime)
        assertEquals(WeeklyDateTimeInterval(dateTimeFromPast).toDateTime, week.toDateTime)

        assertEquals(DailyDateTimeInterval(dateTimeFromPast).fromDateTime, day.fromDateTime)
        assertEquals(DailyDateTimeInterval(dateTimeFromPast).toDateTime, day.toDateTime)
    }

    @Test
    fun testDownwardsConversionsFromWeeklyIntervalTypeComingTowardsADayFromPast()
    {
        val dateTimeFromPast = LocalDateTime.of(2006, 1, 2, 0, 0, 0, 0)

        val week = WeeklyDateTimeInterval(dateTimeFromPast)
        val day = DateTimeIntervalConverter.convert(week).to(DailyDateTimeInterval::class.java)

        assertEquals(DailyDateTimeInterval(dateTimeFromPast).fromDateTime, day.fromDateTime)
        assertEquals(DailyDateTimeInterval(dateTimeFromPast).toDateTime, day.toDateTime)
    }
}
