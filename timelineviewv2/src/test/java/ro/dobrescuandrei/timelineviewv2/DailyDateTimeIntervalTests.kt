package ro.dobrescuandrei.timelineviewv2

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ro.dobrescuandrei.timelineviewv2.model.DailyDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval

class DailyDateTimeIntervalTests
{
    private val dateTimeInterval : DateTimeInterval = DailyDateTimeInterval(
        referenceDateTime = DateTime(2006, 1, 20, 0, 0, 0, 0))

    private val dateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss.SSS")

    @Before
    fun setup() = setupUnitTests()

    @Test
    fun testIntervalRange()
    {
        assertEquals("20.01.2006 00:00:00.000", dateTimeFormatter.print(dateTimeInterval.fromDateTime))
        assertEquals("20.01.2006 23:59:59.999", dateTimeFormatter.print(dateTimeInterval.toDateTime))
    }

    @Test
    fun testIntervalShifting()
    {
        assertEquals("20 Jan 2006", dateTimeInterval.toString(mockResources))

        val nextDay=dateTimeInterval.getNextDateTimeInterval()!!
        assertEquals("21.01.2006 00:00:00.000", dateTimeFormatter.print(nextDay.fromDateTime))
        assertEquals("21.01.2006 23:59:59.999", dateTimeFormatter.print(nextDay.toDateTime))
        assertEquals("21 Jan 2006", nextDay.toString(mockResources))

        val nextNextDay=dateTimeInterval.getShiftedDateTimeInterval(2)!!
        assertEquals("22.01.2006 00:00:00.000", dateTimeFormatter.print(nextNextDay.fromDateTime))
        assertEquals("22.01.2006 23:59:59.999", dateTimeFormatter.print(nextNextDay.toDateTime))
        assertEquals("22 Jan 2006", nextNextDay.toString(mockResources))

        val prevDay=dateTimeInterval.getPreviousDateTimeInterval()!!
        assertEquals("19.01.2006 00:00:00.000", dateTimeFormatter.print(prevDay.fromDateTime))
        assertEquals("19.01.2006 23:59:59.999", dateTimeFormatter.print(prevDay.toDateTime))
        assertEquals("19 Jan 2006", prevDay.toString(mockResources))

        val prevPrevDay=dateTimeInterval.getShiftedDateTimeInterval(-2)!!
        assertEquals("18.01.2006 00:00:00.000", dateTimeFormatter.print(prevPrevDay.fromDateTime))
        assertEquals("18.01.2006 23:59:59.999", dateTimeFormatter.print(prevPrevDay.toDateTime))
        assertEquals("18 Jan 2006", prevPrevDay.toString(mockResources))
    }

    @Test
    fun testIntervalAroundToday()
    {
        val year=DailyDateTimeInterval.today().fromDateTime.year
        val month=DailyDateTimeInterval.today().fromDateTime.monthOfYear
            .let { month -> if (month<10) "0$month" else month.toString() }
        val dayOfMonth=DailyDateTimeInterval.today().fromDateTime.dayOfMonth
            .let { day -> if (day<10) "0$day" else day.toString() }

        val today=DailyDateTimeInterval.today()
        assertEquals("$dayOfMonth.$month.$year 00:00:00.000", dateTimeFormatter.print(today.fromDateTime))
        assertEquals("$dayOfMonth.$month.$year 23:59:59.999", dateTimeFormatter.print(today.toDateTime))
    }

    @Test
    fun testIntervalToString()
    {
        val today=DailyDateTimeInterval.today()
        val tomorrow=today.getNextDateTimeInterval()
        val dayAfterTomorrow=tomorrow.getNextDateTimeInterval()
        val yesterday=today.getPreviousDateTimeInterval()
        val dayBeforeYesterday=yesterday.getPreviousDateTimeInterval()

        assertEquals("Today", today.toString(mockResources))
        assertEquals("Tomorrow", tomorrow.toString(mockResources))
        assertEquals("Yesterday", yesterday.toString(mockResources))

        assertEquals(DateTimeFormat.forPattern("dd MMM").print(dayAfterTomorrow.fromDateTime), dayAfterTomorrow.toString(mockResources))
        assertEquals(DateTimeFormat.forPattern("dd MMM").print(dayBeforeYesterday.fromDateTime), dayBeforeYesterday.toString(mockResources))

        assert(today.isToday)
        assert(!tomorrow.isToday)
        assert(!dayAfterTomorrow.isToday)
        assert(!yesterday.isToday)
        assert(!dayBeforeYesterday.isToday)
    }

    @Test
    fun testDaylightSavingTimeChangeFromSpring()
    {
        fun iterateUseCases(consumer : (
            dayBeforeDstChangeDay : DailyDateTimeInterval,
            dstChangeDay : DailyDateTimeInterval,
            dayAfterDstChangeDay : DailyDateTimeInterval) -> (Unit))
        {
            val dstChangeDate=DailyDateTimeInterval(DateTime(2021, 3, 28,
                0, 0, 0, 0, TimelineViewDefaults.timezone))

            val dayBeforeDstChangeDay=DailyDateTimeInterval(DateTime(2021, 3, 27,
                0, 0, 0, 0, TimelineViewDefaults.timezone))

            val dayAfterDstChangeDay=DailyDateTimeInterval(DateTime(2021, 3, 29,
                0, 0, 0, 0, TimelineViewDefaults.timezone))

            println("coming from DST change day, go previous and next")
            consumer(dstChangeDate.getPreviousDateTimeInterval(), dstChangeDate, dstChangeDate.getNextDateTimeInterval())

            println("coming from day before DST change day, go next then next")
            consumer(dayBeforeDstChangeDay, dayBeforeDstChangeDay.getNextDateTimeInterval(),
                dayBeforeDstChangeDay.getNextDateTimeInterval().getNextDateTimeInterval())

            println("coming from day after DST change day, go previous then previous")
            consumer(dayAfterDstChangeDay.getPreviousDateTimeInterval().getPreviousDateTimeInterval(),
                dayAfterDstChangeDay.getPreviousDateTimeInterval(), dayAfterDstChangeDay)
        }

        iterateUseCases { dayBeforeDstChangeDay, dstChangeDay, dayAfterDstChangeDay ->

            assertEquals("28.03.2021 00:00:00.000", dateTimeFormatter.print(dstChangeDay.fromDateTime))
            assertEquals("28.03.2021 23:59:59.999", dateTimeFormatter.print(dstChangeDay.toDateTime))

            assertEquals("29.03.2021 00:00:00.000", dateTimeFormatter.print(dayAfterDstChangeDay.fromDateTime))
            assertEquals("29.03.2021 23:59:59.999", dateTimeFormatter.print(dayAfterDstChangeDay.toDateTime))

            assertEquals("27.03.2021 00:00:00.000", dateTimeFormatter.print(dayBeforeDstChangeDay.fromDateTime))
            assertEquals("27.03.2021 23:59:59.999", dateTimeFormatter.print(dayBeforeDstChangeDay.toDateTime))

            //summer is coming, thus the sunlight will take more daytime, time will decrease with one hour
            assertEquals(24.hoursInMills.toLong(), dayBeforeDstChangeDay.toDateTime.millis-dayBeforeDstChangeDay.fromDateTime.millis+1)
            assertEquals(23.hoursInMills.toLong(), dstChangeDay.toDateTime.millis-dstChangeDay.fromDateTime.millis+1)
            assertEquals(24.hoursInMills.toLong(), dayAfterDstChangeDay.toDateTime.millis-dayAfterDstChangeDay.fromDateTime.millis+1)
        }
    }

    @Test
    fun testDaylightSavingTimeChangeFromAuntum()
    {
        fun iterateUseCases(consumer : (
            dayBeforeDstChangeDay : DailyDateTimeInterval,
            dstChangeDay : DailyDateTimeInterval,
            dayAfterDstChangeDay : DailyDateTimeInterval) -> (Unit))
        {
            val dstChangeDate=DailyDateTimeInterval(DateTime(2021, 10, 31,
                0, 0, 0, 0, TimelineViewDefaults.timezone))

            val dayBeforeDstChangeDay=DailyDateTimeInterval(DateTime(2021, 10, 30,
                0, 0, 0, 0, TimelineViewDefaults.timezone))

            val dayAfterDstChangeDay=DailyDateTimeInterval(DateTime(2021, 11, 1,
                0, 0, 0, 0, TimelineViewDefaults.timezone))

            println("coming from DST change day, go previous and next")
            consumer(dstChangeDate.getPreviousDateTimeInterval(), dstChangeDate, dstChangeDate.getNextDateTimeInterval())

            println("coming from day before DST change day, go next then next")
            consumer(dayBeforeDstChangeDay, dayBeforeDstChangeDay.getNextDateTimeInterval(),
                dayBeforeDstChangeDay.getNextDateTimeInterval().getNextDateTimeInterval())

            println("coming from day after DST change day, go previous then previous")
            consumer(dayAfterDstChangeDay.getPreviousDateTimeInterval().getPreviousDateTimeInterval(),
                dayAfterDstChangeDay.getPreviousDateTimeInterval(), dayAfterDstChangeDay)
        }

        iterateUseCases { dayBeforeDstChangeDay, dstChangeDay, dayAfterDstChangeDay ->

            assertEquals("31.10.2021 00:00:00.000", dateTimeFormatter.print(dstChangeDay.fromDateTime))
            assertEquals("31.10.2021 23:59:59.999", dateTimeFormatter.print(dstChangeDay.toDateTime))

            assertEquals("01.11.2021 00:00:00.000", dateTimeFormatter.print(dayAfterDstChangeDay.fromDateTime))
            assertEquals("01.11.2021 23:59:59.999", dateTimeFormatter.print(dayAfterDstChangeDay.toDateTime))

            assertEquals("30.10.2021 00:00:00.000", dateTimeFormatter.print(dayBeforeDstChangeDay.fromDateTime))
            assertEquals("30.10.2021 23:59:59.999", dateTimeFormatter.print(dayBeforeDstChangeDay.toDateTime))

            //winter is coming, thus the sunlight will take less daytime, time will increase with one hour
            assertEquals(24.hoursInMills.toLong(), dayBeforeDstChangeDay.toDateTime.millis-dayBeforeDstChangeDay.fromDateTime.millis+1)
            assertEquals(25.hoursInMills.toLong(), dstChangeDay.toDateTime.millis-dstChangeDay.fromDateTime.millis+1)
            assertEquals(24.hoursInMills.toLong(), dayAfterDstChangeDay.toDateTime.millis-dayAfterDstChangeDay.fromDateTime.millis+1)
        }
    }
}
