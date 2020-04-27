package ro.dobrescuandrei.timelineviewv2;

public class OnDateTimeIntervalChangedEventsLock
{
    volatile boolean isLocked = false;

    public synchronized void lock()
    {
        this.isLocked=true;
    }

    public synchronized void unlock()
    {
        this.isLocked=false;
    }
}
