package com.rouletteCasinoGame.game.roulette;


import com.rouletteCasinoGame.*;
import com.rouletteCasinoGame.exceptions.GameException;
import com.rouletteCasinoGame.files.csv.CSVFileFilesRepository;
import com.rouletteCasinoGame.roulette.Roulette;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static com.rouletteCasinoGame.Game.aBetFor;
import static com.sun.org.apache.xerces.internal.util.PropertyState.is;

public class RouletteTest {

    @Before
    public void clean() {
        try {
            new PrintWriter(TestHelper.dummyFile).close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void should_have_a_correct_list_of_players_after_start() throws GameException, IOException {
        final String[] playersStrings = {"Tiki_Monkey,10.0,6.0", "Barbara,5.5,0.0", "Player,12.0,35.0"};
        final File initFile = TestHelper.initFileFromContent(playersStrings);
        final Game game = createRouletteFrom(initFile);
        game.start();

        assertThat(game.players().size(), is(playersStrings.length));
        for (String entry : playersStrings) {
            String[] playerData = entry.split(",");
            final Player player = game.playerByName(playerData[0]);
            assertThat(player, is(notNullValue()));
            assertThat(player.totalWinnings(), is(Double.valueOf(playerData[1])));
            assertThat(player.totalBettings(), is(Double.valueOf(playerData[2])));
        }
    }

    @Test
    public void should_register_a_bet_when_game_is_being_played() throws GameException, IOException, InterruptedException {
        final String testPlayer = "Tiki_Monkey";
        final String[] playersStrings = {testPlayer + ",10.0,6.0", "Barbara,5.5,0.0", "Player,12.0,35.0"};
        final File initFile = TestHelper.initFileFromContent(playersStrings);
        final Roulette roulette = createRouletteFrom(initFile);
        startRouletteForTest(roulette, 1);

        final String betString = testPlayer + " EVEN  25.0";
        final Bet bet = aBetFor(roulette).fromInput(betString);
        final boolean betPlaced = roulette.placeBet(bet);

        assertTrue(betPlaced);
        Thread.sleep(3);//give some time to add the bet to the list, may vary depending on machine

        Collection<Bet> currentBets = roulette.currentBets();
        assertThat(currentBets, hasItem(bet));
    }

    @Test
    public void should_return_a_result_after_each_spin_interval() throws Exception {
        final int spinInterval = 1;
        final int repeats = 3;
        final int typesOfResult = 2;//game result and totals

        final String[] playersStrings = {"Tiki_Monkey,10.0,6.0", "Barbara,5.5,0.0", "Player,12.0,35.0"};
        final File initFile = TestHelper.initFileFromContent(playersStrings);
        final Roulette roulette = createRouletteFrom(initFile);

        TestListener listener = new TestListener();
        roulette.addListener(listener);

        final int initialResults = listener.results.size();

        startRouletteForTest(roulette, spinInterval);

        int delay = (spinInterval) * repeats;
        stopAfterDelay(roulette, delay);

        final int finalResults = listener.results.size();

        assertThat(initialResults, is(0));
        assertThat(finalResults, is(repeats * typesOfResult));
    }

    @Test
    public void should_save_players_totals_after_the_spin() throws Exception {
        final int spinInterval = 2;
        final String testPlayerName = "Tiki_Monkey";
        final double initTotalWin = 10.0;
        final double initTotalBet = 20.0;
        final double testBet = 30.0;
        final String testBetTarget = "EVEN";

        final String[] playersStrings = {testPlayerName + "," + initTotalWin + "," + initTotalBet};
        final File initFile = TestHelper.initFileFromContent(playersStrings);
        final Roulette roulette = createRouletteFrom(initFile);

        startRouletteForTest(roulette, spinInterval);
        final String betString = testPlayerName + " " + testBetTarget + "  " + testBet;
        final Bet bet = aBetFor(roulette).fromInput(betString);

        final boolean betPlaced = roulette.placeBet(bet);
        stopAfterDelay(roulette, spinInterval);

        assertTrue(betPlaced);

        final GameResult result = roulette.lastResult();
        final Player player = roulette.playerByName(testPlayerName);

        if(wins(testBetTarget, result)) {
            final double payoff = bet.amount() * bet.target().payoff();
            assertThat(player.totalWinnings(), is(initTotalWin + payoff));
            assertThat(player.totalBettings(), is(initTotalBet + bet.amount()));
        } else {
            assertThat(player.totalWinnings(), is(initTotalWin));
            assertThat(player.totalBettings(), is(initTotalBet + bet.amount()));
        }

    }

    private boolean wins(final String betTarget, final GameResult result) {
        for (BetTarget target : result.allWinningTargets()) {
            if (target.valueToString().equals(betTarget)) {
                return true;
            }
        }
        return false;
    }

    private Roulette createRouletteFrom(final File file) throws GameException {
        return new Roulette(new CSVFileFilesRepository(file));
    }


    private void startRouletteForTest(final Roulette roulette, final int spinInterval) throws InterruptedException {
        roulette.spinInterval(spinInterval);
        roulette.start();
        Thread.sleep(40);//give some time to start the spinner
    }

    private void stopAfterDelay(final Roulette roulette, final int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
        roulette.stop();
    }

    private class TestListener implements GameResultsListener {

        final List<String> results = new ArrayList<String>();

        @Override
        public void update(final String content) {
                results.add(content);
        }

    }
}
