package dips.co.news.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import dips.co.news.Fragment.Latest_Fragment;
import dips.co.news.Fragment.Top_Fragment;

/**
 * Created by Dipesh on 03-Jan-17.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public ViewPagerAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Top_Fragment tab1 = new Top_Fragment();
                return tab1;
            case 1:
                Latest_Fragment tab2 = new Latest_Fragment();
                return tab2;
            default:
                return null; }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
