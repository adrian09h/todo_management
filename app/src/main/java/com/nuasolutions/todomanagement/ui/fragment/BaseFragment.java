package com.nuasolutions.todomanagement.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.nuasolutions.todomanagement.ui.MainActivity;

public class BaseFragment extends Fragment {
    protected MainActivity activity;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    protected void showErrorSnack(String errMsg) {
        Snackbar snackBar = Snackbar.make(activity.findViewById(android.R.id.content),
            errMsg, Snackbar.LENGTH_SHORT);
        snackBar.show();
    }
}