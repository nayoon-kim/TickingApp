package com.project.mobpro.time;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private List<String> mFragmentTitleList;

    public void addFragment(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
    public CharSequence getPageTitle(int position){
        return mFragmentTitleList.get(position);
    }
    public SectionPageAdapter(FragmentManager fm){
        super(fm);
        mFragmentList = new ArrayList<Fragment>();
        mFragmentTitleList = new ArrayList<String>();
    }
    @Override
    public Fragment getItem(int position){return mFragmentList.get(position);}

    @Override
    public int getCount(){
        return mFragmentList.size();
    }
}
