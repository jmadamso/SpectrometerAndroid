package jmadamso.spectrometer;


import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ResultFragmentAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private double[] mArray;


    public ResultFragmentAdapter(Context context, FragmentManager fm, double[] array) {
        super(fm);
        mContext = context;
        mArray = array;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new GraphFragment(mArray);
        } else if (position == 1){
            return new ScrollFragment();
        } else {
            return new GraphFragment(mArray);
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 3;
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
            case 2:
                return "YEP";
            default:
                return "default tab";
        }
    }

}