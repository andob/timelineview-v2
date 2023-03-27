package ro.dobrescuandrei.timelineviewv2;

import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval;

@FunctionalInterface
public interface OnDateTimeIntervalChangedListener
{
    void invoke(DateTimeInterval interval);
}
