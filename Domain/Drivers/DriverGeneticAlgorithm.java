package Domain.Drivers;

import Domain.Classes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DriverGeneticAlgorithm implements IDomainDriver {

    private static void showFunctions() {
        System.out.println("1. Guess playTurn(GuessResult result)");
        System.out.println("0. Exit driver");
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("GeneticAlgorithm class driver");
        System.out.println("");

        System.out.println("Input the length of the guesses:");
        int size = Integer.parseInt(scanner.nextLine());
        System.out.println("Input the desired number of colors:");
        int numColors = Integer.parseInt(scanner.nextLine());
        System.out.println("Input the desired colors, one at a time, from the following list:");
        for (Peg.Color c : Peg.Color.values()) System.out.println(c.toString());
        List<Peg.Color> colors = new ArrayList<>();
        for (int i = 0; i < numColors; i++) colors.add(Peg.Color.valueOf(scanner.nextLine()));


        // Game needed to test
        Game.GameBuilder gameBuilder = new Game.GameBuilder();
        Game makerGame = gameBuilder.setNumTurns(10)
                .setNumColors(numColors)
                .setNumHoles(size)
                .setTimeLimit(1000)
                .setColorList(colors)
                .setComputerStrategy(new GeneticAlgorithm())
                .setGameType("maker")
                .setPlayer(new Player("hola", "holahola", "si"))
                .buildGame();

        makerGame.setDifficulty(Game.Difficulty.EASY);

        IBreakerLogic breaker = new GeneticAlgorithm();
        breaker.initialize(makerGame);

        showFunctions();
        int option = Integer.parseInt(scanner.nextLine());

        while (option != 0) {
            switch (option) {
                case 1:
                    System.out.println("Submit a GuessResult to GeneticAlgorithm");
                    System.out.println("The code suggested by GeneticAlgorithm is:");
                    System.out.println(Guess.generateCode(size, colors).toString());
                    System.out.println("Input BLACK, WHITE or EMPTY, one at a time:");
                    List<Peg> tryList = new ArrayList<>();
                    for (int i = 0; i < size; i++)
                        tryList.add(new Peg(Peg.Color.valueOf(scanner.nextLine())));
                    GuessResult tryGuess = new GuessResult(tryList);

                    System.out.println("The evaluation submitted is " + tryGuess.toString());
                    System.out.println("Calling playTurn...");
                    System.out.println(breaker.playTurn(tryGuess).toString());
                    break;

            }
            System.out.println("Press any key to continue...");
            scanner.nextLine();
            showFunctions();
            option = Integer.parseInt(scanner.nextLine());
        }
    }
}
