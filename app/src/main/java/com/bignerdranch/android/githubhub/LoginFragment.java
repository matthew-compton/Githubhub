package com.bignerdranch.android.githubhub;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.githubhub.utils.DialogUtils;
import com.bignerdranch.android.githubhub.utils.TextUtils;
import com.bignerdranch.android.githubhub.utils.ThreadUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

    public static final String EXTRA_USERNAME = TAG + ".username";
    public static final String EXTRA_PASSWORD = TAG + ".password";

    @InjectView(R.id.username) TextView usernameTextView;
    @InjectView(R.id.password) TextView passwordTextView;
    @InjectView(R.id.signin) Button signinButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, layout);
        return layout;
    }

    @OnEditorAction(R.id.username)
    public boolean usernameOnEditorAction() {
        return passwordTextView.requestFocus();
    }

    @OnEditorAction(R.id.password)
    public boolean passwordOnEditorAction() {
        signin();
        return true;
    }

    @OnClick({R.id.signin})
    public void signinOnClick() {
        signin();
    }

    private void signin() {
        new LoginAsyncTask().execute();
    }

    private class LoginAsyncTask extends AsyncTask<Void, Void, Void> {

        private String username;
        private String password;

        private boolean loginSucceeded = true;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DialogUtils.showLoadingDialog(getActivity().getSupportFragmentManager(), false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            ThreadUtils.pause(1000);
            username = usernameTextView.getText().toString();
            password = passwordTextView.getText().toString();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                loginSucceeded = false;
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            DialogUtils.hideLoadingDialog(getActivity().getSupportFragmentManager());
            if (loginSucceeded) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(EXTRA_USERNAME, username);
                intent.putExtra(EXTRA_PASSWORD, password);
                startActivity(intent);
                getActivity().finish();
            } else {
                Toast.makeText(getActivity(), getString(R.string.login_failure), Toast.LENGTH_SHORT).show();
            }

        }

    }

}