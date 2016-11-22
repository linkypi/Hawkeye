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
        if(firstOpen){
            writeBoolean(context,"first_open",false);
        }
        return firstOpen;
    }

}
