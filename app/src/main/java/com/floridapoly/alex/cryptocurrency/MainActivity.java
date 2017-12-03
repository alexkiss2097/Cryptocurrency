package com.floridapoly.alex.cryptocurrency;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
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

    //initialize variables used to pass data from api to layout
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

    //calls api for each cryptocurrency
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

    //Get current server time for refresh data
    public String getServerTime() {
        Long sysTime = System.currentTimeMillis()/1000;
        Date timeStamp = new Date(sysTime*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss z");
        String formattedTimeStamp = sdf.format(timeStamp);
        return formattedTimeStamp;
    }
    @Override
    public void serviceSuccess(CurrentValue currentValue) {
        currentValue.setDifferenceColor();
        //Toast.makeText(getBaseContext(), currentValue.getValueUSD(), Toast.LENGTH_LONG).show();
        int valueColor = currentValue.getDifferenceColor();
        String usdValue = "$" + currentValue.getValueUSD();
        String usdChange = currentValue.getChange();
        switch (currentValue.getFromSymbol()) {
            case ("BTC"):
                valueBTCTextView.setText(usdValue);
                changeBTCTextView.setText(usdChange);
                changeBTCTextView.setTextColor(valueColor);
                break;
            case ("ETH"):
                valueETHTextView.setText(usdValue);
                changeETHTextView.setText(usdChange);
                changeETHTextView.setTextColor(valueColor);
                break;
            case ("DSH"):
                valueDSHTextView.setText(usdValue);
                changeDSHTextView.setText(usdChange);
                changeDSHTextView.setTextColor(valueColor);
                break;
            case ("LTC"):
                valueLTCTextView.setText(usdValue);
                changeLTCTextView.setText(usdChange);
                changeLTCTextView.setTextColor(valueColor);
                break;
            case ("XMR"):
                valueXMRTextView.setText(usdValue);
                changeXMRTextView.setText(usdChange);
                changeXMRTextView.setTextColor(valueColor);
                break;
            case ("XEM"):
                valueXEMTextView.setText(usdValue);
                changeXEMTextView.setText(usdChange);
                changeXEMTextView.setTextColor(valueColor);
                break;
            case ("EOS"):
                valueEOSTextView.setText(usdValue);
                changeEOSTextView.setText(usdChange);
                changeEOSTextView.setTextColor(valueColor);
                break;
            case ("STRAT"):
                valueSTRATTextView.setText(usdValue);
                changeSTRATTextView.setText(usdChange);
                changeSTRATTextView.setTextColor(valueColor);
                break;
            case ("USDT"):
                valueUSDTTextView.setText(usdValue);
                changeUSDTTextView.setText(usdChange);
                changeUSDTTextView.setTextColor(valueColor);
                break;
            case ("ZEC"):
                valueZECTextView.setText(usdValue);
                changeZECTextView.setText(usdChange);
                changeZECTextView.setTextColor(valueColor);
                break;
            case ("ICO"):
                valueICOTextView.setText(usdValue);
                changeICOTextView.setText(usdChange);
                changeICOTextView.setTextColor(valueColor);
                break;
            case ("NEO"):
                valueNEOTextView.setText(usdValue);
                changeNEOTextView.setText(usdChange);
                changeNEOTextView.setTextColor(valueColor);
                break;
            case ("BTS"):
                valueBTSTextView.setText(usdValue);
                changeBTSTextView.setText(usdChange);
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

    public void popupWindow(String currency) {
        Intent intent = new Intent(MainActivity.this, PopWindow.class);
        intent.putExtra("passedCurrency", currency);
        startActivity(intent);
    }

    public void refreshButton(View view) {
        refreshCurrency();
        Toast refreshedMsg = new Toast(this);
        String timeStamp = getServerTime();
        refreshedMsg.makeText(MainActivity.this, "Information updated at \n" + timeStamp, Toast.LENGTH_LONG).show();
    }

    public void detailedPopup(View view) {
        switch(view.getId())
        {
            case R.id.btcRow:
                popupWindow("BTC");
                break;
            case R.id.ethRow:
                popupWindow( "ETH");
                break;
            case R.id.dshRow:
                popupWindow( "DSH");
                break;
            case R.id.btsRow:
                popupWindow("BTS");
                break;
            case R.id.eosRow:
                popupWindow("EOS");
                break;
            case R.id.ltcRow:
                popupWindow("LTC");
                break;
            case R.id.icoRow:
                popupWindow("ICO");
                break;
            case R.id.neoRow:
                popupWindow("NEO");
                break;
            case R.id.stratRow:
                popupWindow("STRAT");
                break;
            case R.id.usdtRow:
                popupWindow("USDT");
                break;
            case R.id.xemRow:
                popupWindow("XEM");
                break;
            case R.id.xmrRow:
                popupWindow("XMR");
                break;
            case R.id.zecRow:
                popupWindow("ZEC");
                break;
        }
    }
}
