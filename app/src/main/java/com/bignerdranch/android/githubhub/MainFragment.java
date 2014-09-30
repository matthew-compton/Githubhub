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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.oauth.OAuth;
import io.oauth.OAuthCallback;
import io.oauth.OAuthData;
import io.oauth.OAuthRequest;

public class MainFragment extends Fragment implements OAuthCallback {

    private static final String TAG = MainFragment.class.getSimpleName();

    private static final String OAUTH_PUBLIC_KEY = "mIPwQwd_LhLBISMxADbqD-62fOM";
    private static final String OAUTH_PROVIDER = "github";

    private OAuthData mOAuthData;

    @InjectView(R.id.result) TextView resultTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, layout);
        setHasOptionsMenu(true);

        final OAuth o = new OAuth(getActivity());
        o.initialize(OAUTH_PUBLIC_KEY);
        o.popup(OAUTH_PROVIDER, this);

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
                // TODO - logout
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * Callback from OAuthCallback
     */
    public void onFinished(OAuthData data) {
        mOAuthData = data;
        new FetchAsyncTask().execute();
    }

    private class FetchAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DialogUtils.showLoadingDialog(getActivity().getSupportFragmentManager(), false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            mOAuthData.http("https://api.github.com/user", new OAuthRequest() {

                private URL url;
                private URLConnection con;

                @Override
                public void onSetURL(String _url) {
                    try {
                        url = new URL(_url);
                        con = url.openConnection();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSetHeader(String header, String value) {
                    con.addRequestProperty(header, value);
                }

                @Override
                public void onReady() {
                    try {
                        BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        StringBuilder total = new StringBuilder();
                        String line;
                        while ((line = r.readLine()) != null) {
                            total.append(line);
                        }
                        JSONObject result = new JSONObject(total.toString());
                        resultTextView.setText(result.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String message) {
                    Log.e(TAG, "Error: " + message);
                }

            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            DialogUtils.hideLoadingDialog(getActivity().getSupportFragmentManager());
        }

    }

}