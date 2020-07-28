package com.nuasolutions.todomanagement.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nuasolutions.todomanagement.api.APIService;
import com.nuasolutions.todomanagement.api.RetrofitFactory;
import com.nuasolutions.todomanagement.model.ErrorEnvelope;
import com.nuasolutions.todomanagement.persistence.PersistanceManager;

import io.reactivex.disposables.Disposable;


public class BaseViewModel extends ViewModel {
    protected PersistanceManager persistanceManager = PersistanceManager.sharedInstance();
    protected APIService apiService = RetrofitFactory.getAPIService(persistanceManager.readAuthToken());
    protected MutableLiveData<Boolean> progress = new MutableLiveData<>();
    protected MutableLiveData<ErrorEnvelope> error = new MutableLiveData<>();
    protected Disposable disposable;

    public LiveData<ErrorEnvelope> error() {
        return error;
    }

    public LiveData<Boolean> progress() {
        return progress;
    }

    @Override
    protected void onCleared() {
        cancel();
    }

    protected void cancel() {
        dispose(disposable);
    }

    void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}