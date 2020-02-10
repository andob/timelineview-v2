package ro.dobrescuandrei.timelineviewv2.model

import org.joda.time.DateTime

class DateTimeIntervalConverter
{
    val cache = Cache()

    fun <FROM : DateTimeInterval<*>, TO : DateTimeInterval<*>> convert(from : FROM, to : Class<TO>) : TO
    {
        val inputInterval=from
        val inputType=from::class.java
        val outputType=to
        val outputInterval=when(outputType)
        {
            InfiniteDateTimeInterval::class.java -> InfiniteDateTimeInterval()
            CustomDateTimeInterval::class.java ->
            {
                CustomDateTimeInterval(
                    fromDateTime = inputInterval.fromDateTime,
                    toDateTime = inputInterval.toDateTime)
            }
            else ->
            {
                val referenceDateTime=cache[outputType]?:inputInterval.fromDateTime
                outputType.constructors.find { constructor ->
                    DateTime::class.java in constructor.parameterTypes
                }!!.newInstance(referenceDateTime)
            }
        } as TO

        cache[inputType]=inputInterval.fromDateTime
        cache[outputType]=outputInterval.fromDateTime

        return outputInterval
    }

    class Cache
    {
        private val items = mutableMapOf<Class<*>, DateTime>()

        operator fun get(type : Class<*>) = items[type]

        operator fun set(type : Class<*>, value : DateTime)
        {
            val targetTypes : List<Class<*>> = listOf(
                DailyDateTimeInterval::class.java,
                WeeklyDateTimeInterval::class.java,
                MonthlyDateTimeInterval::class.java,
                YearlyDateTimeInterval::class.java)

            if (type in targetTypes)
            {
                items[type]=value

                val lowerOrderTypes : List<Class<*>> = when(type)
                {
                    DailyDateTimeInterval::class.java -> listOf()

                    WeeklyDateTimeInterval::class.java -> listOf(
                        DailyDateTimeInterval::class.java)

                    MonthlyDateTimeInterval::class.java -> listOf(
                        WeeklyDateTimeInterval::class.java,
                        DailyDateTimeInterval::class.java)

                    YearlyDateTimeInterval::class.java -> listOf(
                        MonthlyDateTimeInterval::class.java,
                        WeeklyDateTimeInterval::class.java,
                        DailyDateTimeInterval::class.java)

                    else -> listOf()
                }

                val higherOrderTypes : List<Class<*>> = when(type)
                {
                    DailyDateTimeInterval::class.java -> listOf(
                        WeeklyDateTimeInterval::class.java,
                        MonthlyDateTimeInterval::class.java,
                        YearlyDateTimeInterval::class.java)

                    WeeklyDateTimeInterval::class.java -> listOf(
                        MonthlyDateTimeInterval::class.java,
                        YearlyDateTimeInterval::class.java)

                    MonthlyDateTimeInterval::class.java -> listOf(
                        YearlyDateTimeInterval::class.java)

                    else -> listOf()
                }

                for (lowerOrderType in lowerOrderTypes)
                    if (!items.containsKey(lowerOrderType))
                        items[lowerOrderType]=value

                for (higherOrderType in higherOrderTypes)
                    items.remove(higherOrderType)
            }
        }
    }
}
