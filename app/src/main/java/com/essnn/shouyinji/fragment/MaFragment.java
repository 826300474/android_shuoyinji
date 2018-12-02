package com.essnn.shouyinji.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.essnn.shouyinji.R;
import com.essnn.shouyinji.utils.CallBackInterface;

public class MaFragment extends Fragment {
    private CallBackInterface mCallBackInterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ma,container,false);
        mCallBackInterface = (CallBackInterface) getActivity();
        return view;
    }
}
