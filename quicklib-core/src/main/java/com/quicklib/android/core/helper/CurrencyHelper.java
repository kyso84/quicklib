package com.quicklib.android.core.helper;


import android.os.Build;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This helper provides convenient methods related to money & currency
 *
 * @author Benoit Deschanel
 * @package com.quicklib.android.core.helper
 * @since 17 -05-02
 */
public class CurrencyHelper {


    /**
     * Gets system's currency list.
     *
     * @return the currency list
     */
    public static List<Currency> getCurrencyList() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new ArrayList<>(Currency.getAvailableCurrencies());
        } else {
            Currency currency;
            Map<String, Currency> currencyList = new HashMap<>();
            for (Locale locale : Locale.getAvailableLocales()) {
                try {
                    currency = Currency.getInstance(locale);
                    if (!currencyList.containsKey(currency.getCurrencyCode())) {
                        currencyList.put(currency.getCurrencyCode(), currency);
                    }
                } catch (IllegalArgumentException e) {
                }
            }
            return new ArrayList<>(currencyList.values());
        }
    }

    /**
     * Gets the system's currency codes.
     *
     * @return the currency codes
     */
    public static List<String> getCurrencyCodes() {
        List<String> list = new ArrayList<>();
        for (Currency currency : getCurrencyList()) {
            list.add(currency.getCurrencyCode());
        }
        return list;
    }

    /**
     * Gets the system's currency names.
     *
     * @return the currency names
     */
    public static List<String> getCurrencyNames() {
        List<String> list = new ArrayList<>();
        for (Currency currency : getCurrencyList()) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                list.add(currency.getDisplayName(Locale.getDefault()));
            } else {
                list.add(currency.getSymbol(Locale.getDefault()) + " (" + currency.getCurrencyCode() + ")");
            }
        }
        return list;
    }


}
