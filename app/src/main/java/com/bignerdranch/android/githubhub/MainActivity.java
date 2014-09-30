package com.bignerdranch.android.githubhub;

import android.support.v4.app.Fragment;

import com.bignerdranch.android.githubhub.utils.SingleFragmentActivity;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }

}
