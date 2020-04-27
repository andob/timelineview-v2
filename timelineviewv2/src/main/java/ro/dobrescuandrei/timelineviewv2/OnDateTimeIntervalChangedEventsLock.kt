package ro.dobrescuandrei.timelineviewv2

class OnDateTimeIntervalChangedEventsLock
{
    @Volatile
    private var isLocked = false

    @Synchronized
    fun lock()
    {
        isLocked = true
    }

    @Synchronized
    fun disableInvokingEvents() = lock()

    @Synchronized
    fun unlock()
    {
        isLocked = false
    }

    @Synchronized
    fun enableInvokingEvents() = unlock()

    @Synchronized
    fun isEventInvokingEnabled() = !isLocked
}