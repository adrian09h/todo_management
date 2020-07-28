package com.nuasolutions.todomanagement.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;


import com.nuasolutions.todomanagement.data.Resource;
import com.nuasolutions.todomanagement.data.local.dao.AccessTokenDAO;
import com.nuasolutions.todomanagement.data.local.entity.AccessTokenEntity;
import com.nuasolutions.todomanagement.data.remote.api.OnboardingAPIService;
import com.nuasolutions.todomanagement.data.repository.AccessTokenRepository;

import java.util.List;

import javax.inject.Inject;

public class LoginViewModel extends BaseViewModel {

    private AccessTokenRepository accessTokenRepository;

    private MutableLiveData<Resource<List<AccessTokenEntity>>> accessToken = new MutableLiveData<>();
    private ObservableBoolean loginEnabled = new ObservableBoolean(false);

    @Inject
    public LoginViewModel(AccessTokenDAO accessTokenDAO, OnboardingAPIService apiService) {
        accessTokenRepository = new AccessTokenRepository(accessTokenDAO, apiService);
    }

    public void login(String email, String password, boolean forceToRequest) {
        accessTokenRepository.getAccessToken(email, password, forceToRequest)
            .subscribe(resource -> accessToken.postValue(resource));
    }

    public ObservableBoolean getLoginEnabled() {
        return loginEnabled;
    }

    public void setLoginEnabled(Boolean loginEnabled) {
        this.loginEnabled.set(loginEnabled);
    }

    public MutableLiveData<Resource<List<AccessTokenEntity>>> getAccessToken() {return accessToken;}

}