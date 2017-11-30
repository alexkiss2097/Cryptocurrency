package com.floridapoly.alex.cryptocurrency;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.floridapoly.alex.cryptocurrency.data.CurrentValue;
import com.floridapoly.alex.cryptocurrency.service.CryptoCompareService;
import com.floridapoly.alex.cryptocurrency.service.CryptocurrencyCallback;

public class MainActivity extends Activity implements CryptocurrencyCallback {

    String[] cryptoCurrency, cryptoCurrencyName;

    private TextView marketCapTextView;
    private TextView volumeTextView;
    private TextView maxSupplyTextView;
    private TextView valueTextView;
    private TextView lastTradeView;
    private TextView exchangeTextView;

    private CryptoCompareService service;
    //private ProgressBar dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_start);

        //widget reference in xml layout
        final RelativeLayout relativeL = findViewById(R.id.relativeL);
        final ImageView currencyImage = findViewById(R.id.currencyImage);

        //instantiate variables used for API importing JSON files
        marketCapTextView = findViewById(R.id.valMarketCap);
        volumeTextView = findViewById(R.id.valVolume);
        maxSupplyTextView = findViewById(R.id.valMaxSupply);
        valueTextView = findViewById(R.id.valUSD);
        lastTradeView = findViewById(R.id.lastTradeIDValue);
        exchangeTextView = findViewById(R.id.exchangeValue);


        //services used for API - cryptocompare
        service = new CryptoCompareService(this);

        // Initial call to get API data for BTC using CryptoCompareService
        service.refreshCurrency("BTC");

        // Changes IMG based on spinner selection
        // TODO: used to run API query based on selection later
        cryptoCurrency = getResources().getStringArray(R.array.cryptoSelect);
        cryptoCurrencyName = getResources().getStringArray(R.array.cryptoSelectName);
        final Spinner cryptoSpinner = findViewById(R.id.cryptoSelect);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cryptoCurrency);

        cryptoSpinner.setAdapter(adapter);
        cryptoSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int index = arg0.getSelectedItemPosition();

                // TODO: currently passed variable currency from spinner object through service refresh works and pulls data

                String currencyString = cryptoCurrency[index];
                String currencyStringName = cryptoCurrencyName[index];
                String currImg = currencyString.toLowerCase();
                int resID = getResources().getIdentifier(currImg, "mipmap", getPackageName());
                currencyImage.setImageResource(resID);
                service.refreshCurrency(currencyString);

                // Troubleshooting resID assignment and imageView reassignment based on user choice on spinner
                //Toast.makeText(getBaseContext(), "Current resID: " + resID + " Current Img: " + currImg, Toast.LENGTH_SHORT).show();

                Toast.makeText(getBaseContext(), "You've selected " + currencyStringName, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void serviceSuccess(CurrentValue currentValue) {
        //Toast.makeText(getBaseContext(), currentValue.getValueUSD(), Toast.LENGTH_LONG).show();
        valueTextView.setText(currentValue.getValueUSD());
        marketCapTextView.setText(currentValue.getMarketCap());
        maxSupplyTextView.setText(currentValue.getMaxSupply());
        volumeTextView.setText(currentValue.getVolume());
        exchangeTextView.setText((currentValue.getExchange()));
        lastTradeView.setText(currentValue.getLastTradeId());


    }

    @Override
    public void serviceFailure(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();

    }
}
