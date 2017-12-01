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
    private TextView lastTradeView;
    private TextView exchangeTextView;
    private TextView valueBTCTextView, changeBTCTextView;
    private TextView valueETHTextView, changeETHTextView;
    private TextView valueDSHTextView, changeDSHTextView;
    private TextView valueLTCTextView, changeLTCTextView;
    private TextView valueXMRTextView, changeXMRTextView;
    private TextView valueXEMTextView, changeXEMTextView;

    private CryptoCompareService BTCservice, ETHservice, DSHservice, LTCservice, XMRservice, XEMservice;
    //private ProgressBar dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        //widget reference in xml layout

        /**
        //instantiate variables used for API importing JSON files
        marketCapTextView = findViewById(R.id.valMarketCap);
        volumeTextView = findViewById(R.id.valVolume);
        maxSupplyTextView = findViewById(R.id.valMaxSupply);
        valueTextView = findViewById(R.id.valUSD);
        lastTradeView = findViewById(R.id.lastTradeIDValue);
        exchangeTextView = findViewById(R.id.exchangeValue);
         **/

        valueBTCTextView = findViewById(R.id.btcPrice);
        changeBTCTextView = findViewById(R.id.btc_Change);
        valueETHTextView = findViewById(R.id.ethPrice);
        changeETHTextView = findViewById(R.id.eth_Change);
        valueDSHTextView = findViewById(R.id.dshPrice);
        changeDSHTextView = findViewById(R.id.dsh_Change);
        valueLTCTextView = findViewById(R.id.ltcPrice);
        changeLTCTextView = findViewById(R.id.ltc_Change);
        valueXMRTextView = findViewById(R.id.xmrPrice);
        changeXMRTextView = findViewById(R.id.xmr_Change);
        valueXEMTextView = findViewById(R.id.xemPrice);
        changeXEMTextView = findViewById(R.id.xem_Change);


        //services used for API - cryptocompare
        BTCservice = new CryptoCompareService(this);
        ETHservice = new CryptoCompareService(this);
        DSHservice = new CryptoCompareService(this);
        LTCservice = new CryptoCompareService(this);
        XMRservice = new CryptoCompareService(this);
        XEMservice = new CryptoCompareService(this);

        // Initial call to get API data for cryptocurrency using CryptoCompareService
        BTCservice.refreshCurrency("BTC");
        ETHservice.refreshCurrency("ETH");
        DSHservice.refreshCurrency("DSH");
        LTCservice.refreshCurrency("LTC");
        XMRservice.refreshCurrency("XMR");
        XEMservice.refreshCurrency("XEM");

        // Changes IMG based on spinner selection
        // TODO: used to run API query based on selection later
        cryptoCurrency = getResources().getStringArray(R.array.cryptoSelect);
        cryptoCurrencyName = getResources().getStringArray(R.array.cryptoSelectName);

        /**
         * final Spinner cryptoSpinner = findViewById(R.id.cryptoSelect);

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
        }); **/
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
        switch (currentValue.getFromSymbol()) {
            case ("Ƀ"):
                valueBTCTextView.setText(currentValue.getValueUSD());
                changeBTCTextView.setText(currentValue.getChange());
                break;
            case ("Ξ"):
                valueETHTextView.setText(currentValue.getValueUSD());
                changeETHTextView.setText(currentValue.getChange());
                break;
            case ("DSH"):
                valueDSHTextView.setText(currentValue.getValueUSD());
                changeDSHTextView.setText(currentValue.getChange());
                break;
            case ("Ł"):
                valueLTCTextView.setText(currentValue.getValueUSD());
                changeLTCTextView.setText(currentValue.getChange());
                break;
            case ("XMR"):
                valueXMRTextView.setText(currentValue.getValueUSD());
                changeXMRTextView.setText(currentValue.getChange());
                break;
            case ("XEM"):
                valueXEMTextView.setText(currentValue.getValueUSD());
                changeXEMTextView.setText(currentValue.getChange());
                break;
        }


        //marketCapTextView.setText(currentValue.getMarketCap());
        //maxSupplyTextView.setText(currentValue.getMaxSupply());
        //volumeTextView.setText(currentValue.getVolume());
        //exchangeTextView.setText((currentValue.getExchange()));
        //lastTradeView.setText(currentValue.getLastTradeId());


    }

    @Override
    public void serviceFailure(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();

    }
}
