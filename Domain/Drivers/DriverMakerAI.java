package Domain.Drivers;


import Domain.Classes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DriverMakerAI implements IDomainDriver {

    private static void showFunctions() {
        System.out.println("1. GuessResult playTurn(Guess guess)");
        System.out.println("0. Exit driver");
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("MakerAI class driver");
        System.out.println("");


        System.out.println("Input the length of the correct Guess:");
        int size = Integer.parseInt(scanner.nextLine());
        System.out.println("Input the desired number of colors:");
        int numColors = Integer.parseInt(scanner.nextLine());
        System.out.println("Input the desired colors, one at a time, from the following list:");
        for (Peg.Color c : Peg.Color.values()) System.out.println(c.toString());
        List<Peg.Color> colors = new ArrayList<>();
        for (int i = 0; i < numColors; i++) colors.add(Peg.Color.valueOf(scanner.nextLine()));
        System.out.println("Now form the Guess");
        for (Peg.Color c2 : colors) System.out.println(c2.toString());
        System.out.println("Input the desired colors from the selection, one at a time:");
        List<Peg> testList = new ArrayList<>();
        for (int i = 0; i < size; i++)
            testList.add(new Peg(Peg.Color.valueOf(scanner.nextLine())));
        Guess g = new Guess(testList);
        System.out.println("Remember: " + g.toString());
        System.out.println("MakerAI will evaluate your tries based on this secret code");

        // Game needed to test
        Game.GameBuilder gameBuilder = new Game.GameBuilder();
        Game breakerGame = gameBuilder.setNumTurns(10)
                .setNumColors(numColors)
                .setNumHoles(size)
                .setTimeLimit(1000)
                .setColorList(colors)
                .setComputerStrategy(new MakerAI())
                .setGameType("breaker")
                .setPlayer(new Player("hola", "holahola", "si"))
                .buildGame();

        breakerGame.setDifficulty(Game.Difficulty.EASY);
        breakerGame.setCorrectAnswer(g);

        IMakerLogic maker = new MakerAI();
        maker.initialize(breakerGame);

        showFunctions();
        int option = Integer.parseInt(scanner.nextLine());

        while (option != 0) {
            switch (option) {
                case 1:
                    System.out.println("Submit a Guess to MakerAI");
                    System.out.println("Input the desired colors, one at a time, from the following list:");
                    for (Peg.Color c : colors) System.out.println(c.toString());
                    List<Peg> tryList = new ArrayList<>();
                    for (int i = 0; i < size; i++)
                        tryList.add(new Peg(Peg.Color.valueOf(scanner.nextLine())));
                    Guess tryGuess = new Guess(tryList);

                    System.out.println("The guess submitted is " + tryGuess.toString());
                    System.out.println("Calling playTurn...");
                    System.out.println(maker.playTurn(tryGuess).toString());
                    break;

            }
            System.out.println("Press any key to continue...");
            scanner.nextLine();
            showFunctions();
            option = Integer.parseInt(scanner.nextLine());
        }
    }
}
