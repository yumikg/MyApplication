package com.example.yumi.myapplication;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public  class TabsPagerAdapter extends FragmentPagerAdapter {


    private String tabTitles[] = new String[] { "もらったもの", "あげたもの"};
    private final int PAGE_COUNT = tabTitles.length;



    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ReceivedFragment.newInstance("");

            case 1:
                return  GaveFragment.newInstance("");

            default:
                throw new IllegalArgumentException("position: " + position + "is unsupported.");
        }
    }

    @Override
    public int getCount() {
        return this.PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {

       return tabTitles[position];
    }
}