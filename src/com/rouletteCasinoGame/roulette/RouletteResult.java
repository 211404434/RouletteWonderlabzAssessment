package com.rouletteCasinoGame.roulette;


import com.rouletteCasinoGame.BetTarget;
import com.rouletteCasinoGame.GameResult;

import static com.rouletteCasinoGame.roulette.EvenAndOdd.Type.EVEN;
import static com.rouletteCasinoGame.roulette.EvenAndOdd.Type.ODD;

/**
 * A result of one game round (a spin for Roulette)
 */
class RouletteResult implements GameResult {

    private final TargetedNumber TargetedNumber;
    private final EvenAndOdd EvenAndOdd;

    RouletteResult(final int number) {
        this.TargetedNumber = new TargetedNumber(number);
        this.EvenAndOdd = evenOddBy(number);
    }

    private EvenAndOdd evenOddBy(final int number) {
        if (number == 0) {
            return new None(null);
        }
        if (number % 2 == 0) {
            return new EvenAndOdd(EVEN);
        }
        return new EvenAndOdd(ODD);
    }

    @Override
    public BetTarget[] allWinningTargets() {
        return new BetTarget[] {TargetedNumber, EvenAndOdd};
    }

    /**
     * A helper class used to cover even/odd response to zero.
     */
    class None extends EvenAndOdd {

        private None(Type type) {
            super(type);
        }

        @Override
        public double payoff() {
            return 0.0;
        }

        @Override
        public String valueToString() {
            return "";
        }

    }
}