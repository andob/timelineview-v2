package ro.dobrescuandrei.timelineviewv2.model

import org.joda.time.DateTime
import ro.dobrescuandrei.timelineviewv2.TimelineViewDefaults
import java.util.*

class DateTimeIntervalConverter
{
    private val conversionHistory = ConversionHistory(maxSize = 10)

    fun <FROM : DateTimeInterval<*>, TO : DateTimeInterval<*>> convert(from : FROM, to : Class<TO>) : TO
    {
        val inputInterval=from
        val inputType=from::class.java
        val outputType=to

        fun calculateReferenceDateTime() : DateTime
        {
            if (outputType.isLowerOrderTypeOf(inputType))
            {
                for (historyItem in conversionHistory.toList())
                {
                    if (historyItem.inputType==outputType)
                        return historyItem.inputInterval.fromDateTime
                    if (historyItem.outputType==outputType)
                        return historyItem.outputInterval.fromDateTime
                }

                val todayAndNow=DateTime.now(TimelineViewDefaults.timezone)!!
                if (inputInterval.contains(todayAndNow))
                    return todayAndNow
            }

            return inputInterval.fromDateTime
        }

        val outputInterval=when(outputType)
        {
            InfiniteDateTimeInterval::class.java ->
                InfiniteDateTimeInterval()

            CustomDateTimeInterval::class.java ->
                CustomDateTimeInterval(
                    fromDateTime = inputInterval.fromDateTime,
                    toDateTime = inputInterval.toDateTime)

            else ->
                outputType.constructors.find { constructor ->
                    DateTime::class.java in constructor.parameterTypes
                }!!.newInstance(calculateReferenceDateTime())
        } as TO

        conversionHistory.add(ConversionHistory.Item(
            inputInterval = inputInterval, inputType = inputType,
            outputInterval = outputInterval, outputType = outputType))

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

    private class ConversionHistory(val maxSize : Int)
    {
        private val queue : LinkedList<Item> = LinkedList()

        fun add(item : Item)
        {
            queue.addFirst(item)

            if (queue.size>maxSize)
                queue.removeLast()
        }

        fun toList() : List<Item> = queue

        class Item
        (
            val inputInterval : DateTimeInterval<*>,
            val inputType : Class<out DateTimeInterval<*>>,
            val outputInterval : DateTimeInterval<*>,
            val outputType : Class<out DateTimeInterval<*>>
        )
    }
}
