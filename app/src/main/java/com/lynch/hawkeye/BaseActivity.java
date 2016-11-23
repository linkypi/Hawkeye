package com.lynch.hawkeye;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by linxueqi on 2016/11/23 0023.
 */

public class BaseActivity extends AppCompatActivity {

    public void showMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
