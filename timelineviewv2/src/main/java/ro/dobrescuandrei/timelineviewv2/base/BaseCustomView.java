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
        LayoutInflater.from(getContext()).inflate(getLayoutId(), this, true);
    }

    public BaseCustomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(getLayoutId(), this, true);
    }

    public abstract int getLayoutId();
}
