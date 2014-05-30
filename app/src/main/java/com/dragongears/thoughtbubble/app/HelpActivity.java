package com.dragongears.thoughtbubble.app;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;

public class HelpActivity extends FragmentActivity {
    MyPageAdapter pageAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        List<Fragment> fragments = getFragments();

        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);

        ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);

        //Bind the title indicator to the adapter
        CirclePageIndicator helpIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        helpIndicator.setViewPager(pager);

    }

    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(HelpFragment.newInstance("Type a message", "type_message"));
        fList.add(HelpFragment.newInstance("Rotate your device", "rotate_phone"));
        fList.add(HelpFragment.newInstance("Hold it up to your head", "hold_up_phone"));
        fList.add(HelpFragment.newInstance("Tap the down arrow to add your message to the list.\n\nLong press on a list item to delete it.", "usage_tips"));

        return fList;
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }
        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }
}