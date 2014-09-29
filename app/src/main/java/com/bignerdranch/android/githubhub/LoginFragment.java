package com.bignerdranch.android.githubhub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.eclipse.egit.github.core.service.OAuthService;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginFragment extends Fragment {

    @InjectView(R.id.username) TextView usernameTextView;
    @InjectView(R.id.password) TextView passwordTextView;
    @InjectView(R.id.signin) Button signinButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, layout);
        return layout;
    }

    @OnClick({R.id.signin})
    public void signin() {
        OAuthService oauthService = new OAuthService();
        oauthService.getClient().setCredentials(usernameTextView.getText().toString(), passwordTextView.getText().toString());
    }

}