package com.floridapoly.alex.cryptocurrency;

import android.app.Activity;
import android.drm.DrmStore;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.floridapoly.alex.cryptocurrency.data.CurrentValue;
import com.floridapoly.alex.cryptocurrency.service.CryptoCompareService;
import com.floridapoly.alex.cryptocurrency.service.CryptoCompareServiceDisplay;
import com.floridapoly.alex.cryptocurrency.service.CryptoTimestamp;
import com.floridapoly.alex.cryptocurrency.service.CryptoTimestampCallback;
import com.floridapoly.alex.cryptocurrency.service.CryptocurrencyCallback;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Adam Seevers 2017.
 * Cryptocurrency project - Mobile Dev
 */

public class PopWindow extends Activity implements CryptocurrencyCallback, CryptoTimestampCallback {

    private CryptoCompareServiceDisplay apiService;
    private CryptoTimestamp timeStampService;
    private TextView marketCap, popupTitle, circSupply, dayVolume, currencyPrice;
    private TextView percChange1d, percChange24h, percChange7d;
    private String currency;
    private int timeStampsAdded = 0;
    GraphView currencyGraph;
    LineGraphSeries<DataPoint> dataSeries;
    Double graphY;
    Date graphX;
    Long sysTime = (System.currentTimeMillis()/1000) - 1259200;
    Long sysTime1 = (System.currentTimeMillis()/1000) - 572800;
    Long sysTime2 = System.currentTimeMillis()/1000;
    Date[] graphXarray = new Date[] {
            new Date(sysTime*1000L), new Date(sysTime1*1000L), new Date(sysTime2*1000L)
    };
    String[] timeStamps = new String[] {
            String.valueOf(sysTime*1000L),
            String.valueOf(sysTime1*1000L),
            String.valueOf(sysTime2*1000L)
    };
    double[] graphYArray = new double[] { 0,0,0 };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);
        initializeVariables();
        //fillGraph();
    }

    public void initializeVariables() {
        apiService = new CryptoCompareServiceDisplay(this);
        timeStampService = new CryptoTimestamp(this);
        marketCap = findViewById(R.id.marketCapTV);
        popupTitle = findViewById(R.id.popupTitle);
        circSupply = findViewById(R.id.circulatingSupplyTV);
        dayVolume = findViewById(R.id.dayVolumeTV);
        currencyPrice = findViewById(R.id.popupPriceTV);
        percChange24h = findViewById(R.id.percChange24h);
        percChange1d = findViewById(R.id.percChange1h);

        currency = getIntent().getStringExtra("passedCurrency");

        apiService.refreshCurrency(currency);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.9), (int)(height*0.9));

        timeStampService.refreshTimestamp(currency, timeStamps[0]);
        timeStampService.refreshTimestamp(currency, timeStamps[1]);
        timeStampService.refreshTimestamp(currency, timeStamps[2]);

        currencyGraph = (GraphView) findViewById(R.id.graphView);
        currencyGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        currencyGraph.getGridLabelRenderer().setNumHorizontalLabels(3);

    }

    public void upDate(View view) {
        fillGraph();
    }

    public void timeStampServiceSuccess(CurrentValue currentValue) {
        graphYArray[timeStampsAdded] = Double.valueOf(currentValue.getTimeStamp());
        timeStampsAdded += 1;
        //Toast.makeText(this, String.valueOf(graphYArray[0] + " " + graphYArray[1] + " " + graphYArray[2]), Toast.LENGTH_LONG).show();
    }

    public void fillGraph() {
        currencyGraph.removeAllSeries();
        dataSeries = new LineGraphSeries<DataPoint>();
        for (int i = 0; i < 3; i++) {
            // values of the currency over the last 3 days
            graphY = graphYArray[i];
            graphX = graphXarray[i];

            //dates for the last 3 days based on time queried
            dataSeries.appendData(new DataPoint(graphX, graphY), true, 3);
        }
        dataSeries.setDataPointsRadius(10);
        dataSeries.setThickness(8);
        dataSeries.setColor(Color.GRAY);
        dataSeries.setDrawDataPoints(true);
        currencyGraph.addSeries(dataSeries);
    }

    public void timeStampServiceFailure(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }

    public void serviceSuccess(CurrentValue currentValue) {
        popupTitle.setText(currency);
        currencyPrice.setText("Price : " + currentValue.getValueUSD());
        marketCap.setText("Market Cap : " + currentValue.getMarketCap());
        circSupply.setText("Circulating Supply : " + currentValue.getSupply());
        dayVolume.setText("24 Hour Volume : " + currentValue.getMaxSupply());
        percChange24h.setText("Percent Change(24h) : " + currentValue.getChange());
        percChange1d.setText("Percent Change(Day) : " + currentValue.getOneDayChange());
        //percChange7d.setText("Percent Change (Week) : " + currentValue.getOneWeekChange());

    }

    public void serviceFailure(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
