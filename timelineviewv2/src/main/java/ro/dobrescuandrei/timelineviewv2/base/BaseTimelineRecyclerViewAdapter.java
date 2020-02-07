package ro.dobrescuandrei.timelineviewv2.base;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import ro.dobrescuandrei.timelineviewv2.OnDateTimeIntervalChangedListener;
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval;
import ro.dobrescuandrei.timelineviewv2.recycler.TimelineRecyclerViewHolder;

public abstract class BaseTimelineRecyclerViewAdapter<DATE_TIME_INTERVAL extends DateTimeInterval> extends RecyclerView.Adapter<TimelineRecyclerViewHolder>
{
    public Context context;

    protected DATE_TIME_INTERVAL referenceDateTimeInterval;
    private DATE_TIME_INTERVAL selectedDateTimeInterval;

    protected @Nullable OnDateTimeIntervalChangedListener onSelectedDateTimeIntervalChangedListener;

    public BaseTimelineRecyclerViewAdapter(Context context)
    {
        this.context=context;
    }

    public void setOnSelectedDateTimeIntervalChangedListener(OnDateTimeIntervalChangedListener listener)
    {
        this.onSelectedDateTimeIntervalChangedListener=listener;
    }

    public @NonNull DATE_TIME_INTERVAL getSelectedDateTimeInterval()
    {
        return this.selectedDateTimeInterval;
    }

    public void setSelectedDateTimeInterval(@NonNull DATE_TIME_INTERVAL dateTimeInterval)
    {
        this.selectedDateTimeInterval=dateTimeInterval;

        if (this.onSelectedDateTimeIntervalChangedListener!=null)
            this.onSelectedDateTimeIntervalChangedListener.invoke(this.selectedDateTimeInterval);

        if (this.referenceDateTimeInterval==null)
            this.referenceDateTimeInterval=dateTimeInterval;
    }

    @NonNull
    @Override
    public TimelineRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        TimelineRecyclerViewHolder viewHolder=new TimelineRecyclerViewHolder(context);
        viewHolder.getCellView().setWidthInPixels(getCellWidthInPixels());
        return viewHolder;
    }

    public abstract int getCellWidthInPixels();

    public void dispose()
    {
        this.context=null;
        this.onSelectedDateTimeIntervalChangedListener=null;
    }
}
