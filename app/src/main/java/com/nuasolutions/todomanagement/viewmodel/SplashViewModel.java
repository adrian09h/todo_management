package com.nuasolutions.todomanagement.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.nuasolutions.todomanagement.data.Resource;
import com.nuasolutions.todomanagement.data.local.dao.AccessTokenDAO;
import com.nuasolutions.todomanagement.data.local.entity.AccessTokenEntity;
import com.nuasolutions.todomanagement.data.remote.api.OnboardingAPIService;
import com.nuasolutions.todomanagement.data.repository.AccessTokenRepository;

import java.util.List;

import javax.inject.Inject;

import static io.reactivex.internal.operators.observable.ObservableBlockingSubscribe.subscribe;

public class SplashViewModel extends BaseViewModel {

    private AccessTokenRepository accessTokenRepository;
    private MutableLiveData<Resource<List<AccessTokenEntity>>> mAccessTokenLiveData = new MutableLiveData<>();
    @Inject
    public SplashViewModel(AccessTokenDAO accessTokenDAO, OnboardingAPIService apiService) {
        accessTokenRepository = new AccessTokenRepository(accessTokenDAO, apiService);
    }

    @SuppressLint("CheckResult")
    public void checkAccessToken() {
        accessTokenRepository.getAccessToken("", "", false)
            .subscribe(resource -> mAccessTokenLiveData.postValue(resource));
    }

    public MutableLiveData<Resource<List<AccessTokenEntity>>> getAccessTokenLiveData() {
        return mAccessTokenLiveData;
    }

}