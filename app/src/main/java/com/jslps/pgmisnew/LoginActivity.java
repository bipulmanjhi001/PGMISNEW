package com.jslps.pgmisnew;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.objects.AlertAction;
import com.jslps.pgmisnew.interactor.LoginInteractor;
import com.jslps.pgmisnew.presenter.LoginPresenter;
import com.jslps.pgmisnew.util.AlertView;
import com.jslps.pgmisnew.util.AppConstant;
import com.jslps.pgmisnew.util.CheckConnectivity;
import com.jslps.pgmisnew.util.GetFont;
import com.jslps.pgmisnew.util.GetUrlLogin;
import com.jslps.pgmisnew.util.MDMSharedPreference;
import com.jslps.pgmisnew.util.VersionChecker;
import com.jslps.pgmisnew.util.VolleyString;
import com.jslps.pgmisnew.view.LoginView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.zxy.recovery.core.Recovery;

import java.util.concurrent.ExecutionException;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class LoginActivity extends AppCompatActivity implements LoginView, VolleyString.VolleyListner {
    /*binding views*/
    @BindView(R.id.button3)
    CircularProgressButton btnlogin;
    @BindView(R.id.et_username)
    TextInputEditText etUsername;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.version)
    TextView tvVersion;
    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.checkBox2)
    CheckBox cbRememberMe;
    @BindView(R.id.imageView4)
    ImageView imgLogo;


    /*Defining objects*/
    LoginPresenter presenter;
    MDMSharedPreference mdmSharedPreference;

    /*Class Globals*/
    private String versionString;
    Boolean isPasswordshowing=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        /*initialiazation*/
        presenter = new LoginPresenter(this, new LoginInteractor());
        mdmSharedPreference = MDMSharedPreference.getInstance(this);
        
        //if application get crashed this is called
        crash();


        //methods
        getVersion();
        onTouch();

        //calling presenter methods
        presenter.zoomin();
        presenter.shake();
        presenter.sharedUser();
        presenter.passwordIcon();
        presenter.setFont();
    }

    private void crash() {
        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(LoginActivity.class)
                .recoverEnabled(true)
                .silent(false, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                .init(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onTouch() {
        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    presenter.passwordVisibility(event.getRawX(), etPassword.getRight() - etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width());
                }
                return false;
            }
        });
    }

    private void getVersion() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionString = pInfo.versionName;
            presenter.version(versionString);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showProgress() {
        btnlogin.startAnimation();
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setUsernameError() {
        presenter.revertProgress();
        new AlertView(this, getString(R.string.no_user_name), getString(R.string.username_blank), getString(R.string.try_again));
    }

    @Override
    public void setPasswordError() {
        presenter.revertProgress();
        new AlertView(this, getString(R.string.no_password), getString(R.string.password_blank), getString(R.string.try_again));
    }

    @Override
    public void setStorePassword(boolean value) {
        if (value) {
            mdmSharedPreference.putString("username", etUsername.getText().toString());
            mdmSharedPreference.putString("password", etPassword.getText().toString());
        } else {
            mdmSharedPreference.putString("username", "");
            mdmSharedPreference.putString("password", "");
        }
    }

    @Override
    public void navigateToHome(String when) {
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.right);
        btnlogin.doneLoadingAnimation(R.color.colorWhite,largeIcon);
        Intent intent = new Intent(this, PgActivity.class);
        intent.putExtra("when",when);
        startActivity(intent);
        finish();
    }

    @Override
    public void setCallLoginApi() {
        CheckConnectivity checkConnectivity = new CheckConnectivity();
        if(checkConnectivity.CheckConnection(this)){
            RequestQueue mRequestQueue;
            StringRequest mStringRequest;
            mRequestQueue = Volley.newRequestQueue(this);
            mStringRequest = new VolleyString(new GetUrlLogin(AppConstant.domain,AppConstant.loginMethod,etUsername.getText().toString(),etPassword.getText().toString()).getUrl(),AppConstant.logintbl,this).getString();

            mRequestQueue.add(mStringRequest);
        }else{
            new StyleableToast
                    .Builder(this)
                    .text(getString(R.string.internet_error))
                    .iconStart(R.drawable.wrong_icon_white)
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorPrimary))
                    .show();
        }
    }

    @Override
    public void setVersion(String version) {
        tvVersion.setText(version);
    }

    @Override
    public void setCallVersionCheckApi() {
        CheckConnectivity connectivity = new CheckConnectivity();
        if (connectivity.CheckConnection(this)) {
            VersionChecker versionChecker = new VersionChecker();
            try {
                String latestVersion = versionChecker.execute().get();
                if (latestVersion != null) {
                    if (Float.parseFloat(latestVersion) > Float.parseFloat(versionString)) {
                        alert(getString(R.string.new_update), getString(R.string.new_message));
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setZoomin() {
        Animation zoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        imgLogo.startAnimation(zoom);
    }

    @Override
    public void setShake() {
        Animation zoom = AnimationUtils.loadAnimation(this, R.anim.zoom_in_long);
        btnlogin.startAnimation(zoom);
    }

    @Override
    public void setUser() {
        etUsername.setText(mdmSharedPreference.getString("username"));
        etPassword.setText(mdmSharedPreference.getString("password"));
        cbRememberMe.setChecked(true);
    }

    @Override
    public void setPasswordIcon() {
        etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.password_view_icon, 0);
    }

    @Override
    public void setPasswordVisibility(float x, float y) {
        if (x >= y) {
            if (!isPasswordshowing) {
                etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.password_hide_icon, 0);
                etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                etPassword.setSelection(etPassword.length());
                isPasswordshowing = true;
            } else {
                etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.password_view_icon, 0);
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etPassword.setSelection(etPassword.length());
                isPasswordshowing = false;
            }
        }
    }

    @Override
    public void setFont() {
//        Typeface typeface = new GetFont(this).font();
//        tvVersion.setTypeface(typeface);
//        btnlogin.setTypeface(typeface);
//        tvRefresh.setTypeface(typeface);
//        cbRememberMe.setTypeface(typeface);
    }

    @Override
    public void revertProgress() {
        btnlogin.revertAnimation();
    }

    private void validateCredentials() {
        //showing progress here
        presenter.showProgress();
        presenter.validateCredentials(etUsername.getText().toString(), etPassword.getText().toString(), cbRememberMe.isChecked());
    }

    @Override
    public void onResponseSuccess(String tableIndentifier, String result) {
        if(tableIndentifier.equals(AppConstant.logintbl)){
            if(result.equals("[]")){
                btnlogin.revertAnimation();
                new StyleableToast
                        .Builder(this)
                        .text(getString(R.string.wronguser))
                        .iconStart(R.drawable.wrong_icon_white)
                        .textColor(Color.WHITE)
                        .backgroundColor(getResources().getColor(R.color.colorPrimary))
                        .show();
            }else{


                presenter.consumeData(result,etUsername.getText().toString(),etPassword.getText().toString());
            }
        }


    }

    @Override
    public void onResponseFailure(String tableIdentifier) {
        presenter.revertProgress();
        new StyleableToast
                .Builder(this)
                .text(getString(R.string.server_error))
                .iconStart(R.drawable.wrong_icon_white)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }

    @OnClick(R.id.button3)
    public void onViewClicked() {
        validateCredentials();
    }


    private void alert(String error,String message){
        com.irozon.alertview.AlertView alert = new com.irozon.alertview.AlertView(error, message, AlertStyle.DIALOG);
        alert.addAction(new AlertAction(getString(R.string.updateApp), AlertActionStyle.DEFAULT, action -> {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" +appPackageName)));
            }
        }));
        alert.show(LoginActivity.this);
    }



}
