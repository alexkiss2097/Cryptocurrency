package com.floridapoly.alex.cryptocurrency;

import android.app.Activity;
import android.drm.DrmStore;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.Toast;

import com.floridapoly.alex.cryptocurrency.data.CurrentValue;
import com.floridapoly.alex.cryptocurrency.service.CryptoCompareService;
import com.floridapoly.alex.cryptocurrency.service.CryptocurrencyCallback;

/**
 * Created by Adam Seevers on 12/2/2017.
 * Cryptocurrency project - Mobile Dev
 */

public class PopWindow extends Activity implements CryptocurrencyCallback {

    private CryptoCompareService apiService;
    private TextView marketCap;
    private String currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);

        apiService = new CryptoCompareService(this);
        marketCap = findViewById(R.id.marketCapTV);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.8));

        currency = getIntent().getStringExtra("passedCurrency");

        apiService.refreshCurrency(currency);

    }

    public void serviceSuccess(CurrentValue currentValue) {
        marketCap.setText(currentValue.getMarketCap());

    }

    public void serviceFailure(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
