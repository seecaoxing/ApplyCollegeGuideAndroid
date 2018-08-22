package com.guide.applycollegeguide.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guide.applycollegeguide.R;
import com.guide.applycollegeguide.base.BaseFragment;

import butterknife.ButterKnife;

public class MyFragment extends BaseFragment{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);

        ButterKnife.bind(this, view);
        return view;
    }
}
