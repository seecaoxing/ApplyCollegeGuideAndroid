package com.guide.applycollegeguide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.guide.applycollegeguide.base.BaseActivity;
import com.guide.applycollegeguide.util.UserManager;
import com.xyz.custom.FragmentTabHost;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页，底部5个tab可切换
 * Created by md on 2017/12/14.
 */

public class MainActivity extends BaseActivity {

    private static final int INDEX_MOMENTS = 1;

    @BindView(android.R.id.tabhost)
    FragmentTabHost fragmentTabHost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        fragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        for (int i = 0; i < MainTabHelper.getTabsTextId().length; i++) {
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(MainTabHelper.getFragments()[i].getSimpleName()).setIndicator(getView(i));

            fragmentTabHost.addTab(tabSpec, MainTabHelper.getFragments()[i], null);
        }

//        fragmentTabHost.setOnBeforeTabChangeListener(new FragmentTabHost.OnBeforeTabChangeListener() {
//            @Override
//            public boolean beforeChanged(int index) {
//                if (index == MainTabHelper.getTabsTextId().length-1 && UserManager.getUserInfo(MainActivity.this) == null) {
////                    LoginActivity.launch(MainActivity.this, null);
//                    return true;
//                }
//                return false;
//            }
//        });

    }

    private View getView(int index) {
        View view = View.inflate(this, R.layout.layout_home_tab_item, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_home_tab_item);
        textView.setText(MainTabHelper.getTabsTextId()[index]);
        textView.setCompoundDrawablesWithIntrinsicBounds(0, MainTabHelper.getTabsIcon()[index], 0, 0);

        return view;
    }


}
