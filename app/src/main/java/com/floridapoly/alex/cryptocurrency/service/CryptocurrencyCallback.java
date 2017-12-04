package com.floridapoly.alex.cryptocurrency.service;

import com.floridapoly.alex.cryptocurrency.data.CurrentValue;

/**
 * Created by Adam Seevers on 9/29/2017.
 * Cryptocurrency project - Mobile Dev
 */

public interface CryptocurrencyCallback {
    //passes the information retrieved and parsed by the services into the currentValue class
    void serviceSuccess(CurrentValue currentValue);
    void serviceFailure(Exception exception);

}
