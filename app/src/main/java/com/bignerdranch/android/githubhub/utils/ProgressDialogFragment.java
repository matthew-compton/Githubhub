package com.bignerdranch.android.githubhub.utils;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.githubhub.R;

public class ProgressDialogFragment extends DialogFragment {

    public static ProgressDialogFragment newInstance() {
        return new ProgressDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ProgressDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_progress, container, false);
    }
}