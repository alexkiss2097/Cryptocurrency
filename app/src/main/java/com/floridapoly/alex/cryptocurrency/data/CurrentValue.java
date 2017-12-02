package com.floridapoly.alex.cryptocurrency.data;

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

    public String getFromSymbol() {
        return fromSymbol;
    }

    public int getDifferenceColor() {
        return differenceColor;
    }

    public void setFromSymbol(String fromSymbol) {
        this.fromSymbol = fromSymbol;
    }

    public String getChange() {
        //return Math.round(Float.parseFloat(change));
        return String.format("$%.2f", Double.parseDouble(change));
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

    @Override
    public void populate(JSONObject data)
    {
        change = data.optString("CHANGE24HOUR");
        fromSymbol = data.optString("FROMSYMBOL");
        valueUSD = data.optString("PRICE");
        marketCap = data.optString("MKTCAP");
        maxSupply = data.optString("VOLUME24HOURTO");
        volume = data.optString("SUPPLY");
        lastTradeId = data.optString("LASTTRADEID");
        exchange = data.optString("LASTMARKET");
    }
}