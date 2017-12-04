package com.floridapoly.alex.cryptocurrency;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.floridapoly.alex.cryptocurrency.data.CurrentValue;
import com.floridapoly.alex.cryptocurrency.service.CryptoCompareServiceDisplay;
import com.floridapoly.alex.cryptocurrency.service.CryptoTimestamp;
import com.floridapoly.alex.cryptocurrency.service.CryptoTimestampCallback;
import com.floridapoly.alex.cryptocurrency.service.CryptocurrencyCallback;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Date;

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
    private GraphView currencyGraph;
    private LineGraphSeries<DataPoint> dataSeries;
    private Double graphY;
    private Date graphX;
    private double[] graphYArray = new double[] { 0,0,0 };
    private arrayVariable aV = new arrayVariable();
    private Integer pointsToPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);
        initializeVariables();
    }

    public void initializeVariables() {
        apiService = new CryptoCompareServiceDisplay(this);
        timeStampService = new CryptoTimestamp(this);
        marketCap = findViewById(R.id.marketCapTV);
        popupTitle = findViewById(R.id.popupTitle);
        circSupply = findViewById(R.id.circulatingSupplyTV);
        dayVolume = findViewById(R.id.dayVolumeTV);
        currencyPrice = findViewById(R.id.popupPriceTV);
        percChange1d = findViewById(R.id.percChange1h);

        //grabbing currency from the clicked currency row to use for api query
        currency = getIntent().getStringExtra("passedCurrency");

        apiService.refreshCurrency(currency);

        //update the size of the window based on get device screen size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.9), (int)(height*0.9));

        //adjust api query to how many points to add to graph
        pointsToPlot = 32;
        //query API for historical data on each requested timeStamp
        timeStampService.refreshTimestamp(currency, pointsToPlot.toString());

        currencyGraph = (GraphView) findViewById(R.id.graphView);
        currencyGraph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        currencyGraph.getGridLabelRenderer().setHorizontalAxisTitle("");
    }

    //updates chart to display historical data for 5 days before current date
    public void setToFiveDays(View view) {
        pointsToPlot = 5;
        timeStampService.refreshTimestamp(currency, pointsToPlot.toString());
    }

    //updates chart to display historical data for a month before current date
    public void setToThirtyDays(View view) {
        pointsToPlot = 32;
        timeStampService.refreshTimestamp(currency, pointsToPlot.toString());
    }

    //updates chart to display historical data for a year before current date
    public void setToOneYear(View view) {
        pointsToPlot = 365;
        timeStampService.refreshTimestamp(currency, pointsToPlot.toString());
    }

    //when the time stamp service is successful
    public void timeStampServiceSuccess(CurrentValue currentValue) {
        aV.setVariable(false);

        String[] curVal = new String[currentValue.getTimeArray().length];
        String[] timeStamps = new String[currentValue.getTimeArray().length];

        curVal = currentValue.getValueArray();
        timeStamps = currentValue.getTimeArray();

        //pass JSON data into the graph to plot
        fillGraph(timeStamps, curVal);
    }

    //populate graph with data grabbed from API
    public void fillGraph(String[] timeStamps, String[] values) {
        currencyGraph.removeAllSeries();
        currencyGraph.getGridLabelRenderer().setNumHorizontalLabels(pointsToPlot);
        dataSeries = new LineGraphSeries<DataPoint>();
        for (int i = 0; i < timeStamps.length; i++) {
            graphY = Double.valueOf(values[i]);
            graphX = new Date(Long.valueOf(timeStamps[i])*1000);
            dataSeries.appendData(new DataPoint(graphX, graphY), true, pointsToPlot);
        }
        dataSeries.setDataPointsRadius(2);
        dataSeries.setThickness(2);
        dataSeries.setColor(Color.GREEN);
        dataSeries.setDrawDataPoints(true);
        currencyGraph.addSeries(dataSeries);
    }

    //catch exception if service fails
    public void timeStampServiceFailure(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }

    //grabs currency details information from api - fills information to the popupwindow
    public void serviceSuccess(CurrentValue currentValue) {
        popupTitle.setText(currency);
        currencyPrice.setText("Price : " + currentValue.getValueUSD());
        marketCap.setText("Market Cap : " + currentValue.getMarketCap());
        circSupply.setText("Circulating Supply : " + currentValue.getSupply());
        dayVolume.setText("24 Hour Volume : " + currentValue.getMaxSupply());
        percChange1d.setText("Percent Change(Day) : " + currentValue.getOneDayChange());
    }

    //catch exception if service fails
    public void serviceFailure(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
