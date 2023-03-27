package ro.dobrescuandrei.timelineviewv2

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import ro.dobrescuandrei.timelineviewv2.model.*

class DateTimeIntervalTypeChangeFlowTest
{
    private val flow = DateTimeIntervalTypeChangeFlow.build {
        from(DailyDateTimeInterval::class.java)
            .to(WeeklyDateTimeInterval::class.java)
            .to(MonthlyDateTimeInterval::class.java)
            .to(YearlyDateTimeInterval::class.java)
            .to(InfiniteDateTimeInterval::class.java)
    }

    @Before
    fun setup() = setupUnitTests()

    @Test
    fun testEmptyFlowInstantiation()
    {
        try { DateTimeIntervalTypeChangeFlow.build {}; fail() }
        catch (_ : Exception) {}
    }

    @Test
    fun testToList()
    {
        assertEquals(5, flow.toList().size)
        assertEquals(WeeklyDateTimeInterval::class.java, flow.toList()[1])
        assertEquals(YearlyDateTimeInterval::class.java, flow.toList()[flow.toList().size-2])
    }

    @Test
    fun testGetFirstNode()
    {
        assertEquals(DailyDateTimeInterval::class.java, flow.getFirstNode())
    }

    @Test
    fun testForwardAndBackwardIteration()
    {
        flow.seekToNode(flow.toList().first)

        assert(flow.hasNextNode())
        assertEquals(WeeklyDateTimeInterval::class.java, flow.nextNode())

        assert(flow.hasNextNode())
        assertEquals(MonthlyDateTimeInterval::class.java, flow.nextNode())

        assert(flow.hasNextNode())
        assertEquals(YearlyDateTimeInterval::class.java, flow.nextNode())

        assert(flow.hasNextNode())
        assertEquals(InfiniteDateTimeInterval::class.java, flow.nextNode())

        assert(!flow.hasNextNode())
        assert(flow.nextNode()==null)

        assert(flow.hasPreviousNode())
        assertEquals(YearlyDateTimeInterval::class.java, flow.previousNode())

        assert(flow.hasPreviousNode())
        assertEquals(MonthlyDateTimeInterval::class.java, flow.previousNode())

        assert(flow.hasPreviousNode())
        assertEquals(WeeklyDateTimeInterval::class.java, flow.previousNode())

        assert(flow.hasPreviousNode())
        assertEquals(DailyDateTimeInterval::class.java, flow.previousNode())

        assert(!flow.hasPreviousNode())
        assert(flow.previousNode()==null)
    }

    @Test
    fun testSeeking()
    {
        flow.seekToNode(DailyDateTimeInterval::class.java)
        assert(!flow.hasPreviousNode())
        assert(flow.previousNode()==null)
        assert(flow.hasNextNode())
        assertEquals(WeeklyDateTimeInterval::class.java, flow.nextNode())

        flow.seekToNode(YearlyDateTimeInterval::class.java)
        assert(flow.hasPreviousNode())
        assertEquals(MonthlyDateTimeInterval::class.java, flow.previousNode())
        assert(flow.hasNextNode())
        assertEquals(YearlyDateTimeInterval::class.java, flow.nextNode())
        assert(flow.hasNextNode())
        assertEquals(InfiniteDateTimeInterval::class.java, flow.nextNode())

        flow.seekToNode(WeeklyDateTimeInterval::class.java)
        assert(flow.hasPreviousNode())
        assertEquals(DailyDateTimeInterval::class.java, flow.previousNode())
        assert(flow.hasNextNode())
        assertEquals(WeeklyDateTimeInterval::class.java, flow.nextNode())
        assert(flow.hasNextNode())
        assertEquals(MonthlyDateTimeInterval::class.java, flow.nextNode())

        flow.seekToNode(MonthlyDateTimeInterval::class.java)
        assert(flow.hasPreviousNode())
        assertEquals(WeeklyDateTimeInterval::class.java, flow.previousNode())
        assert(flow.hasNextNode())
        assertEquals(MonthlyDateTimeInterval::class.java, flow.nextNode())
        assert(flow.hasNextNode())
        assertEquals(YearlyDateTimeInterval::class.java, flow.nextNode())

        flow.seekToNode(InfiniteDateTimeInterval::class.java)
        assert(flow.hasPreviousNode())
        assertEquals(YearlyDateTimeInterval::class.java, flow.previousNode())
        assert(flow.hasNextNode())
        assertEquals(InfiniteDateTimeInterval::class.java, flow.nextNode())
        assert(!flow.hasNextNode())
        assert(flow.nextNode()==null)
    }
}
