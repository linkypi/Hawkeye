package com.lynch.hawkeye.component;

import android.content.Context;

import com.lynch.hawkeye.component.MyProgressDialog;

import java.util.HashMap;

/**
 * Created by JL on 2016/11/27/0027.
 */

public class CustomProgressBar {

    private static HashMap<Class,MyProgressDialog> maps = new HashMap<Class, MyProgressDialog>();
    private Class klass;

    public CustomProgressBar(){}
    public CustomProgressBar(Context context,Class klass){
        this.klass = klass;
        if(maps.containsKey(klass)) return;

        MyProgressDialog myProgressDialog = new MyProgressDialog(context);
        myProgressDialog.setMyCancelable(true);
        myProgressDialog.setMyTouchOutside(false);
        maps.put(klass,myProgressDialog);
    }

    /** 初始化自定义对话框 */
    private void init(Context context,Class klass) {
        this.klass = klass;
        if(maps.containsKey(klass)) return;

        MyProgressDialog myProgressDialog = new MyProgressDialog(context);
        myProgressDialog.setMyCancelable(true);
        myProgressDialog.setMyTouchOutside(false);
        maps.put(klass,myProgressDialog);
    }
    public void show(String message) {
        if(!maps.containsKey(klass)) return;

        MyProgressDialog progress = maps.get(klass);
        progress.setMyMessage(message);
        progress.showx();
    }

    public void showDefault(){
        show("数据加载中，请稍候！");
    }

    public void setMessage(String message) {
        if(!maps.containsKey(klass)) return;

        MyProgressDialog progress = maps.get(klass);
        progress.setMyMessage(message);
    }

    public void hide() {
        if(!maps.containsKey(klass)) return;
        maps.get(klass).hide();
    }

}
