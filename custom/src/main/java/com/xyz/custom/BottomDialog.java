package com.xyz.custom;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by see on 2018/1/28.
 */

public class BottomDialog {

    private Activity mActivity;

    private TextView title1;
    private TextView title2;
    private TextView cancelDialog;

    private String text1;
    private String text2;

    private Dialog bottomDialog;

    private View.OnClickListener onClickListener1;
    private View.OnClickListener onClickListener2;


    public BottomDialog(Activity activity, String text1, String text2, View.OnClickListener onClickListener1, View.OnClickListener onClickListener2) {
        this.mActivity = activity;
        this.text1 = text1;
        this.text2 = text2;
        this.onClickListener1 = onClickListener1;
        this.onClickListener2 = onClickListener2;
    }

    public void showDialog() {
        bottomDialog = new Dialog(mActivity, R.style.BottomDialog);
        View contentView = LayoutInflater.from(mActivity).inflate(R.layout.bottom_dialog_content_normal, null);
        title1 = (TextView) contentView.findViewById(R.id.title1);
        title2 = (TextView) contentView.findViewById(R.id.title2);
        title1.setText(text1);
        title2.setText(text2);
        cancelDialog = (TextView) contentView.findViewById(R.id.cancel_dialog);
        title1.setOnClickListener(onClickListener1);
        title2.setOnClickListener(onClickListener2);

        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = mActivity.getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();

    }


    public void dismiss() {
        if (mActivity == null || mActivity.isFinishing()) {
            return;
        }
        if (bottomDialog.isShowing()) {
            bottomDialog.dismiss();
        }
    }

    public static BottomDialog showBottomDialog(Activity mActivity, String text1, String text2, View.OnClickListener onClickListener1, View.OnClickListener onClickListener2) {
        BottomDialog textDialog = null;
        if (!isActivityFinishing(mActivity)) {
            textDialog = new BottomDialog(mActivity, text1, text2, onClickListener1, onClickListener2);
            textDialog.showDialog();
        }
        return textDialog;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isActivityFinishing(Activity activity) {
        if (activity == null) return true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return activity.isDestroyed();
        } else {
            return activity.isFinishing();
        }
    }

}
