package com.lynch.hawkeye.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.lynch.hawkeye.R;
import com.lynch.hawkeye.config.AppContext;
import com.lynch.hawkeye.utils.Utils;
import com.lynch.hawkeye.utils.Validator;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class RegisterActivity extends BaseActivity {

    protected Context mContext;
    private Button btn_register,btn_getcode;
    private static int code;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //overridePendingTransition (R.anim.in_from_right, R.anim.in_from_right);

        mContext = this;
        progressbar = (ProgressBar)findViewById(R.id.progressbar);
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_getcode = (Button)findViewById(R.id.btn_getcode);
        btn_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText)findViewById(R.id.ed_reg_phone);
                String phone = text.getText().toString();
                if(Utils.isNullOrEmpty(phone)){
                    showMsg("手机号不能为空."); return ;
                }
                if(!Validator.isMobile(phone)){
                    showMsg("您输入的手机号有误."); return;
                }

                //progressbar.setVisibility(View.VISIBLE);
//                ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
////                progressDialog.setTitle("显示进度条");
//                progressDialog.setMessage("加载中...");
//                progressDialog.setCancelable(true);
//                progressDialog.show();


                showProgress("注册中...");
                try {
                    code = (int)(Math.random()*10000);
//                    progressBar.show("注册中...");
                    // Simulate network access.
                    Thread.sleep(1500);
//                    progressDialog.hide();
//                    progressBar.hide();
                    hideProgress();
                    //progressbar.setVisibility(View.INVISIBLE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }catch (Exception ex){
                    ex.printStackTrace();
                    Log.d("pgbar", "onClick: "+ ex.getStackTrace());
                }

                showMsg(String.format("验证码发送成功！(%d)" , code));
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ed_reg_phone = (EditText)findViewById(R.id.ed_reg_phone);
                EditText ed_reg_code = (EditText)findViewById(R.id.ed_reg_code);
                EditText ed_reg_pass = (EditText)findViewById(R.id.ed_reg_pass);
                EditText ed_reg_nick = (EditText)findViewById(R.id.ed_reg_nick);

                if(Utils.isNullOrEmpty(ed_reg_phone.getText().toString()))
                {
                    showMsg("手机号不能为空."); return;
                }
                if(Utils.isNullOrEmpty(ed_reg_code.getText().toString()))
                {
                    showMsg("验证码不能为空."); return;
                }
                if(Utils.isNullOrEmpty(ed_reg_pass.getText().toString()))
                {
                    showMsg("密码不能为空."); return;
                }
                if(ed_reg_pass.getText().toString().length() < 5)
                {
                    showMsg("密码至少6位."); return;
                }
                if(Utils.isNullOrEmpty(ed_reg_nick.getText().toString()))
                {
                    showMsg("昵称不能为空."); return;
                }

                AppContext.user_name = ed_reg_phone.getText().toString();
                AppContext.password = ed_reg_pass.getText().toString();
                showMsg("注册成功.");
            }
        });

        LinearLayout layout_register_back = (LinearLayout) findViewById(R.id.layout_register_back);
        layout_register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,LoginActivity.class);
                startActivity(intent);
                ((Activity)mContext).finish();
            }
        });
    }


    // 第三方登录
    public void thirdLogin(int type, Map<String, Object> info, String expires_in, SHARE_MEDIA share_MEDIA) {
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("accesstoken", info.get("access_token").toString()));
//        params.add(new BasicNameValuePair("logo", info.get("profile_image_url").toString()));
//        if ("男".equals(info.get("gender").toString())) {
//            params.add(new BasicNameValuePair("gender", "1"));
//        } else if ("女".equals(info.get("gender").toString())) {
//            params.add(new BasicNameValuePair("gender", "0"));
//        } else {
//            params.add(new BasicNameValuePair("gender", info.get("gender").toString()));
//        }
//        params.add(new BasicNameValuePair("nick", info.get("screen_name").toString()));
//        if (type == 1) {
//            params.add(new BasicNameValuePair("openid", info.get("openid").toString()));
//        } else {
//            params.add(new BasicNameValuePair("openid", info.get("uid").toString()));
//        }
//        params.add(new BasicNameValuePair("expires_in", expires_in));
//        params.add(new BasicNameValuePair("type", type + ""));
//        httpCilents.post(Config.ACCUPASS_THREE_LOGIN, params, new WebServiceCallBack() {
//
//            @Override
//            public void callBack(int code, Object result) {
//                dismissProgress();
//                switch (code) {
//                    case Config.RESULT_CODE_SUCCESS:
//                        application.showMsg("登录成功");
//                        UserInfo userInfo = JSON.parseObject(result.toString(), UserInfo.class);
//                        application.setUserInfo(userInfo);
//                        finish();
//                        break;
//                    case Config.RESULT_CODE_ERROR:
//                        application.showMsg(result.toString());
//                        break;
//                }
//            }
//        });
    }

}
