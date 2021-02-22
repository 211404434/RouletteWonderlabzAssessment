package com.rouletteCasinoGame.Helper;

import static com.rouletteCasinoGame.RouletteGameMessages.*;



/**
 * A helper class that helps create messages shown to the user.
 */
public class MessageShowToUser {


    public static final int[] HEADER_COLUMNS_WIDTH = {20, 10, 10, 10};

    public static String normalizeToLength(final String input, final int fullLength) {
        final int inputLength = input.trim().length();

        if (inputLength > fullLength) {
            return input.substring(0, fullLength);
        }

        if (inputLength == fullLength) {
            return input;
        }

        String norm = input;
        for (int i = 0; i < (fullLength - inputLength); i++) {
            norm += " ";
        }

        return norm;
    }

    public static String defaultGameResultHeader() {
        return gameResultRow(getString(PLAYER.name()), getString(BET.name()),
                             getString(OUTCOME.name()), getString(WINNINGS.name()))
                          + ("\n---");
    }

    public static String gameResultRow(final String playerName, final String bet,
                                                 final String outcome, final String winnings) {
        String separator = " ";

        StringBuilder row = new StringBuilder("\n");
        row.append(normalizeToLength(playerName,HEADER_COLUMNS_WIDTH[0])).append(separator)
           .append(normalizeToLength(bet, HEADER_COLUMNS_WIDTH[1])).append(separator)
           .append(normalizeToLength(outcome, HEADER_COLUMNS_WIDTH[2])).append(separator)
           .append(normalizeToLength(winnings, HEADER_COLUMNS_WIDTH[3])).append(separator);

        return row.toString();
    }

    public static String defaultGameTotalsHeader() {
        return gameTotalsRow(getString(PLAYER.name()),
                getString(TOTAL_WINNINGS.name()),
                getString(TOTAL_BETINGS.name()))
                + ("\n---");
    }

    public static String gameTotalsRow(final String playerName, final String totalWin, final String totalBet) {
        String separator = " ";

        StringBuilder row = new StringBuilder("\n");
        row.append(normalizeToLength(playerName, HEADER_COLUMNS_WIDTH[0])).append(separator)
                .append(normalizeToLength(totalWin, HEADER_COLUMNS_WIDTH[1])).append(separator)
                .append(normalizeToLength(totalBet, HEADER_COLUMNS_WIDTH[2])).append(separator);

        return row.toString();
    }
}
