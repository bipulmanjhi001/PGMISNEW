package com.jslps.pgmisnew.presenter;

import com.jslps.pgmisnew.interactor.LoginInteractor;
import com.jslps.pgmisnew.view.LoginView;

public class LoginPresenter implements LoginInteractor.OnLoginFinishedListener {
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenter(LoginView loginView, LoginInteractor loginInteractor) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }

    public void onDestroy() {
        loginView = null;
    }

    public void validateCredentials(String username, String password,boolean isCheck) {

        loginInteractor.login(username, password,isCheck, this);
        //temporarily this is commented
       // loginInteractor.versionCheck(this);
    }


    @Override
    public void onUsernameError() {
        loginView.setUsernameError();

    }

    @Override
    public void onPasswordError() {
        loginView.setPasswordError();
    }

    @Override
    public void onSuccess(String when) {
        loginView.navigateToHome(when);
    }

    @Override
    public void storePassword(boolean yes) {
        loginView.setStorePassword(yes);
    }

    @Override
    public void callLoginApi() {
        loginView.setCallLoginApi();
    }

    @Override
    public void callVersionCheckApi() {
        loginView.setCallVersionCheckApi();
    }

    @Override
    public void moveToNextActivity(String when) {
        loginView.navigateToHome(when);
    }

    public void showProgress(){
        loginView.showProgress();
    }


    public void hideProgress(){
        loginView.hideProgress();
    }

    public void revertProgress(){
        loginView.revertProgress();
    }

    public void version(String version){
        loginView.setVersion(version);
    }

    public void zoomin(){
        loginView.setZoomin();
    }

    public void shake(){
        loginView.setShake();
    }

    public void sharedUser(){
        loginView.setUser();
    }

    public void passwordIcon(){
        loginView.setPasswordIcon();
    }

    public void passwordVisibility(float x,float y){
        loginView.setPasswordVisibility(x,y);
    }

    public void setFont(){
        loginView.setFont();
    }

    public void consumeData(String data,String username,String password){
        loginInteractor.consumeData(this,data,username,password);
    }

}
