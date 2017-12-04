package com.floridapoly.alex.cryptocurrency.data;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Adam Seevers on 9/29/2017.
 * Cryptocurrency project - Mobile Dev
 */

public class CurrentValue implements JSONHandler {

    private String valueUSD;
    private String fromSymbol;
    private String marketCap;
    private String maxSupply;
    private String volume;
    private String lastTradeId;
    private String exchange;
    private int differenceColor;
    private String change;
    private String currency;
    private String supply;
    private String oneDChange;
    private String timeStamp;
    private String[] timeArray, valueArray;

    public String[] getTimeArray() {
        return timeArray;
    }
    public String[] getValueArray() {
        return valueArray;
    }
    public String getTimeStamp() {
        return timeStamp;
    }

    public String getSupply() { return supply; }

    public String getFromSymbol() {
        return fromSymbol;
    }

    public int getDifferenceColor() {
        return differenceColor;
    }

    public void setFromSymbol(String fromSymbol) {
        this.fromSymbol = fromSymbol;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }
    public String getChange() {
        //return Math.round(Float.parseFloat(change));
        return String.format("%.2f", Double.parseDouble(change)) + " %";
    }

    public void setChange(String change) {
        this.change = change;
    }
    public String getExchange() {
        return exchange;
    }

    public String getLastTradeId() {
        return lastTradeId;
    }

    public void setDifferenceColor() {
        if (Double.parseDouble(change) > 0) {
            this.differenceColor = 0xFF00FF00;
        } else {
            this.differenceColor = 0xFFFF0000;
        }

    }
    public String getValueUSD() {
        return valueUSD;
    }
    public String getMarketCap() {
        return marketCap;
    }

    public String getMaxSupply() {
        return maxSupply;
    }

    public String getVolume() {
        return volume;
    }

    public String getOneDayChange() {
        return String.format("%.2f", Double.parseDouble(oneDChange)) + " %";
    }

    @Override
    public void populate(JSONObject data)
    {
        oneDChange = data.optString("CHANGEPCTDAY");
        supply = data.optString("SUPPLY");
        currency = data.optString("FROMSYMBOL");
        change = data.optString("CHANGEPCT24HOUR");
        fromSymbol = data.optString("FROMSYMBOL");
        valueUSD = data.optString("PRICE");
        marketCap = data.optString("MKTCAP");
        maxSupply = data.optString("VOLUME24HOURTO");
        volume = data.optString("SUPPLY");
        lastTradeId = data.optString("LASTTRADEID");
        exchange = data.optString("LASTMARKET");
        timeStamp = data.optString("USD");
    }

    public void populate(String[] time, String[] value) {
        timeArray = time;
        valueArray = value;
    }
}