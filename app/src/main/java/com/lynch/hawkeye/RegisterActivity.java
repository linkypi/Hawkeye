package com.lynch.hawkeye;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.lynch.hawkeye.config.Credential;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.handler.UMWXHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RegisterActivity extends BaseActivity {

    protected Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = this;
        ImageButton btn_register_back = (ImageButton) findViewById(R.id.btn_register_back);
        btn_register_back.setOnClickListener(new View.OnClickListener() {
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
