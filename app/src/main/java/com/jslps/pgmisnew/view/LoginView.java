package com.jslps.pgmisnew.view;

public interface LoginView {
    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void setStorePassword(boolean value);

    void navigateToHome(String when);

    void setCallLoginApi();

    void setVersion(String version);

    void setCallVersionCheckApi();

    void setZoomin();

    void setShake();

    void setUser();

    void setPasswordIcon();

    void setPasswordVisibility(float x, float y);

    void setFont();

    void revertProgress();


}
