package com.guide.applycollegeguide.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guide.applycollegeguide.R;
import com.guide.applycollegeguide.customview.ToolBar;
import com.xyz.core.util.DensityUtils;
import com.xyz.custom.LoadingDialog;


/**
 * Created by md on 2017/12/14.
 */

public class BaseFragment extends Fragment {

    protected FragmentActivity fragmentActivity;
    protected RelativeLayout mRootView;
    private LinearLayout mTitleLayout;
    protected TextView mTitleView;
    private ImageView mBackView;
    private LinearLayout mActionContainer;
    private LinearLayout mActionLeftContainer;
    private LoadingDialog mProgeressDialog;

    private View ret;

    protected boolean isViewInit = false;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentActivity = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            default:
                break;
        }
    }


    public void showLoading(boolean show) {
        if (mProgeressDialog != null) {
            mProgeressDialog.dismiss();
            mProgeressDialog = null;
        }
        if (show) {
            mProgeressDialog = new LoadingDialog(getActivity());
            mProgeressDialog.show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


            ret = inflater.inflate(R.layout.fragment_base, container, false);
            mRootView = (RelativeLayout) ret;
            mTitleLayout = (LinearLayout) ret.findViewById(R.id.title_bar);
            mTitleView = (TextView) ret.findViewById(R.id.title);
            mBackView = (ImageView) ret.findViewById(R.id.back);
            mActionContainer = (LinearLayout) ret.findViewById(R.id.action_container);
            mActionLeftContainer = (LinearLayout) ret.findViewById(R.id.action_left_container);
            mBackView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                getActivity().finish();
                }
            });
        
        return ret;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (canBack()) {
            ToolBar toolBar = view.findViewById(R.id.tool_bar);
            if (toolBar != null) {
                toolBar.back(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                });
            }
        }
    }

    protected void addContentView(View contentView, boolean belowActionBar) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (belowActionBar) {
            params.addRule(RelativeLayout.BELOW, R.id.title_bar);
        }
        mRootView.addView(contentView, 0, params);
    }

    protected void setTitleBarGone() {
        mTitleLayout.setVisibility(View.GONE);
    }

    public boolean handleBack() {
        return false;
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    public void setTitle(int title) {
        mTitleView.setText(title);
    }

    public boolean canBack() {
        return true;
    }

    public ImageView addLeftAction(int imgResId, View.OnClickListener listener) {

        ImageView iv = new ImageView(getActivity());
        iv.setImageResource(imgResId);
        iv.setScaleType(ImageView.ScaleType.CENTER);
        iv.setOnClickListener(listener);

        int w = (int) DensityUtils.dp2px(37);
        int h = (int) DensityUtils.dp2px(48);
        mActionLeftContainer.removeAllViews();
        mActionLeftContainer.addView(iv, new LinearLayout.LayoutParams(w, h));
        return iv;
    }


    public ImageView addAction(int imgResId, View.OnClickListener listener) {
        ImageView iv = new ImageView(getActivity());
        iv.setImageResource(imgResId);
        iv.setScaleType(ImageView.ScaleType.CENTER);
        iv.setOnClickListener(listener);

        int w = (int) DensityUtils.dp2px(37);
        int h = (int) DensityUtils.dp2px(48);
        mActionContainer.addView(iv, new LinearLayout.LayoutParams(w, h));
        return iv;
    }

    public TextView addTextAction(String text, View.OnClickListener listener) {
        TextView tv = new TextView(getActivity());
        int p = (int) DensityUtils.dp2px(13);
        tv.setPadding(p, 0, p, 0);
        tv.setText(text);
        tv.setTextColor(0xFFFFFFFF);
        tv.setTextSize(14);
        tv.setMaxLines(1);
        tv.setGravity(Gravity.CENTER);
        tv.setOnClickListener(listener);

        int size = (int) DensityUtils.dp2px(48);
        mActionContainer.addView(tv, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, size));
        return tv;
    }


}
