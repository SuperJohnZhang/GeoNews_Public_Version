package com.example.superjohn.geonews.elements;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by superjohn on 15/11/10.
 */
public class CustomSlidingPaneLayout extends android.support.v4.widget.SlidingPaneLayout {
    // constructors
    public CustomSlidingPaneLayout(Context context) {
        super(context);
    }
    public CustomSlidingPaneLayout(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
    public CustomSlidingPaneLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    // override the following part
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.isOpen()) {
            this.closePane();
        }
        return false; // here it returns false so that another event's listener
        // should be called, in your case the MapFragment
        // listener
    }
}
