package com.floridapoly.alex.cryptocurrency;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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

import java.text.SimpleDateFormat;
import java.util.Date;

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
    Long sysTime = System.currentTimeMillis()/1000;
    Date timeStamp = new Date(sysTime*1000L);
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss z");
    String formattedTimeStamp = sdf.format(timeStamp);

    private CryptoCompareService BTCservice, ETHservice,
            DSHservice, LTCservice, XMRservice, XEMservice,
            EOSservice, STRATservice, USDTservice, ZECservice,
            ICOservice, NEOservice, BTSservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        assignVariables();
        refreshCurrency();
        // Initial call to get API data for cryptocurrency using CryptoCompareService
        // Changes IMG based on spinner selection
        cryptoCurrency = getResources().getStringArray(R.array.cryptoSelect);
        cryptoCurrencyName = getResources().getStringArray(R.array.cryptoSelectName);

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

    public void assignVariables() {
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
    }

    public void refreshCurrency() {
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
    }

    @Override
    public void serviceSuccess(CurrentValue currentValue) {
        currentValue.setDifferenceColor();
        //Toast.makeText(getBaseContext(), currentValue.getValueUSD(), Toast.LENGTH_LONG).show();
        int valueColor = currentValue.getDifferenceColor();
        switch (currentValue.getFromSymbol()) {
            case ("BTC"):
                valueBTCTextView.setText(currentValue.getValueUSD());
                changeBTCTextView.setText(currentValue.getChange());
                changeBTCTextView.setTextColor(valueColor);
                break;
            case ("ETH"):
                valueETHTextView.setText(currentValue.getValueUSD());
                changeETHTextView.setText(currentValue.getChange());
                changeETHTextView.setTextColor(valueColor);
                break;
            case ("DSH"):
                valueDSHTextView.setText(currentValue.getValueUSD());
                changeDSHTextView.setText(currentValue.getChange());
                changeDSHTextView.setTextColor(valueColor);
                break;
            case ("LTC"):
                valueLTCTextView.setText(currentValue.getValueUSD());
                changeLTCTextView.setText(currentValue.getChange());
                changeLTCTextView.setTextColor(valueColor);
                break;
            case ("XMR"):
                valueXMRTextView.setText(currentValue.getValueUSD());
                changeXMRTextView.setText(currentValue.getChange());
                changeXMRTextView.setTextColor(valueColor);
                break;
            case ("XEM"):
                valueXEMTextView.setText(currentValue.getValueUSD());
                changeXEMTextView.setText(currentValue.getChange());
                changeXEMTextView.setTextColor(valueColor);
                break;
            case ("EOS"):
                valueEOSTextView.setText(currentValue.getValueUSD());
                changeEOSTextView.setText(currentValue.getChange());
                changeEOSTextView.setTextColor(valueColor);
                break;
            case ("STRAT"):
                valueSTRATTextView.setText(currentValue.getValueUSD());
                changeSTRATTextView.setText(currentValue.getChange());
                changeSTRATTextView.setTextColor(valueColor);
                break;
            case ("USDT"):
                valueUSDTTextView.setText(currentValue.getValueUSD());
                changeUSDTTextView.setText(currentValue.getChange());
                changeUSDTTextView.setTextColor(valueColor);
                break;
            case ("ZEC"):
                valueZECTextView.setText(currentValue.getValueUSD());
                changeZECTextView.setText(currentValue.getChange());
                changeZECTextView.setTextColor(valueColor);
                break;
            case ("ICO"):
                valueICOTextView.setText(currentValue.getValueUSD());
                changeICOTextView.setText(currentValue.getChange());
                changeICOTextView.setTextColor(valueColor);
                break;
            case ("NEO"):
                valueNEOTextView.setText(currentValue.getValueUSD());
                changeNEOTextView.setText(currentValue.getChange());
                changeNEOTextView.setTextColor(valueColor);
                break;
            case ("BTS"):
                valueBTSTextView.setText(currentValue.getValueUSD());
                changeBTSTextView.setText(currentValue.getChange());
                changeBTSTextView.setTextColor(valueColor);
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
        refreshCurrency();
        Toast refreshedMsg = new Toast(this);
        refreshedMsg.makeText(MainActivity.this, "Information updated at \n" + formattedTimeStamp, Toast.LENGTH_LONG).show();
    }
}
