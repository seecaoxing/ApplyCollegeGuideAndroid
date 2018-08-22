package com.xyz.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.xyz.core.util.DensityUtils;

/**
 * Created by li_bin on 2016/12/13.
 * 加载状态的Dialog
 */
public class LoadingDialog {

    private Activity mActivity;
    private AlertDialog mDialog;
    private TextView mText;

    public LoadingDialog(Activity activity) {
        mActivity = activity;
        View view = LayoutInflater.from(mActivity).inflate(R.layout.loading_dialog_layout, null);
        mText = (TextView) view.findViewById(R.id.text);
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        mDialog = builder.create();
        mDialog.setView(view);
        mDialog.setCanceledOnTouchOutside(false);
    }

    public void setText(String text) {
        mText.setText(text);
    }

    public void show() {
        mDialog.show();
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        //居于中间
//        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = (int) DensityUtils.dp2px(150);
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
        //去掉背景，和屏幕有间距
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void dismiss() {
        if (mActivity == null || mActivity.isFinishing()) {
            return;
        }
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
