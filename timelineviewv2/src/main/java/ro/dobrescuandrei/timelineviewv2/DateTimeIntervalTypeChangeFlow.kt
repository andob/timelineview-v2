package ro.dobrescuandrei.timelineviewv2

import ro.dobrescuandrei.timelineviewv2.model.CustomDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import java.util.*

class DateTimeIntervalTypeChangeFlow
{
    private val items = LinkedList<Class<DateTimeInterval>>()
    private var selectedPosition = 0

    constructor(intervalTypes : List<Class<DateTimeInterval>>) : super()
    {
        if (intervalTypes.isEmpty())
            throw RuntimeException("Invalid DateTimeIntervalTypeChange.Flow!!!")

        items.addAll(intervalTypes)
    }

    fun toList() = items

    fun getFirstNode() : Class<DateTimeInterval>
    {
        selectedPosition = 0
        return items[selectedPosition]
    }

    fun hasNextNode() =
        selectedPosition>=0 && selectedPosition<items.size
        && selectedPosition+1<items.size

    fun nextNode() : Class<DateTimeInterval>?
    {
        if (hasNextNode())
        {
            selectedPosition++
            return items[selectedPosition]
        }

        return null
    }

    fun hasPreviousNode() =
        selectedPosition>=0 && selectedPosition<items.size
        && selectedPosition-1>=0

    fun previousNode() : Class<DateTimeInterval>?
    {
        if (hasPreviousNode())
        {
            selectedPosition--
            return items[selectedPosition]
        }

        return null
    }

    fun seekToNode(node : Class<out DateTimeInterval>)
    {
        selectedPosition = items.indexOf(node)
    }

    companion object
    {
        @JvmStatic
        fun build(builderBlock : Builder.() -> (Unit)) : DateTimeIntervalTypeChangeFlow
        {
            val builder = Builder()
            builderBlock.invoke(builder)
            return builder.build()
        }

        @JvmStatic
        fun builder() = Builder()
    }

    class Builder
    {
        private val results = mutableListOf<Class<DateTimeInterval>>()

        fun <FROM : DateTimeInterval> from(type : Class<FROM>) = addType(type)
        fun <FROM : DateTimeInterval> to(type : Class<FROM>) = addType(type)

        @Suppress("UNCHECKED_CAST")
        private fun <FROM : DateTimeInterval> addType(type : Class<FROM>) : Builder
        {
            if (type==CustomDateTimeInterval::class.java)
                throw RuntimeException("Cannot use CustomDateTimeInterval in the flow!")

            results.add(type as Class<DateTimeInterval>)
            return this
        }

        fun build() = DateTimeIntervalTypeChangeFlow(results)
    }
}
