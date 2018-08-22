package com.guide.applycollegeguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.guide.applycollegeguide.base.BaseActivity;


/**
 * 启动页
 * Created by md on 2017/12/14.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = new View(this);
        view.setBackgroundResource(R.drawable.qidongye2);
        setContentView(view);

        view.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, MainActivity.class));


//                if (UserManager.getUserInfo(SplashActivity.this) != null) {
//                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                } else {
//                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                }

                finish();
            }
        }, 400);
    }
}
