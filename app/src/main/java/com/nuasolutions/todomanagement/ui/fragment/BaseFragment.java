package com.nuasolutions.todomanagement.ui.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.nuasolutions.todomanagement.ui.MainActivity;

public class BaseFragment extends Fragment {
    protected MainActivity activity;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

}