package com.bignerdranch.android.githubhub;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.githubhub.utils.TextUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

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
        new LoginAsyncTask().execute();
    }

    private class LoginAsyncTask extends AsyncTask<Void, Void, Void> {

        private boolean loginSucceeded = true;

        @Override
        protected Void doInBackground(Void... params) {
            String username = usernameTextView.getText().toString();
            String password = passwordTextView.getText().toString();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                loginSucceeded = false;
                return null;
            }

            // todo - login

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (loginSucceeded) {
                Toast.makeText(getActivity(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), getString(R.string.login_failure), Toast.LENGTH_SHORT).show();
            }
        }

    };

}