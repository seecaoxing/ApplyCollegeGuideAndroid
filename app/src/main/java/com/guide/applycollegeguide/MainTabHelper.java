package com.guide.applycollegeguide;


/**
 * Created by md on 2017/12/16.
 */

public class MainTabHelper {

    public static int[] getTabsTextId() {
        int[] tabs = {R.string.base_home_page,
                R.string.base_moments,
                R.string.base_motortrip,
                R.string.base_motobike_store,
                R.string.base_mine};
        return tabs;
    }

    public static int[] getTabsIcon() {
        int[] ids = {R.drawable.tabbar_home,
                R.drawable.tabbar_circle,
                R.drawable.tabbar_motortrip,
                R.drawable.tabbar_store,
                R.drawable.tabbar_my};
        return ids;
    }

    public static Class[] getFragments() {
        Class[] clz = {
//                HomeFragment.class,
//                MoMomentsFragment.class,
//                MotorTripFragment.class,
//                MallFragment.class,
//                BrandSelectFragment.class,
//                MotorSelectListFragment.class,
//                MyFragment.class,
 };
        return clz;
    }
}
