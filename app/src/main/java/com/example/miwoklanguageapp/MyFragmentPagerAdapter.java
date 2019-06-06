package com.example.miwoklanguageapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    //To store the total number of tabs used.
    final private int page = 4;
    //To store the context of this class through the default constructor.
    private Context mContext;

    //Default constructor for this class.
    public MyFragmentPagerAdapter(Context context ,FragmentManager fm) {

        super(fm);
        mContext = context;
    }

    //This method attaches the respective fragments to the respective tabs.
    @Override
    public Fragment getItem(int i) {
        if (i == 0) return new NumbersFragment();

        else if (i == 1) return new FamilyFragment();

        else if (i == 2) return new ColorsFragment();

        else return new PhrasesFragment();
    }

    //This method returns the total number of tabs used.
    @Override
    public int getCount() {
        return page;
    }

    //This method sets the name of each tab.
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return mContext.getString(R.string.category_numbers);
        else if (position == 1)
            return mContext.getString(R.string.category_family);
        else if (position == 2)
            return mContext.getString(R.string.category_colors);
        else
            return mContext.getString(R.string.category_phrases);
    }
}
