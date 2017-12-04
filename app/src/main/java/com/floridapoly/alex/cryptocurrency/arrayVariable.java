package com.floridapoly.alex.cryptocurrency;

/**
 * Created by Adam Seevers on 12/3/2017.
 * Cryptocurrency project - Mobile Dev
 */

public class arrayVariable {
    private boolean arrayVar = false;
    private ChangeListener listener;

    public boolean isArrayVariable() {
        return arrayVar;
    }

    public void setVariable(boolean arrayVariable) {
        this.arrayVar = arrayVar;
        if (listener != null) listener.onChange();
    }

    public ChangeListener getListener() {
        return listener;
    }

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onChange();
    }
}