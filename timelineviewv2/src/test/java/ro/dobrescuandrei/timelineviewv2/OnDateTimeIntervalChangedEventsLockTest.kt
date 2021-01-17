package ro.dobrescuandrei.timelineviewv2

import org.junit.Before
import org.junit.Test

class OnDateTimeIntervalChangedEventsLockTest
{
    @Before
    fun setup() = setupUnitTests()

    @Test
    fun testLockStates()
    {
        val lock=OnDateTimeIntervalChangedEventsLock()
        assert(lock.isEventInvokingEnabled())

        lock.disableInvokingEvents()
        assert(!lock.isEventInvokingEnabled())
        lock.enableInvokingEvents()
        assert(lock.isEventInvokingEnabled())

        lock.lock()
        assert(!lock.isEventInvokingEnabled())
        lock.unlock()
        assert(lock.isEventInvokingEnabled())
    }
}
