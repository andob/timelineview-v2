package ro.dobrescuandrei.timelineviewv2.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

public abstract class BaseCustomView extends RelativeLayout
{
    public BaseCustomView(Context context)
    {
        super(context);
        inflateLayout();
        onCreate();
    }

    public BaseCustomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        resolveAttributeSetOnCreate(attrs);
        inflateLayout();
        onCreate();
    }

    private void inflateLayout()
    {
        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(getLayoutId(), this, true);
    }

    public abstract void onCreate();

    public abstract void resolveAttributeSetOnCreate(@NonNull AttributeSet attributeSet);

    public abstract int getLayoutId();
}
