package com.floridapoly.alex.cryptocurrency.service;

import android.net.Uri;
import android.os.AsyncTask;

import com.floridapoly.alex.cryptocurrency.data.CurrentValue;

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

public class CryptoCompareService {
    private CryptocurrencyCallback callback;
    private String currency;
    private Exception error;

    public CryptoCompareService(CryptocurrencyCallback callback) {
        this.callback = callback;
    }

    public String getCurrency() {
        return currency;
    }

    public void refreshCurrency(final String currency) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {

                //passed variable to query to access info on specific currency
                String YQL = String.format("%s", currency);

                //API query currently being used
                String endpoint = String.format("https://min-api.cryptocompare.com/data/pricemultifull?fsyms=%s&tsyms=USD", Uri.encode(YQL));
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
                    callback.serviceFailure(error);
                    return;
                }
                try {
                    JSONObject data = new JSONObject(s);

                    //needs to point at JSOn object - traversing the returned json data
                    JSONObject queryResults = data.optJSONObject("RAW").optJSONObject(currency).optJSONObject("USD");

                    // Option to check if there is an error with symbol - would only apply if we implemented
                    // a feature where a user types the symbol of the currency.
                    /*
                    if (response == "Error") {
                        callback.serviceFailure(new CurrencyException("Invalid Cryptocurrency symbol: " + currency));
                        return;
                    }
                    */

                    //TODO: Check if there is any data that is empty when returned from API.

                    CurrentValue currentValue = new CurrentValue();
                    currentValue.populate(queryResults);

                    //if connection is successful and there are no errors, pass data to callback success
                    callback.serviceSuccess(currentValue);


                } catch (JSONException e) {
                    callback.serviceFailure(e);
                }


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
