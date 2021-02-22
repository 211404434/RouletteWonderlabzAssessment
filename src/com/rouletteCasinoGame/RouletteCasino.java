package com.rouletteCasinoGame;




import java.io.IOException;



public class RouletteCasino  {

    private static final String REQUEST_TO_EXIT = "exit";
    private static final String RETRY_OR_EXIT = "Please, insert a different path or exit " +
            "(typing '" + REQUEST_TO_EXIT + "'):";


    public static void main(String[] args) {
        try {
            System.out.println("Starting game...");
            RouletteCasino rouletteCasino = new RouletteCasino(args);
            rouletteCasino.start();
            rouletteCasino.stop();

        } catch (IOException e) {
            //TODO log
        }
    }

    public RouletteCasino(final String[] args) {
        System.out.println("testing game start function...");
    }

    private void start() throws IOException {

        System.out.println("testing game start function...");
    }

    private void stop() throws IOException {

        System.out.println("testing game stop function...");
    }
}

