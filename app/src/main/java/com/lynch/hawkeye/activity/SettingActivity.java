package com.lynch.hawkeye.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.graphics.ColorUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lynch.hawkeye.R;
import com.lynch.hawkeye.config.AppContext;
import com.lynch.hawkeye.config.Constants;
import com.lynch.hawkeye.model.LoginDto;

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout layoutSetting;
    private Button btnLogout;
    private Color defaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        layoutSetting = (LinearLayout)findViewById(R.id.layout_setting_back);
        btnLogout = (Button)findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(this);
        layoutSetting.setOnClickListener(this);

        if(AppContext.hasLogin){
            btnLogout.setBackgroundColor(Color.parseColor("#c75b5b"));
            btnLogout.setText("退 出 登 录");
        }
        else{
            //btnLogout.setBackground(Color.rgb("#c24848"));
            btnLogout.setText("登 录");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_setting_back:
                LoginDto data = new LoginDto(false,"");
                setActResult(data);
                break;
            case R.id.btn_logout:
                loginOut();
        }
    }

    private void loginOut(){
        if(AppContext.hasLogin){
            LoginDto data = new LoginDto(false,"");
            setActResult(data);
        }else{
            Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
            startActivityForResult(intent, Constants.Login_Callback);
        }
    }

    private void setActResult(LoginDto data){
//        LoginDto data = new LoginDto(false,"");
        Intent intent2 = SettingActivity.this.getIntent().putExtra("data",data);
        setResult(Constants.Login_Callback,intent2);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case Constants.Login_Callback:
                LoginDto loginDto = (LoginDto)data.getExtras().getSerializable("data");
                //if(loginDto==null) return;
//                LoginDto data = new LoginDto(false,"");
                Intent intent2 = SettingActivity.this.getIntent().putExtra("data",loginDto);
                setResult(Constants.Login_Callback,intent2);
                finish();
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}

