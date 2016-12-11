package com.lynch.hawkeye.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lynch.hawkeye.R;
import com.lynch.hawkeye.config.Constants;
import com.lynch.hawkeye.config.Credential;
import com.lynch.hawkeye.config.AppContext;
import com.lynch.hawkeye.model.Dto;
import com.lynch.hawkeye.model.LoginDto;
import com.lynch.hawkeye.utils.SystemBarTintManager;
import com.lynch.hawkeye.utils.Validator;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements LoaderCallbacks<Cursor>, OnClickListener {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "15012795635:123456", "182867664@qq.com:123456"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    private Context mContext = null;

    // UI references.
    private AutoCompleteTextView mAccountView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private UMShareAPI mShareAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mAccountView = (AutoCompleteTextView) findViewById(R.id.email);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mPasswordView = (EditText) findViewById(R.id.password);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        LinearLayout layout_login_back = (LinearLayout) findViewById(R.id.layout_login_back);
        Button btn_register = (Button)findViewById(R.id.btn_register);

        populateAutoComplete();

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mContext = this;
        mEmailSignInButton.setOnClickListener(this);
        layout_login_back.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        try {
            PlatformConfig.setWeixin(Credential.WX_APPID, Credential.WX_APPSECRET);
            PlatformConfig.setSinaWeibo(Credential.Weibo_APPID, Credential.Wweibo_APPSECRET);
            mShareAPI = UMShareAPI.get(this);
            //setStatuBarAndTitleBarTotheSame();
        } catch (Exception ex) {
            Log.d("umeng", "ex: "+ex.getCause());
        }

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.email_sign_in_button:
                attemptLogin();
                break;
            case R.id.layout_login_back:
                //                Intent intent = new Intent(mContext,MainActivity.class);
//                startActivity(intent);
                LoginDto data = new LoginDto(false,"");
                Intent intent2 = LoginActivity.this.getIntent().putExtra("data",data);
                setResult(Constants.Login_Callback,intent2);
                finish();
                break;
            case R.id.btn_register:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                //overridePendingTransition (R.anim.in_from_right, R.anim.out_to_right);
                break;
        }
    }
    /**
     * 将状态栏与标题栏颜色保持一致
     */
    private void setStatuBarAndTitleBarTotheSame()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //系统版本大于19
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.txt_green);//设置标题栏颜色，此颜色在color中声明
        Class clazz = this.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if(true){
                extraFlagField.invoke(this.getWindow(),darkModeFlag,darkModeFlag);//状态栏透明且黑色字体
            }else{
                extraFlagField.invoke(this.getWindow(), 0, darkModeFlag);//清除黑色字体
            }
        }catch (Exception e){

        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;        // a|=b的意思就是把a和b按位或然后赋值给a
                                            // 按位或的意思就是先把a和b都换成2进制，然后用或操作，相当于a=a|b
        } else {
            winParams.flags &= ~bits;        //&是位运算里面，与运算  a&=b相当于 a = a&b  ~非运算符
        }
        win.setAttributes(winParams);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
//            Snackbar.make(mAccountView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
//                    .setAction(android.R.string.ok, new OnClickListener() {
//                        @Override
//                        @TargetApi(Build.VERSION_CODES.M)
//                        public void onClick(View v) {
//                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//                        }
//                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mAccountView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String account = mAccountView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        String errMsg = "";

        if(TextUtils.isEmpty(password))
        {
            errMsg = getString(R.string.error_password_notempty);
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            errMsg = getString(R.string.error_invalid_password);
            cancel = true;
        }

        // Check for a valid email/phone address.
        if (TextUtils.isEmpty(account)) {
//            mAccountView.setError(getString(R.string.error_account_required));
            errMsg = getString(R.string.error_account_required);
            focusView = mAccountView;
            cancel = true;
        }
        else if(!validateAccount(account)){
//            mAccountView.setError(getString(R.string.error_invalid_account));
            errMsg = getString(R.string.error_invalid_account);
            focusView = mAccountView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            //focusView.requestFocus();
            Toast.makeText(getApplicationContext(),errMsg,Toast.LENGTH_SHORT).show();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(account, password);
            mAuthTask.execute((Void) null);
        }
    }
    private boolean validateAccount(String account){
        return Validator.isMobile(account)||Validator.isEmail(account);
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mAccountView.setAdapter(adapter);
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    public void onThirdLogin(View view)
    {
        switch (view.getId()){
            case R.id.btn_weibologin:
                getAuth(SHARE_MEDIA.SINA);
                break;
            case R.id.btn_qqlogin:
                getAuth(SHARE_MEDIA.QZONE);
                break;
            case R.id.btn_wxlogin:
                getAuth(SHARE_MEDIA.WEIXIN);
                break;
        }
    }

    // 第三方授权
    public void getAuth(final SHARE_MEDIA media_type) {

        mShareAPI.doOauthVerify(this, media_type, new UMAuthListener() {

            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

                Log.i("auth info", "vaule:" + data.toString());
                if (data != null) {
                    showMsg("授权成功");
                    getThirdInfo(media_type, data.get("expires_in"));
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
    public void getThirdInfo(final SHARE_MEDIA media_type, final String expires_in) {
        mShareAPI.getPlatformInfo(this, media_type, new UMAuthListener() {

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

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            boolean ok = false;
            if (success) {
                ok = true;
                showMsg(getString(R.string.success_login));
            } else {
                showMsg(getString(R.string.failed_login));
            }
            LoginDto data = new LoginDto(ok,"Lynch.");
            Intent intent = LoginActivity.this.getIntent().putExtra("data",data);
            setResult(Constants.Login_Callback,intent);
            finish();
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

