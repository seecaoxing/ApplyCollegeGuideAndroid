package com.guide.applycollegeguide;


import com.guide.applycollegeguide.home.*;
import com.guide.applycollegeguide.my.MyFragment;
import com.guide.applycollegeguide.recommend.SchoolRecommend;

/**
 * Created by md on 2017/12/16.
 */

public class MainTabHelper {

    public static int[] getTabsTextId() {
        int[] tabs = {R.string.base_home_page,
                R.string.base_moments,
                R.string.base_mine};
        return tabs;
    }

    public static int[] getTabsIcon() {
        int[] ids = {R.drawable.tabbar_home,
                R.drawable.tabbar_circle,
                R.drawable.tabbar_my};
        return ids;
    }

    public static Class[] getFragments() {
        Class[] clz = {
                HomeFragment.class,
                SchoolRecommend.class,
                MyFragment.class,
 };
        return clz;
    }
}
