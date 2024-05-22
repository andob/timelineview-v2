package ro.dobrescuandrei.timelineviewv2.model

import java.time.ZonedDateTime

class DateTimeIntervalConverter
{
    @Suppress("UNCHECKED_CAST")
    fun <FROM : DateTimeInterval, TO : DateTimeInterval> convert(from : FROM, to : Class<TO>) : TO
    {
        val inputInterval = from
        val inputType = from::class.java
        val outputType = to
        val outputInterval = when(outputType)
        {
            InfiniteDateTimeInterval::class.java ->
                InfiniteDateTimeInterval()

            CustomDateTimeInterval::class.java ->
                CustomDateTimeInterval(
                    fromDateTime = inputInterval.fromDateTime,
                    toDateTime = inputInterval.toDateTime)

            else ->
            {
                val todayAndNow = ZonedDateTime.now(DateTimeInterval.defaultTimezone)!!

                val referenceDateTime = 
                    if (outputType.isLowerOrderTypeOf(inputType)
                        && inputInterval.contains(todayAndNow))
                        todayAndNow
                    else inputInterval.fromDateTime

                outputType.constructors.find { constructor ->
                    ZonedDateTime::class.java in constructor.parameterTypes
                }!!.newInstance(referenceDateTime)
            }
        } as TO

        return outputInterval
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
