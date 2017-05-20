package com.example.jia.news.Activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jia.news.Activity.Activivty.PageFragmentActivity;

import java.util.ArrayList;

/**
 * Created by jia on 2017/5/16.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> title;//存标题
    private ArrayList<PageFragmentActivity> fragments;//显示的新闻列表

    public MyFragmentPagerAdapter(FragmentManager fm,ArrayList<String> title,ArrayList<PageFragmentActivity> fragments) {
        super(fm);
        this.title=title;
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if(position<fragments.size()){
            return  fragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if(fragments==null){
            return 0;
        }
       return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
       if(title!=null && position< title.size()){
           return title.get(position);
       }
       return "notitle";
    }

}
