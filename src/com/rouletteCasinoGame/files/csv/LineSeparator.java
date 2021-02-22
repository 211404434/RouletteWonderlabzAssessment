package com.rouletteCasinoGame.files.csv;

/**
 * Represents elements in a line of a CSV file in
 * an established order.
 */
enum LineSeparator {
    NAME(0), TOTAL_WINNINGS(1), TOTAL_BETINGS(2);

    private final int index;

    private LineSeparator(final int index) {
        this.index = index;
    }

    int index() {
        return index;
    }
}
