package com.rouletteCasinoGame;


import com.rouletteCasinoGame.Helper.MessageShowToUser;
import static com.rouletteCasinoGame.RouletteGameMessages.*;


/**
 * PlayerResult holds the data for a player's bet
 * and the winning BetTargets.
 */
public class PlayerResult {
    public static final double NO_PAY = 0.0;
    private final Bet bet;
    private final BetTarget[] winningTargets;

    public PlayerResult(final Bet bet, final BetTarget[] winningTargets) {
        this.bet = bet;
        this.winningTargets = winningTargets;
    }

    Bet bet() {
        return bet;
    }

    /**
     * This method creates a String which contains the details
     * about the outcome of the bet made.
     * @return The String with created content
     */
    public String toString() {
        if (win()) {
            return MessageShowToUser.gameResultRow(bet.player().name(),
                    bet.target().valueToString(),
                    getString(WIN.name()),
                    payoff());
        } else {
            return MessageShowToUser.gameResultRow(bet.player().name(),
                    bet.target().valueToString(),
                    getString(LOSE.name()),
                    String.valueOf(NO_PAY));
        }
    }

    boolean win() {
        for(BetTarget betTarget : winningTargets){
            if(betTarget.equals(bet.target())) {
                return true;
            }
        }
        return false;
    }

    String payoff() {
        return String.valueOf(bet.amount() * bet.target().payoff());
    }
}
