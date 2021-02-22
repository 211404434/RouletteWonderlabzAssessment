package com.rouletteCasinoGame.files.csv;



import com.rouletteCasinoGame.Player;
import com.rouletteCasinoGame.exceptions.GameException;
import com.rouletteCasinoGame.exceptions.GameIOException;
import com.rouletteCasinoGame.exceptions.MissingPlayersListException;
import com.rouletteCasinoGame.persistence.FilesRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class that facilitates the operations to retrieve data from and
 * store data to CSV files.
 */
public class CSVFileFilesRepository implements FilesRepository {

    private final File csvFile;

    public CSVFileFilesRepository(final File csvFile) {
        this.csvFile = csvFile;
    }

    @Override
    public Collection<Player> getPlayers() throws GameException {
        return extractPlayers(readFrom(csvFile));
    }

    @Override
    public void save(Collection<Player> players) {
        new Thread(new StorageWorker(players)).start();
    }

    private Collection<Player> extractPlayers(final Collection<String> lines) throws MissingPlayersListException {
        List<Player> players = new ArrayList<Player>();

        for(String line : lines) {
            Player player = playerFromLine(line);
            if (player != null) {
                players.add(player);
            }
        }

        if (players.isEmpty()) {
            throw new MissingPlayersListException("Players were not found for the game.");
        }

        return players;
    }

    private Player playerFromLine(final String line) {
        String[] LineSeparators = line.split(",");
        if (LineSeparators.length == 0) {
            return null;
        }
        if (LineSeparators.length == LineSeparator.values().length) {
            final String name = LineSeparators[LineSeparator.NAME.index()];
            final String totalWin = LineSeparators[LineSeparator.TOTAL_WINNINGS.index()];
            final String totalBet = LineSeparators[LineSeparator.TOTAL_BETINGS.index()];
            return new Player(name, validate(totalWin), validate(totalBet));
        } else {
            return new Player(LineSeparators[LineSeparator.NAME.index()]);
        }
    }

    private double validate(final String string) {
        try {
            return Double.valueOf(string);
        } catch (NumberFormatException ex) {
            return 0.0;
        }
    }

    private Collection<String> readFrom(final File file) throws GameIOException {
        final List<String> lines = new ArrayList<String>();

        try {

            final BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while((line = reader.readLine()) != null) {
                lines.add(line);
            }

            reader.close();

        } catch (IOException e) {
            throw new GameIOException(e.getMessage(), e.getCause());
        }

        return lines;
    }

    private class StorageWorker implements Runnable {


        private final Collection<Player> players;

        public StorageWorker(final Collection<Player> players) {
            this.players = players;
        }

        @Override
        public void run() {
            try {
                String coma = ",";
                final StringBuilder content = new StringBuilder();
                for(Player player : players) {
                    content.append(player.PlayerName()).append(coma)
                            .append(player.totalWinnings()).append(coma)
                            .append(player.totalBettings())
                            .append("\n");
                }
                final BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
                writer.write(content.toString());
                writer.close();
            } catch (IOException e) {
                //TODO depends of the project requirements,
                // normally would be logged and some action would be taken: retries, notifications, etc.
            }

        }
    }
}
