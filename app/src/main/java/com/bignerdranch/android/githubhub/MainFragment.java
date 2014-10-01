package com.bignerdranch.android.githubhub;

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
import android.widget.TextView;

import com.bignerdranch.android.githubhub.utils.DialogUtils;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GitHub;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    @InjectView(R.id.result) TextView resultTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, layout);
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
                // TODO - refresh
                new ConnectAsyncTask().execute();
                break;
            case R.id.logout:
                // TODO - logout
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ConnectAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DialogUtils.showLoadingDialog(getActivity().getSupportFragmentManager(), false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                GitHub hub = GitHub.connect();
                GHMyself me = hub.getMyself();
                Log.i(TAG, me.toString());
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