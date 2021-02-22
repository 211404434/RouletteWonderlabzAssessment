package com.rouletteCasinoGame;


import com.rouletteCasinoGame.Helper.MessageShowToUser;

public class Player {

    private final String PlayerName;
    private double totalWinnings;
    private double totalBettings;

    public Player(final String PlayerName, final double totalWinnings, final double totalBettings) {
        this.PlayerName = PlayerName;
        this.totalWinnings = totalWinnings;
        this.totalBettings = totalBettings;
    }

    public Player(final String PlayerName) {
        this(PlayerName, 0.0, 0.0);
    }

    public String PlayerName() {
        return PlayerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return PlayerName.equals(player.PlayerName);
    }

    @Override
    public int hashCode() {
        return PlayerName.hashCode();
    }

    public double totalWinnings() {
        return totalWinnings;
    }

    public double totalBettings() {
        return totalBettings;
    }

    /**
     * This method applies the outcome of the (last) round
     * of game on the status of a player. If a player didn't
     * make bets, his status doesn't change.
     * @param playerResult An instance of a PlayerResult
     */
    public void apply(final PlayerResult playerResult) {
        final Bet bet = playerResult.bet();
        if(playerResult.win()) {
            totalWinnings += (bet.target().payoff() * bet.amount());
        }
        totalBettings += bet.amount();
    }

    public String toString() {
        return MessageShowToUser.gameTotalsRow(PlayerName, String.valueOf(totalWinnings), String.valueOf(totalBettings));
    }
}
