package com.nuasolutions.todomanagement.viewmodel;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends BaseViewModel {
    private MutableLiveData<String> accessToken = new MutableLiveData<>();
    private ObservableBoolean loginEnabled = new ObservableBoolean(false);

    public ObservableBoolean getLoginEnabled() {
        return loginEnabled;
    }

    public void setLoginEnabled(Boolean loginEnabled) {
        this.loginEnabled.set(loginEnabled);
    }

    public LiveData<String> getAccessToken() {return accessToken;}

    public void storeAccessToken(String accessToken) {

    }

    public void loginToServer(String email, String password) {

    }
}