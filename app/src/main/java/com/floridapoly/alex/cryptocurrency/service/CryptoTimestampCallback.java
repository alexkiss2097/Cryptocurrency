package com.floridapoly.alex.cryptocurrency.service;

import com.floridapoly.alex.cryptocurrency.data.CurrentValue;

/**
 * Created by Adam Seevers on 9/29/2017.
 * Cryptocurrency project - Mobile Dev
 */

public interface CryptoTimestampCallback {
    void timeStampServiceSuccess(CurrentValue currentValue);
    void timeStampServiceFailure(Exception exception);

}
