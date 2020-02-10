package ro.dobrescuandrei.timelineviewv2.base;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ro.dobrescuandrei.timelineviewv2.DateTimeIntervalTypeChangeFlow;
import ro.dobrescuandrei.timelineviewv2.OnDateTimeIntervalChangedListener;
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval;

public abstract class BaseTimelineView extends BaseCustomView
{
    protected @Nullable OnDateTimeIntervalChangedListener onDateTimeIntervalChangedListener;

    private DateTimeInterval dateTimeInterval;

    private DateTimeIntervalTypeChangeFlow dateTimeIntervalTypeChangeFlow;

    protected boolean isCustomDateTimeIntervalSupported;

    public BaseTimelineView(Context context)
    {
        super(context);
    }

    public BaseTimelineView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setOnDateTimeIntervalChangedListener(OnDateTimeIntervalChangedListener listener)
    {
        this.onDateTimeIntervalChangedListener=listener;
    }

    public @NonNull DateTimeInterval getDateTimeInterval()
    {
        return this.dateTimeInterval;
    }

    public void setDateTimeInterval(@NonNull DateTimeInterval dateTimeInterval)
    {
        this.dateTimeInterval=dateTimeInterval;

        if (this.onDateTimeIntervalChangedListener!=null)
            this.onDateTimeIntervalChangedListener.invoke(this.dateTimeInterval);
    }

    public @NonNull DateTimeIntervalTypeChangeFlow getDateTimeIntervalTypeChangeFlow()
    {
        return dateTimeIntervalTypeChangeFlow;
    }

    public void setDateTimeIntervalTypeChangeFlow(@NonNull DateTimeIntervalTypeChangeFlow flow)
    {
        this.dateTimeIntervalTypeChangeFlow=flow;
    }

    public boolean isCustomDateTimeIntervalSupported()
    {
        return isCustomDateTimeIntervalSupported;
    }

    public void setCustomDateTimeIntervalSupported(boolean isSupported)
    {
        isCustomDateTimeIntervalSupported=isSupported;
    }
}
