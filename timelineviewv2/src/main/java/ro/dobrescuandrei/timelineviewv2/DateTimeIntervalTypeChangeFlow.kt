package ro.dobrescuandrei.timelineviewv2

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

    internal fun getFirstFlowNode() : Class<DateTimeInterval<*>>
    {
        selectedPosition=0
        return items[selectedPosition]
    }

    internal fun hasNextNode() = selectedPosition+1<items.size

    internal fun nextNode() : Class<DateTimeInterval<*>>?
    {
        if (hasNextNode())
        {
            selectedPosition++
            return items[selectedPosition]
        }

        return null
    }

    internal fun hasPreviousNode() = selectedPosition-1>=0

    internal fun previousNode() : Class<DateTimeInterval<*>>?
    {
        if (hasPreviousNode())
        {
            selectedPosition--
            return items[selectedPosition]
        }

        return null
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

        fun <FROM : DateTimeInterval<*>> from(type : Class<FROM>) : Builder
        {
            results.add(type as Class<DateTimeInterval<*>>)
            return this
        }

        fun <FROM : DateTimeInterval<*>> to(type : Class<FROM>) : Builder
        {
            results.add(type as Class<DateTimeInterval<*>>)
            return this
        }
    }
}
