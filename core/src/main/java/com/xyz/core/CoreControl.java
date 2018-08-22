package com.xyz.core;

import android.app.Application;
import android.content.Context;

/**
 * Created by taiwei on 2017/9/18.
 */

public class CoreControl {

    private Application application;

    CoreControl(Application application) {
        this.application = application;
    }

    Context getContext() {
        return application;
    }

}
