package com.bignerdranch.android.githubhub;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.githubhub.utils.DialogUtils;
import com.bignerdranch.android.githubhub.utils.ThreadUtils;

import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;

import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private static final String BNR_ORG_NAME = "bignerdranch";

    private GitHubClient mGitHubClient;
    private String mUsername;
    private String mPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        mUsername = intent.getStringExtra(LoginFragment.EXTRA_USERNAME);
        mPassword = intent.getStringExtra(LoginFragment.EXTRA_PASSWORD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, layout);

        mGitHubClient = new GitHubClient();
        mGitHubClient.setCredentials(mUsername, mPassword);

        setHasOptionsMenu(true);
        return layout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                new FetchAsyncTask().execute();
                break;
            case R.id.logout:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class FetchAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DialogUtils.showLoadingDialog(getActivity().getSupportFragmentManager(), false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            ThreadUtils.pause(1000);
            RepositoryService repositoryService = new RepositoryService();
            try {
                for (Repository repo : repositoryService.getOrgRepositories(BNR_ORG_NAME)) {
                    Log.d(TAG, repo.getName());
                    for (Contributor contributor : repositoryService.getContributors(repo, true)) {
                        Log.d(TAG, "\t" + contributor.getName());
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            DialogUtils.hideLoadingDialog(getActivity().getSupportFragmentManager());
        }

    }

}