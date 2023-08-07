package ro.dobrescuandrei.timelineviewv2

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ro.dobrescuandrei.timelineviewv2.model.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeIntervalConverterTests
{
    private val converter = DateTimeIntervalConverter()
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS")

    @Before
    fun setup() = setupUnitTests()

    @Test
    @Suppress("NAME_SHADOWING")
    fun testUpwardConversions()
    {
        val day = DailyDateTimeInterval(LocalDateTime.of(2006, 1, 18, 0, 0, 0, 0))
        val week = converter.convert(from = day, to = WeeklyDateTimeInterval::class.java)
        val month = converter.convert(from = day, to = MonthlyDateTimeInterval::class.java)
        val year = converter.convert(from = day, to = YearlyDateTimeInterval::class.java)

        assertEquals("16.01.2006 00:00:00.000", dateTimeFormatter.format(week.fromDateTime))
        assertEquals("22.01.2006 23:59:59.999", dateTimeFormatter.format(week.toDateTime))

        listOf(day, week).map { originalDateTimeInterval ->
            val month = converter.convert(from = originalDateTimeInterval, to = MonthlyDateTimeInterval::class.java)
            assertEquals("01.01.2006 00:00:00.000", dateTimeFormatter.format(month.fromDateTime))
            assertEquals("31.01.2006 23:59:59.999", dateTimeFormatter.format(month.toDateTime))
        }

        listOf(day, week, month).map {
            val year = converter.convert(from = day, to = YearlyDateTimeInterval::class.java)
            assertEquals("01.01.2006 00:00:00.000", dateTimeFormatter.format(year.fromDateTime))
            assertEquals("31.12.2006 23:59:59.999", dateTimeFormatter.format(year.toDateTime))
        }

        listOf(day, week, month, year).map {
            val infiniteInterval = converter.convert(from = day, to = YearlyDateTimeInterval::class.java)
            assertEquals("01.01.2006 00:00:00.000", dateTimeFormatter.format(infiniteInterval.fromDateTime))
            assertEquals("31.12.2006 23:59:59.999", dateTimeFormatter.format(infiniteInterval.toDateTime))
        }
    }

    @Test
    fun testDownwardsConversionsFromInfiniteIntervalType()
    {
        val infiniteInterval = InfiniteDateTimeInterval()
        val year = converter.convert(from = infiniteInterval, to = YearlyDateTimeInterval::class.java)
        val month = converter.convert(from = infiniteInterval, to = MonthlyDateTimeInterval::class.java)
        val week = converter.convert(from = infiniteInterval, to = WeeklyDateTimeInterval::class.java)
        val day = converter.convert(from = infiniteInterval, to = DailyDateTimeInterval::class.java)

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
    fun testDownwardsConversionsFromYearlyIntervalType()
    {
        println("coming towards today").let {
            val year = YearlyDateTimeInterval.aroundToday()
            val month = converter.convert(from = year, to = MonthlyDateTimeInterval::class.java)
            val week = converter.convert(from = year, to = WeeklyDateTimeInterval::class.java)
            val day = converter.convert(from = year, to = DailyDateTimeInterval::class.java)

            assertEquals(MonthlyDateTimeInterval.aroundToday().fromDateTime, month.fromDateTime)
            assertEquals(MonthlyDateTimeInterval.aroundToday().toDateTime, month.toDateTime)

            assertEquals(WeeklyDateTimeInterval.aroundToday().fromDateTime, week.fromDateTime)
            assertEquals(WeeklyDateTimeInterval.aroundToday().toDateTime, week.toDateTime)

            assertEquals(DailyDateTimeInterval.today().fromDateTime, day.fromDateTime)
            assertEquals(DailyDateTimeInterval.today().toDateTime, day.toDateTime)
        }

        println("coming towards a day from the past").let {
            val dateTimeFromPast = LocalDateTime.of(2006, 1, 1, 0, 0, 0, 0)

            val year = YearlyDateTimeInterval(dateTimeFromPast)
            val month = converter.convert(from = year, to = MonthlyDateTimeInterval::class.java)
            val week = converter.convert(from = year, to = WeeklyDateTimeInterval::class.java)
            val day = converter.convert(from = year, to = DailyDateTimeInterval::class.java)

            assertEquals(MonthlyDateTimeInterval(dateTimeFromPast).fromDateTime, month.fromDateTime)
            assertEquals(MonthlyDateTimeInterval(dateTimeFromPast).toDateTime, month.toDateTime)

            assertEquals(WeeklyDateTimeInterval(dateTimeFromPast).fromDateTime, week.fromDateTime)
            assertEquals(WeeklyDateTimeInterval(dateTimeFromPast).toDateTime, week.toDateTime)

            assertEquals(DailyDateTimeInterval(dateTimeFromPast).fromDateTime, day.fromDateTime)
            assertEquals(DailyDateTimeInterval(dateTimeFromPast).toDateTime, day.toDateTime)
        }
    }

    @Test
    fun testDownwardsConversionsFromMonthlyIntervalType()
    {
        println("coming towards today").let {
            val month = MonthlyDateTimeInterval.aroundToday()
            val week = converter.convert(from = month, to = WeeklyDateTimeInterval::class.java)
            val day = converter.convert(from = month, to = DailyDateTimeInterval::class.java)

            assertEquals(WeeklyDateTimeInterval.aroundToday().fromDateTime, week.fromDateTime)
            assertEquals(WeeklyDateTimeInterval.aroundToday().toDateTime, week.toDateTime)

            assertEquals(DailyDateTimeInterval.today().fromDateTime, day.fromDateTime)
            assertEquals(DailyDateTimeInterval.today().toDateTime, day.toDateTime)
        }

        println("coming towards a day from the past").let {
            val dateTimeFromPast = LocalDateTime.of(2006, 1, 1, 0, 0, 0, 0)

            val month = MonthlyDateTimeInterval(dateTimeFromPast)
            val week = converter.convert(from = month, to = WeeklyDateTimeInterval::class.java)
            val day = converter.convert(from = month, to = DailyDateTimeInterval::class.java)

            assertEquals(WeeklyDateTimeInterval(dateTimeFromPast).fromDateTime, week.fromDateTime)
            assertEquals(WeeklyDateTimeInterval(dateTimeFromPast).toDateTime, week.toDateTime)

            assertEquals(DailyDateTimeInterval(dateTimeFromPast).fromDateTime, day.fromDateTime)
            assertEquals(DailyDateTimeInterval(dateTimeFromPast).toDateTime, day.toDateTime)
        }
    }

    @Test
    fun testDownwardsConversionsFromWeeklyIntervalType()
    {
        println("coming towards today").let {
            val week = WeeklyDateTimeInterval.aroundToday()
            val day = converter.convert(from = week, to = DailyDateTimeInterval::class.java)

            assertEquals(DailyDateTimeInterval.today().fromDateTime, day.fromDateTime)
            assertEquals(DailyDateTimeInterval.today().toDateTime, day.toDateTime)
        }

        println("coming towards a day from the past").let {
            val dateTimeFromPast = LocalDateTime.of(2006, 1, 1, 0, 0, 0, 0)

            val week = WeeklyDateTimeInterval(dateTimeFromPast)
            val day = converter.convert(from = week, to = DailyDateTimeInterval::class.java)

            assertEquals(DailyDateTimeInterval(week.fromDateTime).fromDateTime, day.fromDateTime)
            assertEquals(DailyDateTimeInterval(week.fromDateTime).toDateTime, day.toDateTime)
        }
    }
}
