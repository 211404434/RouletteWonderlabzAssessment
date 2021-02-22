package com.rouletteCasinoGame;


import com.rouletteCasinoGame.exceptions.GameException;
import com.rouletteCasinoGame.persistence.FilesRepository;
import com.rouletteCasinoGame.Helper.MessageShowToUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Generic class that implements the common operations in/on a game
 * and defines several abstract classes, that should be implemented
 * by each specific game
 */
public abstract class Game {

    protected final FilesRepository filesRepository;
    protected final BetTargetFactory betTargetFactory;
    protected final List<GameResultsListener> listeners = new ArrayList<GameResultsListener>();
    protected final Collection<Player> players;
    protected final BetManager betManager= new BetManager();
    protected final Collection<PlayerResult> lastPlayerResults = new ArrayList<PlayerResult>();
    protected GameResult lastResult;

    protected Game(final FilesRepository filesRepository, final BetTargetFactory betTargetFactory) throws GameException {
        this.filesRepository = filesRepository;
        this.betTargetFactory = betTargetFactory;
        players = filesRepository.getPlayers();
    }

    /**
     * @return The Collection of the players registered in
     * the game from the initial file.
     */
    public Collection<Player> players() {
        return players;
    }

    public boolean placeBet(final Bet bet) {
        return betManager.placeBet(bet);
    }

    /**
     * All the bets registered in the current round of the game.
     * If called when the game is being played, the result may
     * vary depending of the moment, as the bet are being accepted.
     * @return The collection of the bets registered at the moment of call.
     */
    public Collection<Bet> currentBets() {
        return betManager.currentBets();
    }

    public Player playerByName(final String name) {
        for (Player player : players) {
            if(player.PlayerName().equals(name)){
                return player;
            }
        }
        return null;
    }

    /**
     * This method should be used to get the specific
     * BetFactory of the Game.
     * @param game The game whose BetFactory is required
     * @return A specific BetFactory
     */
    public static BetFactory aBetFor(final Game game) {
        return game.betFactory();
    }

    protected void acceptBets(final boolean accept) {
        betManager.acceptBets(accept);
    }

    public void addListener(final GameResultsListener listener) {
        listeners.add(listener);
    }

    /**
     * @return The last result of the Game (eg. the winning number)
     * in order to be available to any component when executing
     * operations that might take some time due to workload or
     * connection/access problems.
     */
    public GameResult lastResult() {
        return lastResult;
    }

    /**
     *
     * @return The current players' position in the game, i.e. the
     * totals for each player registered in the game
     */
    public String playersTotalsToString() {
        final StringBuilder totals = new StringBuilder();
        totals.append(MessageShowToUser.defaultGameTotalsHeader());

        for (Player player : players) {
            totals.append(player.toString());
        }

        return "\n" + totals.toString() + "\n";
    }

    protected abstract BetFactory betFactory();
    public abstract void start();
    public abstract void stop();
    public abstract String gameResultToString(GameResult result);
}
