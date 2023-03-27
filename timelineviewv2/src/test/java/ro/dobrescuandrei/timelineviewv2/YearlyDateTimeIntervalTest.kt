package ro.dobrescuandrei.timelineviewv2

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ro.dobrescuandrei.timelineviewv2.model.DailyDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.YearlyDateTimeInterval
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class YearlyDateTimeIntervalTest
{
    private val dateTimeInterval : DateTimeInterval = YearlyDateTimeInterval(
        referenceDateTime = LocalDateTime.of(2021, 1, 12, 0, 0, 0, 0))

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS")

    @Before
    fun setup() = setupUnitTests()

    @Test
    fun testIntervalRange()
    {
        assertEquals("01.01.2021 00:00:00.000", dateTimeFormatter.format(dateTimeInterval.fromDateTime))
        assertEquals("31.12.2021 23:59:59.999", dateTimeFormatter.format(dateTimeInterval.toDateTime))
    }

    @Test
    fun testIntervalShifting()
    {
        assertEquals("2021", dateTimeInterval.toString(mockResources))

        val _2022=dateTimeInterval.getNextDateTimeInterval()!!
        assertEquals("01.01.2022 00:00:00.000", dateTimeFormatter.format(_2022.fromDateTime))
        assertEquals("31.12.2022 23:59:59.999", dateTimeFormatter.format(_2022.toDateTime))
        assertEquals("2022", _2022.toString(mockResources))

        val _2023=dateTimeInterval.getShiftedDateTimeInterval(2)!!
        assertEquals("01.01.2023 00:00:00.000", dateTimeFormatter.format(_2023.fromDateTime))
        assertEquals("31.12.2023 23:59:59.999", dateTimeFormatter.format(_2023.toDateTime))
        assertEquals("2023", _2023.toString(mockResources))

        val _2020=dateTimeInterval.getPreviousDateTimeInterval()!!
        assertEquals("01.01.2020 00:00:00.000", dateTimeFormatter.format(_2020.fromDateTime))
        assertEquals("31.12.2020 23:59:59.999", dateTimeFormatter.format(_2020.toDateTime))
        assertEquals("2020", _2020.toString(mockResources))

        val _2019=dateTimeInterval.getShiftedDateTimeInterval(-2)!!
        assertEquals("01.01.2019 00:00:00.000", dateTimeFormatter.format(_2019.fromDateTime))
        assertEquals("31.12.2019 23:59:59.999", dateTimeFormatter.format(_2019.toDateTime))
        assertEquals("2019", _2019.toString(mockResources))
    }

    @Test
    fun testIntervalAroundToday()
    {
        val year=DailyDateTimeInterval.today().fromDateTime.year
        val interval=YearlyDateTimeInterval.aroundToday()
        assertEquals("01.01.$year 00:00:00.000", dateTimeFormatter.format(interval.fromDateTime))
        assertEquals("31.12.$year 23:59:59.999", dateTimeFormatter.format(interval.toDateTime))
        assertEquals(year.toString(), interval.toString(mockResources))
    }
}
