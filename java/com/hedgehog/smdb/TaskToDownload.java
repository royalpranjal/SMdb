package com.hedgehog.smdb;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TaskToDownload extends AsyncTask<String, Void, String> {

    DownloadTask listener;
    //    ProgressDialog dialog;
    Context mContext;
    String source;

    TaskToDownload(Context mContext, DownloadTask listener, String source) {
        this.mContext = mContext;
        this.listener = listener;
        this.source = source;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
//        dialog = ProgressDialog.show(mContext, "Loading", "");
    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        StringBuilder total = new StringBuilder();
        try {
            URL url = new URL(params[0]);
            Log.d("Url", "" + params[0]);
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return total.toString();
    }

    @Override
    protected void onPostExecute(String result) {
//        if (dialog != null && dialog.isShowing())
//            dialog.dismiss();
        listener.downloadComplete(result, source);

    }
}
