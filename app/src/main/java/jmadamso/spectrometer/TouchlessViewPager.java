package jmadamso.spectrometer;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TouchlessViewPager extends ViewPager {

    public TouchlessViewPager(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }





}
