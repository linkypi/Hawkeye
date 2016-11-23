package com.lynch.hawkeye;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RegisterActivity extends BaseActivity {

    protected Context mContext;
    private   UMShareAPI  mShareAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = this;
        mShareAPI = UMShareAPI.get(RegisterActivity.this );
    }

    // 第三方授权
    public void getAuth(final int type, final SHARE_MEDIA media_type) {

        mShareAPI.doOauthVerify(RegisterActivity.this, media_type, new UMAuthListener() {

            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

                Log.i("auth info", "vaule:" + data.toString());
                if (data != null) {
                    showMsg("授权成功");
                    getThirdInfo(type, media_type, data.get("expires_in"));
                } else {
                    showMsg("授权失败");
                    //dismissProgress();
                }
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                //dismissProgress();
                showMsg("授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                showMsg("授权取消");//Authorize cancel
                //dismissProgress();
            }
        });
    }


    // 获取第三方资料
    public void getThirdInfo(final int type, final SHARE_MEDIA media_type, final String expires_in) {
        mShareAPI.getPlatformInfo(RegisterActivity.this, media_type, new UMAuthListener() {

            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

                Log.i("auth user info", "vaule:" + data.toString());
                if (data != null) {
                    showMsg("授权成功");
                    //getThirdInfo(type, share_MEDIA, value.getString("expires_in"));
                } else {
                    showMsg("授权失败");
                    //dismissProgress();
                }
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                //dismissProgress();
                showMsg("授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                showMsg("授权取消");//Authorize cancel
                //dismissProgress();
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
