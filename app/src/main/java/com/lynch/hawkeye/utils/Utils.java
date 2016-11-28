package com.lynch.hawkeye.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by linxueqi on 2016/11/22 0022.
 */

public class Utils {

    public static Boolean readBoolean(Context context,String key) {
        SharedPreferences share = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        if (share == null || !share.contains(key)) {
            return false;
        }
        return share.getBoolean(key, false);
    }

    public static void writeBoolean(Context context,String key, Boolean value) {
        SharedPreferences share = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        if (share != null) {
            SharedPreferences.Editor editor = share.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }
    }

    public static boolean isFirstOpen(Context context)
    {
        boolean firstOpen = readBoolean(context,"first_open");
        if(!firstOpen){
            writeBoolean(context,"first_open",true);
        }
        return firstOpen;
    }

    public static boolean isNullOrEmpty(String text) {
        return text==null||text.equals("");
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
