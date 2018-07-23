/*
A viewPager subclass which has been modified to intercept/block
touch input so that the user may still scroll horizontally
within one of the tabs
 */

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
        performClick();
        return false;
    }

    //just overriding this so lint doesn't yell at us
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }





}
