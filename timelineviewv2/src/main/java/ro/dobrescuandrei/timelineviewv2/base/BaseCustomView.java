package ro.dobrescuandrei.timelineviewv2.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public abstract class BaseCustomView extends RelativeLayout
{
    public BaseCustomView(Context context)
    {
        super(context);
        inflateLayout();
    }

    public BaseCustomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        inflateLayout();
    }

    private void inflateLayout()
    {
        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(getLayoutId(), this, true);
    }

    public abstract int getLayoutId();
}
