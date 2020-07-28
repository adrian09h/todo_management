package com.nuasolutions.todomanagement.data.repository;



import androidx.annotation.NonNull;

import com.nuasolutions.todomanagement.data.remote.api.OnboardingAPIService;
import com.nuasolutions.todomanagement.data.NetworkBoundResource;
import com.nuasolutions.todomanagement.data.Resource;
import com.nuasolutions.todomanagement.data.local.dao.AccessTokenDAO;
import com.nuasolutions.todomanagement.data.local.entity.AccessTokenEntity;
import com.nuasolutions.todomanagement.data.remote.model.requests.LoginRequest;
import com.nuasolutions.todomanagement.data.remote.model.response.AccessTokenResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import io.reactivex.Flowable;
import io.reactivex.Observable;

@Singleton
public class AccessTokenRepository {
    private AccessTokenDAO accessTokenDAO;
    private OnboardingAPIService apiService;
    public AccessTokenRepository(AccessTokenDAO accessTokenDAO, OnboardingAPIService apiService) {
        this.accessTokenDAO = accessTokenDAO;
        this.apiService = apiService;
    }

    public Observable<Resource<List<AccessTokenEntity>>> getAccessToken(String email,
                                                                        String password,
                                                                        boolean forcedRequest) {
        return new NetworkBoundResource<List<AccessTokenEntity>, AccessTokenResponse>() {

            @Override
            protected void saveCallResult(@NonNull AccessTokenResponse response) {
                List<AccessTokenEntity> entities = new ArrayList<>();
                entities.add(new AccessTokenEntity(response.accessToken));
                accessTokenDAO.insertAccessToken(entities);
            }

            @Override
            protected boolean shouldFetch() {
                return forcedRequest;
            }

            @NonNull
            @Override
            protected Flowable<List<AccessTokenEntity>> loadFromDb() {
                List<AccessTokenEntity> entities = accessTokenDAO.getAccessTokens();
                if(entities == null || entities.isEmpty()) {
                    return Flowable.empty();
                }
                return Flowable.just(entities);
            }

            @NonNull
            @Override
            protected Observable<Resource<AccessTokenResponse>> createCall() {
                return apiService.doLogin(new LoginRequest(email, password))
                    .flatMap(accessTokenResponse -> Observable.just(accessTokenResponse == null
                        ? Resource.error("Failed to get accessToken.", null)
                        : Resource.success(accessTokenResponse)));
            }
        }.getAsObservable();
    }
}
