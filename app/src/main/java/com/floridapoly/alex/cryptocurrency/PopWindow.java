package com.floridapoly.alex.cryptocurrency;

import android.app.Activity;
import android.drm.DrmStore;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.floridapoly.alex.cryptocurrency.data.CurrentValue;
import com.floridapoly.alex.cryptocurrency.service.CryptoCompareService;
import com.floridapoly.alex.cryptocurrency.service.CryptoCompareServiceDisplay;
import com.floridapoly.alex.cryptocurrency.service.CryptocurrencyCallback;

/**
 * Created by Adam Seevers on 12/2/2017.
 * Cryptocurrency project - Mobile Dev
 */

public class PopWindow extends Activity implements CryptocurrencyCallback {

    private CryptoCompareServiceDisplay apiService;
    private TextView marketCap, popupTitle, circSupply, dayVolume, currencyPrice;
    private TextView percChange1d, percChange24h, percChange7d;
    private String currency;
    private ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);

        initializeVariables();

    }

    public void initializeVariables() {
        apiService = new CryptoCompareServiceDisplay(this);
        marketCap = findViewById(R.id.marketCapTV);
        popupTitle = findViewById(R.id.popupTitle);
        circSupply = findViewById(R.id.circulatingSupplyTV);
        dayVolume = findViewById(R.id.dayVolumeTV);
        currencyPrice = findViewById(R.id.popupPriceTV);
        percChange1d = findViewById(R.id.percChange1h);
        percChange24h = findViewById(R.id.percChange24h);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.9), (int)(height*0.9));

        currency = getIntent().getStringExtra("passedCurrency");

        apiService.refreshCurrency(currency);
    }

    public void serviceSuccess(CurrentValue currentValue) {
        popupTitle.setText(currency);
        currencyPrice.setText("Price : " + currentValue.getValueUSD());
        marketCap.setText("Market Cap : " + currentValue.getMarketCap());
        circSupply.setText("Circulating Supply : " + currentValue.getSupply());
        dayVolume.setText("24 Hour Volume : " + currentValue.getMaxSupply());
        percChange24h.setText("Percent Change(24h) : " + currentValue.getChange());
        percChange1d.setText("Percent Change(Day) : " + currentValue.getOneDayChange());



    }

    public void serviceFailure(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
