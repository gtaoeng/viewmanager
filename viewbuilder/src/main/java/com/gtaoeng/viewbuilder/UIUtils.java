package com.gtaoeng.viewbuilder;

import android.content.Context;


/**
 * 和UI相关的实用工具类
 */
public class UIUtils {

    public static Context mContext;

    /**
     * dp转换成像素
     *
     * @param dp
     * @return
     */
    public static int dp2Px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int dp2sp(float dp) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) ((dp * scale + 0.5f) / fontScale + 0.5f);
    }


}
