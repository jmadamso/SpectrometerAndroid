package jmadamso.spectrometer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ResultFragmentAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private double[] mArray;
    private Bundle mArgs = new Bundle();

    public ResultFragmentAdapter(Context context, FragmentManager fm, double[] array) {
        super(fm);
        mContext = context;
        mArray = array;
        mArgs.putDoubleArray("spectrumArray",array);
    }



    // This determines the fragment for each tab.
    // we can define some args in a bundle to send
    // stuff to whoever might need it.


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            GraphFragment gf = new GraphFragment();
            gf.setArguments(mArgs);
            return gf;
        } else if (position == 1){
            return new ScrollFragment();
        } else {
            return new ScrollFragment();
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return "Graph";
            case 1:
                return "Results";
            default:
                return "default tab";
        }
    }

}