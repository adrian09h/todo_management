package com.nuasolutions.todomanagement.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nuasolutions.todomanagement.data.Resource;
import com.nuasolutions.todomanagement.data.local.dao.AccessTokenDAO;
import com.nuasolutions.todomanagement.data.local.entity.AccessTokenEntity;
import com.nuasolutions.todomanagement.data.remote.api.OnboardingAPIService;
import com.nuasolutions.todomanagement.data.repository.AccessTokenRepository;

import java.util.List;

import javax.inject.Inject;

public class SignupViewModel extends BaseViewModel {

    private ObservableBoolean mSignupEnabled = new ObservableBoolean(false);
    private AccessTokenRepository mAccessTokenRepository;
    private MutableLiveData<Resource<List<AccessTokenEntity>>> mAccessTokenLiveData = new MutableLiveData<>();

    @Inject
    public SignupViewModel(AccessTokenDAO accessTokenDAO, OnboardingAPIService apiService) {
        mAccessTokenRepository = new AccessTokenRepository(accessTokenDAO, apiService);
    }

    public void signup(String name, String email, String password, String passwordConfirm) {
        mAccessTokenRepository.signup(name, email, password, passwordConfirm)
            .subscribe(resource -> mAccessTokenLiveData.postValue(resource));
    }

    public ObservableBoolean getSignupEnabled() {
        return mSignupEnabled;
    }

    public void setSingupEnabled(Boolean enabled) {
        this.mSignupEnabled.set(enabled);
    }

    public MutableLiveData<Resource<List<AccessTokenEntity>>> getAccessTokenLiveData() {return mAccessTokenLiveData;}
}