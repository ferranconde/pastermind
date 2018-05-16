package Domain.Drivers;


import Domain.Classes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DriverMakerGame implements IDomainDriver {

    private static void showFunctions() {
        System.out.println("1. PegCombination playTurn()");
        System.out.println("0. Exit driver");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("MakerGame class driver");
        System.out.println("");

        System.out.println("Let's create a new game:");

        // Player
        System.out.println("Input the player's name:");
        String name = scanner.nextLine();
        System.out.println("Input the player's username:");
        String user = scanner.nextLine();
        System.out.println("Input the player's password (only for testing purposes!):");
        String password = scanner.nextLine();
        Player player = new Player(user, name, password);

        // numTurns holes colors timelimit colorlist
        // logica sera FiveGuess

        System.out.println("Input the number of turns (higher than 0):");
        Integer numTurns = Integer.parseInt(scanner.nextLine());
        System.out.println("Input the number of holes (higher than 0):");
        Integer numHoles = Integer.parseInt(scanner.nextLine());
        System.out.println("Input the number of colors (higher than 0):");
        Integer numColors = Integer.parseInt(scanner.nextLine());
        System.out.println("Input the time limit (positive):");
        long timeLimit = Long.parseLong(scanner.nextLine());

        System.out.println("You have to make a selection of the colors used.");
        System.out.println("This is the list of available colors.");
        for (Peg.Color c : Peg.Color.values()) System.out.println(c.toString());
        System.out.println("Input the desired colors, one at a time, from the list above:");
        List<Peg.Color> colors = new ArrayList<>();
        for (int i = 0; i < numColors; i++)
            colors.add(Peg.Color.valueOf(scanner.nextLine()));

        Game.GameBuilder gameBuilder = new Game.GameBuilder();
        Game makerGame = gameBuilder.setNumTurns(numTurns)
                .setNumColors(numColors)
                .setNumHoles(numHoles)
                .setTimeLimit(timeLimit)
                .setColorList(colors)
                .setComputerStrategy(new FiveGuess())
                .setGameType("maker")
                .setPlayer(player)
                .buildGame();


        makerGame.setDifficulty(Game.Difficulty.EASY);

        showFunctions();
        int option = Integer.parseInt(scanner.nextLine());

        while (option != 0) {
            switch (option) {
                case 1:
                    System.out.println("Remember that the guess will be of length "
                            + String.valueOf(numHoles));
                    System.out.println("Assuming the last played Guess is:");
                    makerGame.setCorrectAnswer(Guess.generateCode(numHoles, colors));
                    System.out.println(makerGame.getCorrectAnswer());
                    System.out.println("Input BLACK, WHITE or EMPTY, one at a time:");
                    List<Peg> playList = new ArrayList<>();
                    for (int i = 0; i < numHoles; i++)
                        playList.add(new Peg(Peg.Color.valueOf(scanner.nextLine())));
                    System.out.println("Calling playTurn...");
                    System.out.println(makerGame.playTurn(playList).toString());
                    break;
            }
            System.out.println("Press any key to continue...");
            scanner.nextLine();
            showFunctions();
            option = Integer.parseInt(scanner.nextLine());
        }
    }
}
