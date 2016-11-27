package com.lynch.hawkeye.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lynch.hawkeye.component.CustomProgressBar;
import com.lynch.hawkeye.component.MyProgressDialog;
import com.lynch.hawkeye.fragment.BaseFragment;

/**
 * Created by linxueqi on 2016/11/23 0023.
 */

public class BaseActivity extends AppCompatActivity {
    protected CustomProgressBar progressBar;
    protected MyProgressDialog myProgressDialog;
    private Context mContext;
    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        init();
        //progressBar = new CustomProgressBar(this,BaseActivity.class);
    }

    public void showMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    /** 初始化自定义对话框 */
    private void init() {
        myProgressDialog = new MyProgressDialog(mContext);
        myProgressDialog.setMyCancelable(true);
        myProgressDialog.setMyTouchOutside(false);
    }

    /**
     * @param
     */
    public void showProgress(String message) {
        myProgressDialog.setMyMessage(message);
        myProgressDialog.show();
    }

    public void showDefaultProgress() {
        myProgressDialog.setMyMessage("数据加载中，请稍候！");
        myProgressDialog.show();
    }

    /**
     * @param
     */
    public void setMessage(String message) {
        myProgressDialog.setMyMessage(message);
    }

    public void hideProgress() {
        if (myProgressDialog != null) {
            myProgressDialog.hide();
        }
    }
}
