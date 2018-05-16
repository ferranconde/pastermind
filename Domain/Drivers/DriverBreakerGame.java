package Domain.Drivers;


import Domain.Classes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DriverBreakerGame implements IDomainDriver {

    private static void showFunctions() {
        System.out.println("1. (Game) void setRanking()");
        System.out.println("2. (Game) Player getPlayer()");
        System.out.println("3. (Game) int getNumTurns()");
        System.out.println("4. (Game) int getNumHoles()");
        System.out.println("5. (Game) long getGameTime()");
        System.out.println("6. (Game) long getTimeLimit()");
        System.out.println("7. (Game) int getNumColors()");
        System.out.println("8. (Game) List<Peg.Color> getColors()");
        System.out.println("9. (Game) int getCurrentTurn()");
        System.out.println("10. (Game) Guess getCorrectAnswer()");
        System.out.println("11. (Game) void setCorrectAnswer(Guess)");
        System.out.println("12. (Game) boolean isFinished()");
        System.out.println("13. (Game) boolean isValidTurn()");
        System.out.println("14. (Game) void setFinished(boolean finished)");
        System.out.println("15. (Game) Guess getLastGuess()");
        System.out.println("16. (Game) List<Guess> getBreakerHistory()");
        System.out.println("17. (Game) List<GuessResult> getMakerHistory()");
        System.out.println("18. (Game) boolean getPlayerWon()");
        System.out.println("19. (Game) Difficulty getDifficulty()");
        System.out.println("20. (Game) int getHintsLeft()");
        System.out.println("21. (Game) String getHint()");
        System.out.println("22. (Game) void setDifficulty(Difficulty difficulty)");
        System.out.println("23. (BreakerGame) PegCombination playTurn(List<Peg> play)");
        System.out.println("    (Ensure you have created a ranking first!)");
        System.out.println("0. Exit driver");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("BreakerGame class driver");
        System.out.println("Since Game is abstract, this driver");
        System.out.println("also tests the Game methods.");
        System.out.println("There is also a JUnit driver with test cases: testGame.java");
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
        // logica sera MakerAI

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
        Game breakerGame = gameBuilder.setNumTurns(numTurns)
                                        .setNumColors(numColors)
                                        .setNumHoles(numHoles)
                                        .setTimeLimit(timeLimit)
                                        .setColorList(colors)
                                        .setComputerStrategy(new MakerAI())
                                        .setGameType("breaker")
                                        .setPlayer(player)
                                        .buildGame();


        breakerGame.setDifficulty(Game.Difficulty.EASY);

        showFunctions();
        int option = Integer.parseInt(scanner.nextLine());

        while (option != 0) {
            switch (option) {
                case 1:
                    System.out.println("Creating an empty ranking and assigning it to the game");
                    Ranking rank = new Ranking();
                    System.out.println("Calling setRanking...");
                    breakerGame.setRanking(rank);
                    System.out.println("Ranking added!");
                    break;

                case 2:
                    System.out.println("Calling getPlayer...");
                    System.out.println(breakerGame.getPlayer().toString());
                    break;

                case 3:
                    System.out.println("Calling getNumTurns...");
                    System.out.println(String.valueOf(breakerGame.getNumTurns()));
                    break;

                case 4:
                    System.out.println("Calling getNumHoles...");
                    System.out.println(String.valueOf(breakerGame.getNumHoles()));
                    break;

                case 5:
                    System.out.println("Calling getGameTime...");
                    System.out.println(String.valueOf(breakerGame.getGameTime()));
                    break;

                case 6:
                    System.out.println("Calling getTimeLimit...");
                    System.out.println(String.valueOf(breakerGame.getTimeLimit()));
                    break;

                case 7:
                    System.out.println("Calling getNumColors...");
                    System.out.println(String.valueOf(breakerGame.getNumColors()));
                    break;

                case 8:
                    System.out.println("Calling getColors...");
                    List<Peg.Color> resultColorList = breakerGame.getColors();
                    for (Peg.Color c : resultColorList) System.out.println(c.toString());
                    break;

                case 9:
                    System.out.println("Calling getCurrentTurn...");
                    System.out.println(String.valueOf(breakerGame.getCurrentTurn()));
                    break;

                case 10:
                    System.out.println("Calling getCorrectAnswer...");
                    System.out.println(breakerGame.getCorrectAnswer().toString());
                    break;

                case 11:
                    int guessSize = numHoles;
                    System.out.println("Input the desired colors, one at a time, from the following list:");
                    for (Peg.Color c : colors) System.out.println(c.toString());
                    List<Peg> testList = new ArrayList<>();
                    for (int i = 0; i < guessSize; i++)
                        testList.add(new Peg(Peg.Color.valueOf(scanner.nextLine())));
                    Guess g = new Guess(testList);
                    System.out.println("Calling setCorrectAnswer...");
                    breakerGame.setCorrectAnswer(g);
                    break;

                case 12:
                    System.out.println("Calling isFinished...");
                    System.out.println(breakerGame.isFinished());
                    break;

                case 13:
                    System.out.println("Calling isValidTurn...");
                    System.out.println(breakerGame.isValidTurn());
                    break;

                case 14:
                    System.out.println("Input 'true' or 'false':");
                    breakerGame.setFinished(scanner.nextLine().equals("true"));
                    System.out.println("Calling setFinished...");
                    break;

                case 15:
                    System.out.println("Calling getLastGuess...");
                    try {
                        System.out.println(breakerGame.getLastGuess().toString());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Please, first play a turn using option 23");
                    }
                    break;

                case 16:
                    System.out.println("Calling getBreakerHistory...");
                    List<Guess> breakerHistory = breakerGame.getBreakerHistory();
                    for (Guess gh : breakerHistory) System.out.println(gh.toString());
                    break;

                case 17:
                    System.out.println("Calling getMakerHistory...");
                    List<GuessResult> makerHistory = breakerGame.getMakerHistory();
                    for (GuessResult grh : makerHistory) System.out.println(grh.toString());
                    break;

                case 18:
                    System.out.println("Calling getPlayerWon...");
                    System.out.println(breakerGame.getPlayerWon());
                    break;

                case 19:
                    System.out.println("Calling getDifficulty...");
                    System.out.println(breakerGame.getDifficulty().toString());
                    break;

                case 20:
                    System.out.println("Calling getHintsLeft...");
                    System.out.println(breakerGame.getHintsLeft());
                    break;

                case 21:
                    System.out.println("Calling getHint...");
                    System.out.println(breakerGame.getHint());
                    break;

                case 22:
                    System.out.println("Input EASY, MEDIUM or HARD:");
                    Game.Difficulty diff = Game.Difficulty.valueOf(scanner.nextLine());
                    System.out.println("Calling setDifficulty...");
                    breakerGame.setDifficulty(diff);
                    break;

                case 23:
                    System.out.println("Remember that the guess will be of length "
                    + String.valueOf(numHoles));
                    System.out.println("Input the desired colors, one at a time, from the following list:");
                    for (Peg.Color c : colors) System.out.println(c.toString());
                    List<Peg> playList = new ArrayList<>();
                    for (int i = 0; i < numHoles; i++)
                        playList.add(new Peg(Peg.Color.valueOf(scanner.nextLine())));
                    System.out.println("Calling playTurn...");
                    System.out.println(breakerGame.playTurn(playList).toString());
                    break;



            }
            System.out.println("Press any key to continue...");
            scanner.nextLine();
            showFunctions();
            option = Integer.parseInt(scanner.nextLine());
        }
    }
}
