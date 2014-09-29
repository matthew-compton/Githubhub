package com.bignerdranch.android.githubhub;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.githubhub.utils.TextUtils;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private static final String BNR_ORG_NAME = "bignerdranch";

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

        private boolean loginSucceeded = true;

        @Override
        protected Void doInBackground(Void... params) {
            String username = usernameTextView.getText().toString();
            String password = passwordTextView.getText().toString();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                loginSucceeded = false;
                return null;
            }

            GitHubClient gitHubClient = new GitHubClient();
            gitHubClient.setCredentials(username, password);

            RepositoryService repositoryService = new RepositoryService();
            try {
                for(Repository repo : repositoryService.getOrgRepositories(BNR_ORG_NAME)) {
                    Log.d(TAG, repo.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (loginSucceeded) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else {
                Toast.makeText(getActivity(), getString(R.string.login_failure), Toast.LENGTH_SHORT).show();
            }
        }

    }

}