package com.rouletteCasinoGame.roulette;


import com.rouletteCasinoGame.BetTarget;

class TargetedNumber implements BetTarget {

    public static final double PAYOFF = 36.0;
    private final int number;

    TargetedNumber(final int number) {
        this.number = number;
    }

    @Override
    public double payoff() {
        return PAYOFF;
    }

    @Override
    public String valueToString() {
        return String.valueOf(number);
    }

    @Override
    public boolean equals(final BetTarget other) {
        try {
            return this.number == Integer.valueOf(other.valueToString());
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
