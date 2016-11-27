package com.lynch.hawkeye.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import com.lynch.hawkeye.component.CustomProgressBar;
import com.lynch.hawkeye.component.MyProgressDialog;

/**
 * Created by JL on 2016/11/26/0026.
 */

public class BaseFragment  extends Fragment {
//    protected MyProgressDialog myProgressDialog;
    protected Context mContext;
    private CustomProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        progressBar = new CustomProgressBar(mContext,BaseFragment.class);
//        httpCilents = new HttpCilents(mContext);
//        dbManager = new DBManager(mContext);
//        application = (AccuApplication) getActivity().getApplication();
        //init();
    }
    /** 初始化自定义对话框 */
//    private void init() {
//        myProgressDialog = new MyProgressDialog(mContext);
//        myProgressDialog.setMyCancelable(true);
//        myProgressDialog.setMyTouchOutside(false);
//    }
//
//    /**
//     * @param 消息
//     */
//    public void showProgress(String message) {
//        myProgressDialog.setMyMessage(message);
//        myProgressDialog.show();
//    }
//
//    public void showDefaultProgress() {
//        myProgressDialog.setMyMessage("数据加载中，请稍候！");
//        myProgressDialog.show();
//    }
//
//    /**
//     * @param 消息
//     */
//    public void setMessage(String message) {
//        myProgressDialog.setMyMessage(message);
//    }
//
//    public void hideProgress() {
//        if (myProgressDialog != null) {
//            myProgressDialog.hide();
//        }
//    }
}
