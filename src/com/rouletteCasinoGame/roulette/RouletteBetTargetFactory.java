package com.rouletteCasinoGame.roulette;

import com.rouletteCasinoGame.BetTarget;
import com.rouletteCasinoGame.BetTargetFactory;

class RouletteBetTargetFactory implements BetTargetFactory {

    @Override
    public BetTarget createFrom(final String string) {
        if (isNumberOfValidRange(string)) {
            return new TargetedNumber(Integer.valueOf(string));
        }

        if (isEvenOddValidLabel(string)) {
            return new EvenAndOdd(EvenAndOdd.Type.byName(string));
        }

        return null;
    }

    private boolean isEvenOddValidLabel(final String string) {
        final EvenAndOdd.Type tryTarget = EvenAndOdd.Type.byName(string);
        return tryTarget != null;
    }

    private boolean isNumberOfValidRange(final String string) {
        try {

            final Integer tryNumber = Integer.valueOf(string);
            return (tryNumber > Roulette.ROULETTE_MIN) && (tryNumber <= Roulette.ROULETTE_MAX);

        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
