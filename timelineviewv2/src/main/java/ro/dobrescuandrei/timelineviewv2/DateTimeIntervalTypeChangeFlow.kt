package ro.dobrescuandrei.timelineviewv2

import ro.dobrescuandrei.timelineviewv2.model.CustomDateTimeInterval
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval
import java.util.*

class DateTimeIntervalTypeChangeFlow
{
    private val items = LinkedList<Class<DateTimeInterval<*>>>()
    private var selectedPosition = 0

    private constructor() : super()

    private constructor(builder : Builder) : super()
    {
        if (builder.results.isEmpty())
            throw RuntimeException("Invalid DateTimeIntervalTypeChange.Flow!!!")

        items.addAll(builder.results)
    }

    fun toList() = items

    fun getFirstNode() : Class<DateTimeInterval<*>>
    {
        selectedPosition=0
        return items[selectedPosition]
    }

    fun hasNextNode() =
        selectedPosition>=0&&selectedPosition<items.size
        &&selectedPosition+1<items.size

    fun nextNode() : Class<DateTimeInterval<*>>?
    {
        if (hasNextNode())
        {
            selectedPosition++
            return items[selectedPosition]
        }

        return null
    }

    fun hasPreviousNode() =
        selectedPosition>=0&&selectedPosition<items.size
        &&selectedPosition-1>=0

    fun previousNode() : Class<DateTimeInterval<*>>?
    {
        if (hasPreviousNode())
        {
            selectedPosition--
            return items[selectedPosition]
        }

        return null
    }

    fun seekToNode(node : Class<out DateTimeInterval<*>>)
    {
        selectedPosition=items.indexOf(node)
    }

    companion object
    {
        @JvmStatic
        fun build(builderBlock : Builder.() -> (Unit)) : DateTimeIntervalTypeChangeFlow
        {
            val builder=Builder()
            builderBlock.invoke(builder)
            return DateTimeIntervalTypeChangeFlow(builder)
        }
    }

    class Builder
    {
        val results=mutableListOf<Class<DateTimeInterval<*>>>()

        fun <FROM : DateTimeInterval<*>> from(type : Class<FROM>) = addType(type)
        fun <FROM : DateTimeInterval<*>> to(type : Class<FROM>) = addType(type)

        private fun <FROM : DateTimeInterval<*>> addType(type : Class<FROM>) : Builder
        {
            if (type==CustomDateTimeInterval::class.java)
                throw RuntimeException("Cannot use CustomDateTimeInterval in the flow!")

            results.add(type as Class<DateTimeInterval<*>>)
            return this
        }
    }
}
