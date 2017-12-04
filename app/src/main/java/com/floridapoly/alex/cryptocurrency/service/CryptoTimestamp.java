package com.floridapoly.alex.cryptocurrency.service;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.floridapoly.alex.cryptocurrency.PopWindow;
import com.floridapoly.alex.cryptocurrency.data.CurrentValue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Adam Seevers on 9/29/2017.
 * Cryptocurrency project - Mobile Dev
 */

public class CryptoTimestamp {
    private CryptoTimestampCallback callback;
    private Exception error;
    String[] JSONTime;
    String[] JSONValue;

    public CryptoTimestamp(CryptoTimestampCallback callback) {
        this.callback = callback;
    }

    @SuppressLint("StaticFieldLeak")
    public void refreshTimestamp(final String currency, final String timeIntervals) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {

                //passed variable to query to access info on specific currency
                String YQL = String.format("%s", currency);
                String TIME = String.format("%s", timeIntervals);

                //API query currently being used
                String endpoint = String.format("https://min-api.cryptocompare.com/data/histohour?fsym=%s&tsym=USD&limit=%s&aggregate=12", Uri.encode(YQL), Uri.encode(TIME));

                try {
                    URL url = new URL(endpoint);
                    URLConnection connection = url.openConnection();
                    InputStream  inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    // While there is information to read
                    while((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return result.toString();
                } catch (Exception e) {
                    error = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null && error != null) {
                    callback.timeStampServiceFailure(error);
                    return;
                }
                try {
                    JSONObject data = new JSONObject(s);
                    //needs to point at JSOn object - traversing the returned json data
                    JSONArray queryResults = data.getJSONArray("Data");
                    JSONTime = new String[queryResults.length()];
                    JSONValue = new String[queryResults.length()];
                    //create two string arrays one of timestamps and one of the values at that timestamp
                    for (int i = 0; i < queryResults.length(); i++) {
                        JSONObject mJSONObjProp = queryResults.getJSONObject(i);
                        JSONTime[i] = mJSONObjProp.getString("time");
                        JSONValue[i] = mJSONObjProp.getString("close");
                    }
                } catch (JSONException e) {
                    callback.timeStampServiceFailure(e);
                }
                    CurrentValue currentValue = new CurrentValue();
                    //pass the string arrays to the populate interface to assign them to the currentValue class.
                    currentValue.populate(JSONTime, JSONValue);

                    //if connection is successful and there are no errors, pass data to callback success
                    callback.timeStampServiceSuccess(currentValue);
            }

        }.execute(currency);
    }

    // use to handle errors when invalid symbols if editText feature implemented for users to search for their currency
    public class CurrencyException extends Exception {
        public CurrencyException(String detailMessage) {
            super(detailMessage);
        }
    }
}
