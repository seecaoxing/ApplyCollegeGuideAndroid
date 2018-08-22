package com.xyz.core.util;

import android.content.res.Resources;
import android.util.TypedValue;

import com.xyz.core.Core;

/**
 * Created by heq on 17/9/11.
 */

public class DensityUtils {
    public static float dp2px(float dp) {
        Resources res = Core.context().getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

    public static float sp2px(float sp) {
        Resources res = Core.context().getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, res.getDisplayMetrics());
    }
}
