package ro.dobrescuandrei.timelineviewv2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.joda.time.DateTime;
import ro.dobrescuandrei.timelineviewv2.base.BaseCustomView;
import ro.dobrescuandrei.timelineviewv2.model.CustomDateTimeInterval;
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval;
import ro.dobrescuandrei.timelineviewv2.recycler.TimelineRecyclerViewCell;

public abstract class TimelineViewApi extends BaseCustomView
{
    protected @Nullable OnDateTimeIntervalChangedListener onDateTimeIntervalChangedListener;

    private DateTimeInterval dateTimeInterval;

    private DateTimeIntervalTypeChangeFlow dateTimeIntervalTypeChangeFlow;

    protected boolean isCustomDateTimeIntervalSupported = true;

    private final @NonNull TimelineViewAppearance appearance;

    protected @Nullable TimelineRecyclerViewCell.Transformer timelineRecyclerViewCellTransformer;

    public TimelineViewApi(Context context)
    {
        super(context);
        this.appearance=new TimelineViewAppearance(context);
    }

    public TimelineViewApi(Context context, @NonNull TimelineViewAppearance appearance)
    {
        super(context);
        this.appearance=appearance;
    }

    @SuppressLint("CustomViewStyleable")
    public TimelineViewApi(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);

        TypedArray attributes=context.obtainStyledAttributes(attributeSet, R.styleable.TimelineView);
        this.appearance=new TimelineViewAppearance(context, attributes);
        this.isCustomDateTimeIntervalSupported=attributes.getBoolean(R.styleable.TimelineView_tv_is_custom_date_time_interval_supported, true);
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
        if (!(dateTimeInterval instanceof CustomDateTimeInterval)&&!dateTimeIntervalTypeChangeFlow.toList().contains(dateTimeInterval.getClass()))
            throw new InvalidDateTimeIntervalTypeException("Cannot use "+dateTimeInterval.getClass().getSimpleName());
        if (dateTimeInterval instanceof CustomDateTimeInterval&&!isCustomDateTimeIntervalSupported)
            throw new InvalidDateTimeIntervalTypeException("Cannot use CustomDateTimeInterval!");

        if (this.dateTimeInterval!=null&&!this.dateTimeInterval.getClass().equals(dateTimeInterval.getClass()))
        {
            dateTimeIntervalTypeChangeFlow.seekToNode(dateTimeInterval.getClass());
            updateUiFromIntervalTypeChangeFlow();
        }

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

    public @NonNull TimelineViewAppearance getAppearance()
    {
        return appearance;
    }

    public @Nullable TimelineRecyclerViewCell.Transformer getTimelineRecyclerViewCellTransformer()
    {
        return timelineRecyclerViewCellTransformer;
    }

    public void setTimelineRecyclerViewCellTransformer(@Nullable TimelineRecyclerViewCell.Transformer transformer)
    {
        this.timelineRecyclerViewCellTransformer=transformer;
    }

    protected abstract void updateUiFromIntervalTypeChangeFlow();
}
