package com.rouletteCasinoGame;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Constants used to retrieve Sting values in the application,
 * stored in properties files for possibility to
 * internationalize easily.
 */
public enum RouletteGameMessages {
    BET,
    WINNING_NUMBER,
    WIN,
    LOSE,
    PLAYER,
    OUTCOME,
    WINNINGS,
    TOTAL_WINNINGS,
    TOTAL_BETINGS;


    private static final Locale locale = new Locale("en", "GB");
    private static final String MESSAGES_BUNDLE = "i18n.RouletteGameMessages";
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE, locale);

    public static String getString(final String key) {
        try {
        return BUNDLE.getString(key);
        } catch (MissingResourceException ex) {
            return key;
        }
    }

    public static String getString(final String key, final Object... params) {
        try {
            return MessageFormat.format(BUNDLE.getString(key), params);
        } catch (MissingResourceException ex) {
            return key;
        }
    }
}
