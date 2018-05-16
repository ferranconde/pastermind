package Domain.Drivers;


import Domain.Classes.Player;
import Domain.Classes.Ranking;

import java.util.Scanner;

public class DriverRanking implements IDomainDriver {

    private static void showFunctions() {
        System.out.println("1. void addScore(Player player, int numHoles, int numColors, int gameTime, int numTurns, boolean win)");
        System.out.println("2. String toString()");
        System.out.println("0. Exit driver");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ranking class driver");
        System.out.println("");

        Ranking ranking = new Ranking();

        showFunctions();
        int option = Integer.parseInt(scanner.nextLine());

        while (option != 0) {
            switch (option) {
                case 1:
                    System.out.println("Create a new entry in the ranking:");

                    System.out.println("Input a player's name:");
                    String name = scanner.nextLine();
                    System.out.println("Input a player's username:");
                    String user = scanner.nextLine();
                    System.out.println("Input a player's password:");
                    String password = scanner.nextLine();

                    Player player = new Player(user, name, password);

                    System.out.println("Input the game settings:");
                    System.out.println("Number of holes?");
                    Integer numHoles = Integer.parseInt(scanner.nextLine());
                    System.out.println("Number of colors?");
                    Integer numColors = Integer.parseInt(scanner.nextLine());
                    System.out.println("Time elapsed?");
                    int gameTime = Integer.parseInt(scanner.nextLine());
                    System.out.println("Number of turns?");
                    int numTurns = Integer.parseInt(scanner.nextLine());
                    System.out.println("Did the player win? Answer Y or N:");
                    boolean win = scanner.nextLine().equals("Y");

                    System.out.println("Calling addScore...");
                    ranking.addScore(player, numHoles, numColors, gameTime, numTurns, win);
                    break;

                case 2:
                    System.out.println("Calling toString...");
                    System.out.println(ranking.toString());
                    break;


            }
            System.out.println("Press any key to continue...");
            scanner.nextLine();
            showFunctions();
            option = Integer.parseInt(scanner.nextLine());
        }
    }
}
