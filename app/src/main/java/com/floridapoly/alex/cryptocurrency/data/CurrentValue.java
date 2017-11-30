package com.floridapoly.alex.cryptocurrency.data;

import org.json.JSONObject;

/**
 * Created by Adam Seevers on 9/29/2017.
 * Cryptocurrency project - Mobile Dev
 */

public class CurrentValue implements JSONHandler {

    private String valueUSD;
    private String marketCap;
    private String maxSupply;
    private String volume;
    private String lastTradeId;
    private String exchange;

    public String getExchange() {
        return exchange;
    }

    public String getLastTradeId() {
        return lastTradeId;
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
        valueUSD = data.optString("PRICE");
        marketCap = data.optString("MKTCAP");
        maxSupply = data.optString("VOLUME24HOURTO");
        volume = data.optString("SUPPLY");
        lastTradeId = data.optString("LASTTRADEID");
        exchange = data.optString("LASTMARKET");
    }
}