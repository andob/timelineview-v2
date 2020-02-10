package ro.dobrescuandrei.timelineviewv2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ro.dobrescuandrei.timelineviewv2.base.BaseCustomView;
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval;

public abstract class TimelineViewApi extends BaseCustomView
{
    protected @Nullable OnDateTimeIntervalChangedListener onDateTimeIntervalChangedListener;

    private DateTimeInterval dateTimeInterval;

    private DateTimeIntervalTypeChangeFlow dateTimeIntervalTypeChangeFlow;

    protected boolean isCustomDateTimeIntervalSupported;

    private final TimelineViewAppearance appearance;

    public TimelineViewApi(Context context)
    {
        super(context);
        appearance=new TimelineViewAppearance(context);
    }

    @SuppressLint("CustomViewStyleable")
    public TimelineViewApi(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);

        TypedArray attributes=context.obtainStyledAttributes(attributeSet, R.styleable.TimelineView);
        appearance=new TimelineViewAppearance(context, attributes);
        attributes.recycle();
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

    public TimelineViewAppearance getAppearance()
    {
        return appearance;
    }
}
