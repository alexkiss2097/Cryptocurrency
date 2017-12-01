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
    private TextView valueEOSTextView, changeEOSTextView;
    private TextView valueSTRATTextView, changeSTRATTextView;
    private TextView valueUSDTTextView, changeUSDTTextView;
    private TextView valueZECTextView, changeZECTextView;
    private TextView valueICOTextView, changeICOTextView;
    private TextView valueNEOTextView, changeNEOTextView;
    private TextView valueBTSTextView, changeBTSTextView;

    private CryptoCompareService BTCservice, ETHservice, DSHservice, LTCservice, XMRservice, XEMservice, EOSservice, STRATservice, USDTservice, ZECservice, ICOservice, NEOservice, BTSservice;
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
        valueEOSTextView = findViewById(R.id.eosPrice);
        changeEOSTextView = findViewById(R.id.eos_Change);
        valueSTRATTextView = findViewById(R.id.stratPrice);
        changeSTRATTextView = findViewById(R.id.strat_Change);
        valueUSDTTextView = findViewById(R.id.usdtPrice);
        changeUSDTTextView = findViewById(R.id.usdt_Change);
        valueZECTextView = findViewById(R.id.zecPrice);
        changeZECTextView = findViewById(R.id.zec_Change);
        valueICOTextView = findViewById(R.id.icoPrice);
        changeICOTextView = findViewById(R.id.ico_Change);
        valueNEOTextView = findViewById(R.id.neoPrice);
        changeNEOTextView = findViewById(R.id.neo_Change);
        valueBTSTextView = findViewById(R.id.btsPrice);
        changeBTSTextView = findViewById(R.id.bts_Change);


        //services used for API - cryptocompare
        BTCservice = new CryptoCompareService(this);
        ETHservice = new CryptoCompareService(this);
        DSHservice = new CryptoCompareService(this);
        LTCservice = new CryptoCompareService(this);
        XMRservice = new CryptoCompareService(this);
        XEMservice = new CryptoCompareService(this);
        EOSservice = new CryptoCompareService(this);
        STRATservice = new CryptoCompareService(this);
        USDTservice = new CryptoCompareService(this);
        ZECservice = new CryptoCompareService(this);
        ICOservice = new CryptoCompareService(this);
        NEOservice = new CryptoCompareService(this);
        BTSservice = new CryptoCompareService(this);

        // Initial call to get API data for cryptocurrency using CryptoCompareService
        BTCservice.refreshCurrency("BTC");
        ETHservice.refreshCurrency("ETH");
        DSHservice.refreshCurrency("DSH");
        LTCservice.refreshCurrency("LTC");
        XMRservice.refreshCurrency("XMR");
        XEMservice.refreshCurrency("XEM");
        EOSservice.refreshCurrency("EOS");
        STRATservice.refreshCurrency("STRAT");
        USDTservice.refreshCurrency("USDT");
        ZECservice.refreshCurrency("ZEC");
        ICOservice.refreshCurrency("ICO");
        NEOservice.refreshCurrency("NEO");
        BTSservice.refreshCurrency("BTS");

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
            case ("EOS"):
                valueEOSTextView.setText(currentValue.getValueUSD());
                changeEOSTextView.setText(currentValue.getChange());
                break;
            case ("STRAT"):
                valueSTRATTextView.setText(currentValue.getValueUSD());
                changeSTRATTextView.setText(currentValue.getChange());
                break;
            case ("USDT"):
                valueUSDTTextView.setText(currentValue.getValueUSD());
                changeUSDTTextView.setText(currentValue.getChange());
                break;
            case ("ZEC"):
                valueZECTextView.setText(currentValue.getValueUSD());
                changeZECTextView.setText(currentValue.getChange());
                break;
            case ("ICO"):
                valueICOTextView.setText(currentValue.getValueUSD());
                changeICOTextView.setText(currentValue.getChange());
                break;
            case ("NEO"):
                valueNEOTextView.setText(currentValue.getValueUSD());
                changeNEOTextView.setText(currentValue.getChange());
                break;
            case ("BTS"):
                valueBTSTextView.setText(currentValue.getValueUSD());
                changeBTSTextView.setText(currentValue.getChange());
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

    public void refreshButton(View view) {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}
