package ro.dobrescuandrei.timelineviewv2

import ro.dobrescuandrei.timelineviewv2.model.CustomDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import java.util.*

class DateTimeIntervalTypeChangeFlow
{
    private val items = LinkedList<Class<out DateTimeInterval>>()
    private var selectedPosition = 0

    constructor(intervalTypes : List<Class<out DateTimeInterval>>) : super()
    {
        if (intervalTypes.isEmpty())
        {
            throw RuntimeException("DateTimeIntervalTypeChangeFlow cannot be empty!")
        }

        for (intervalType in intervalTypes)
        {
            if (!DateTimeInterval::class.java.isAssignableFrom(intervalType))
            {
                throw RuntimeException("Please add only DateTimeInterval types to the flow!")
            }

            if (CustomDateTimeInterval::class.java.isAssignableFrom(intervalType))
            {
                throw RuntimeException("Cannot use CustomDateTimeInterval type in the flow!")
            }
        }

        items.addAll(intervalTypes)
    }

    fun toList() = items

    fun getFirstNode() : Class<out DateTimeInterval>
    {
        selectedPosition = 0
        return items[selectedPosition]
    }

    fun hasNextNode() =
        selectedPosition >= 0 && selectedPosition < items.size
        && selectedPosition+1 < items.size

    fun nextNode() : Class<out DateTimeInterval>?
    {
        if (hasNextNode())
        {
            selectedPosition++
            return items[selectedPosition]
        }

        return null
    }

    fun hasPreviousNode() =
        selectedPosition >= 0 && selectedPosition < items.size
        && selectedPosition-1 >= 0

    fun previousNode() : Class<out DateTimeInterval>?
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
        if (items.contains(node))
        {
            selectedPosition = items.indexOf(node)
        }
    }

    companion object
    {
        @JvmStatic
        fun from(type : Class<out DateTimeInterval>) = Builder(type)
    }

    class Builder
    {
        private val types = mutableListOf<Class<out DateTimeInterval>>()
        constructor(type : Class<out DateTimeInterval>) { types.add(type) }
        fun to(type : Class<out DateTimeInterval>) = also { types.add(type) }
        fun build() = DateTimeIntervalTypeChangeFlow(types)
    }
}
