package ro.dobrescuandrei.timelineviewv2.model

import java.time.ZonedDateTime

class DateTimeIntervalConverter<FROM : DateTimeInterval>
private constructor(private val interval : FROM)
{
    companion object
    {
        @JvmStatic
        fun <FROM : DateTimeInterval> convert(interval : FROM) = DateTimeIntervalConverter(interval)
    }

    @Suppress("UNCHECKED_CAST")
    fun <TO : DateTimeInterval> to(type : Class<TO>) : TO
    {
        if (type == InfiniteDateTimeInterval::class.java)
        {
            return InfiniteDateTimeInterval() as TO
        }

        if (type == CustomDateTimeInterval::class.java)
        {
            return CustomDateTimeInterval(interval.fromDateTime, interval.toDateTime) as TO;
        }

        val today = ZonedDateTime.now(DateTimeInterval.defaultTimezone)!!
        val isLowerOrderType = type.isLowerOrderTypeOf(interval::class.java)
        val referenceDateTime = if (isLowerOrderType && interval.contains(today)) today else interval.fromDateTime

        if (type == DailyDateTimeInterval::class.java)
        {
            return DailyDateTimeInterval(referenceDateTime) as TO
        }

        if (type == WeeklyDateTimeInterval::class.java)
        {
            return WeeklyDateTimeInterval(referenceDateTime) as TO
        }

        if (type == MonthlyDateTimeInterval::class.java)
        {
            return MonthlyDateTimeInterval(referenceDateTime) as TO
        }

        if (type == YearlyDateTimeInterval::class.java)
        {
            return YearlyDateTimeInterval(referenceDateTime) as TO
        }

        throw RuntimeException("Cannot convert $interval from ${interval::class.java} to ${type::class.java}")
    }

    private fun Class<*>.isLowerOrderTypeOf(another : Class<*>) : Boolean
    {
        val hierarchy : List<Class<*>> = listOf(
            DailyDateTimeInterval::class.java,
            WeeklyDateTimeInterval::class.java,
            MonthlyDateTimeInterval::class.java,
            YearlyDateTimeInterval::class.java,
            CustomDateTimeInterval::class.java,
            InfiniteDateTimeInterval::class.java)

        return hierarchy.indexOf(this)<hierarchy.indexOf(another)
    }
}
