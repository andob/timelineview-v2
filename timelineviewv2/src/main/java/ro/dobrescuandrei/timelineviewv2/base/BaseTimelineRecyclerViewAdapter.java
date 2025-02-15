package ro.dobrescuandrei.timelineviewv2.base;

import android.content.Context;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ro.dobrescuandrei.timelineviewv2.TimelineView;
import ro.dobrescuandrei.timelineviewv2.model.DateTimeInterval;
import ro.dobrescuandrei.timelineviewv2.recycler.TimelineRecyclerViewHolder;
import ro.dobrescuandrei.timelineviewv2.utils.ScreenSizeDetector;

@SuppressWarnings("unchecked")
public abstract class BaseTimelineRecyclerViewAdapter<DATE_TIME_INTERVAL extends DateTimeInterval> extends RecyclerView.Adapter<TimelineRecyclerViewHolder>
{
    public Context context;
    public TimelineView timelineView;

    protected final DATE_TIME_INTERVAL referenceDateTimeInterval;

    public BaseTimelineRecyclerViewAdapter(Context context, TimelineView timelineView)
    {
        this.context = context;
        this.timelineView = timelineView;
        this.referenceDateTimeInterval = (DATE_TIME_INTERVAL)timelineView.getDateTimeInterval();
    }

    @NonNull
    @Override
    public TimelineRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        TimelineRecyclerViewHolder viewHolder = new TimelineRecyclerViewHolder(context, timelineView.getAppearance());

        int widthInPixels = getCellWidthInPixels();
        if (widthInPixels == ViewGroup.LayoutParams.MATCH_PARENT)
            viewHolder.getCellView().setWidthInPixels(ScreenSizeDetector.getScreenSize(context).x);
        else viewHolder.getCellView().setWidthInPixels(widthInPixels);

        return viewHolder;
    }

    public abstract int getCellWidthInPixels();

    public void dispose()
    {
        this.context = null;
        this.timelineView = null;
    }
}
