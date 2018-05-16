package Domain.Drivers;


import Domain.Classes.Peg;
import Domain.Controllers.CtrlDomain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DriverCtrlDomain implements IDomainDriver {

    private static CtrlDomain control;

    private static void showFunctions() {
        System.out.println("1. String getLoggedPlayerName()");
        System.out.println("2. void storePlayer(String identifier, String name, String password)");
        System.out.println("3. void storeRanking()");
        System.out.println("4. String getRanking()");
        System.out.println("5. boolean loginPlayer(String username, String password)");
        System.out.println("6. boolean existsPlayer(String username)");
        System.out.println("7. CtrlDomain setGameType(String gameType)");
        System.out.println("8. String getGameType()");
        System.out.println("9. CtrlDomain setGameNumTurns(int numTurns)");
        System.out.println("10. Integer getGameNumTurns()");
        System.out.println("11. int getGameCurrentTurn()");
        System.out.println("12. CtrlDomain setGameNumHoles(int numHoles)");
        System.out.println("13. Integer getGameNumHoles()");
        System.out.println("14. CtrlDomain setGameNumColors(int numColors)");
        System.out.println("15. Integer getGameNumColors()");
        System.out.println("16. CtrlDomain setGameTimeLimit(long timeLimit)");
        System.out.println("17. long getGameTimeLimit()");
        System.out.println("18. CtrlDomain setGameColorList(List<String> colorList)");
        System.out.println("19. List<String> getGameColorList()");
        System.out.println("20. CtrlDomain setGameComputerStrategy(String strategy)");
        System.out.println("21. String getGameCorrectAnswer()");
        System.out.println("22. void setGameCorrectAnswer(String correctAnswer)");
        System.out.println("23. void buildGame()");
        System.out.println("24. List<String> getColorList()");
        System.out.println("25. boolean isGameFinished()");
        System.out.println("26. String playTurn(String play)");
        System.out.println("27. List<String> getGameBreakerHistory()");
        System.out.println("28. List<String> getGameMakerHistory()");
        System.out.println("29. boolean getGamePlayerWon()");
        System.out.println("30. void saveGame(String name)");
        System.out.println("31. void loadGame(String fileName)");
        System.out.println("32. List<String> getAllSavedGames()");
        System.out.println("33. String getGameDifficulty()");
        System.out.println("34. void setGameDifficulty(String difficulty)");
        System.out.println("35. int getGameHintsLeft()");
        System.out.println("36. int getGameMaxHints()");
        System.out.println("37. String getGameHint()");
        System.out.println("38. List<String> getGameDifficultyLevels()");
        System.out.println("39. boolean checkValidGuessResult(String play)");
        System.out.println("40. boolean isGameValidTurn()");
        System.out.println("41. long getGameCurrentTime()");
        System.out.println("42. void startCountingTime()");
        System.out.println("43. void exitProgram()");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("CtrlDomain class driver");


        try {
            control = new CtrlDomain();
        } catch (Exception e) {
            e.printStackTrace();
        }

        showFunctions();

        int option = Integer.parseInt(scanner.nextLine());

        while (option != 0) {
            switch (option) {
                case 1:
                    try {
                        System.out.println(control.getLoggedPlayerName());
                    } catch (Exception e) {
                        System.out.println("No player logged. Create a player and/or login with option 5");
                    }
                    break;

                case 2:
                    System.out.println("Input the username, the name and the password, one at a time:");
                    try {
                        control.storePlayer(scanner.nextLine(), scanner.nextLine(), scanner.nextLine());
                    } catch (IOException e) {
                        System.out.println("Error during I/O.");
                    }
                    break;

                case 3:
                    try {
                        control.storeRanking();
                    } catch (IOException e) {
                        System.out.println("Error during I/O.");
                    }
                    break;

                case 4:
                    try {
                        System.out.println(control.getRanking());
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                    break;

                case 5:
                    System.out.println("Input the username:");
                    String userLogin = scanner.nextLine();
                    System.out.println("Input the password:");
                    String passLogin = scanner.nextLine();
                    try {
                        if (control.loginPlayer(userLogin, passLogin))
                            System.out.println("Login successful!");
                        else System.out.println("Login failed.");
                    } catch (Exception e) {
                        System.out.println("This username does not exist.");
                    }
                    break;

                case 6:
                    System.out.println("Input the username of the player:");
                    System.out.println(control.existsPlayer(scanner.nextLine()));
                    break;

                case 7:
                    System.out.println("Input the game type ('breaker' or 'maker'):");
                    System.out.println("Changes will apply when you build the game.");
                    control.setGameType(scanner.nextLine());
                    break;

                case 8:
                    try {
                        System.out.println(control.getGameType());
                    } catch (Exception e) {
                        System.out.println("Game type is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                    }
                    break;

                case 9:
                    System.out.println("Input the number of turns:");
                    control.setGameNumTurns(Integer.parseInt(scanner.nextLine()));
                    break;

                case 10:
                    try {
                        System.out.println(control.getGameNumTurns());
                    } catch (Exception e) {
                        System.out.println("Game number of turns is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                    }
                    break;

                case 11:
                    try {
                        System.out.println(control.getGameCurrentTurn());
                    } catch (Exception e) {
                        System.out.println("Game current turn is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                    }
                    break;

                case 12:
                    System.out.println("Input the number of holes:");
                    control.setGameNumHoles(Integer.parseInt(scanner.nextLine()));
                    break;

                case 13:
                    try {
                        System.out.println(control.getGameNumHoles());
                    } catch (Exception e) {
                        System.out.println("Game number of holes is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                    }
                    break;

                case 14:
                    System.out.println("Input the number of colors:");
                    control.setGameNumColors(Integer.parseInt(scanner.nextLine()));
                    break;

                case 15:
                    try {
                        System.out.println(control.getGameNumColors());
                    } catch (Exception e) {
                        System.out.println("Game number of colors is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                    }
                    break;

                case 16:
                    System.out.println("Input the time limit:");
                    control.setGameTimeLimit(Long.parseLong(scanner.nextLine()));
                    break;

                case 17:
                    System.out.println("This function converts the time to an exact int / 1000");
                    try {
                        System.out.println(control.getGameTimeLimit());
                    } catch (Exception e) {
                        System.out.println("An error occurred.");
                    }
                    break;

                case 18:
                    for (Peg.Color c : Peg.Color.values()) System.out.println(c.toString());
                    System.out.println("Input the number of colors to select:");
                    int numColorsToSelect = Integer.parseInt(scanner.nextLine());
                    System.out.println("Write the colors, one at a time:");
                    List<String> colorList18 = new ArrayList<>();
                    for (int i = 0; i < numColorsToSelect; i++) {
                        colorList18.add(Peg.Color.valueOf(scanner.nextLine()).toString());
                    }
                    System.out.println("Calling setGameColorList...");
                    control.setGameColorList(colorList18);
                    break;

                case 19:
                    try {
                        for (String s : control.getGameColorList()) System.out.println(s);
                    } catch (Exception e) {
                        System.out.println("Game color list is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                    }
                    break;

                case 20:
                    System.out.println("Input the computer strategy.");
                    System.out.println("Options are: 'fiveguess', 'genetic', 'makerai':");
                    control.setGameComputerStrategy(scanner.nextLine());
                    break;

                case 21:
                    try {
                        System.out.println(control.getGameCorrectAnswer());
                    } catch (Exception e) {
                        System.out.println("Game correct answer is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                    }
                    break;

                case 22:
                    System.out.println("Input the correct answer.");
                    System.out.println("Example: RED BLUE YELLOW ORANGE");
                    try {
                        control.setGameCorrectAnswer(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("Game is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                    }
                    break;

                case 23:
                    System.out.println("A new game will be built using your settings.");
                    control.buildGame();
                    break;

                case 24:
                    try {
                        for (String s : control.getColorList()) System.out.println(s);
                    } catch (Exception e) {
                        System.out.println("An error occurred.");
                    }
                    break;

                case 25:
                    try {
                        System.out.println(control.isGameFinished());
                    } catch (Exception e) {
                        System.out.println("Game finished state is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                    }
                    break;

                case 26:
                    try {
                        System.out.println("Input a play.");
                        System.out.println("Format: RED BLUE RED YELLOW");
                        System.out.println(control.playTurn(scanner.nextLine()));
                    } catch (Exception e) {
                        System.out.println("Game is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                    }
                    break;

                case 27:
                    try {
                        for (String s : control.getGameBreakerHistory()) System.out.println(s);
                    } catch (Exception e) {
                        System.out.println("Game is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                        System.out.println("Play a turn to see changes.");
                    }
                    break;

                case 28:
                    try {
                        for (String s : control.getGameMakerHistory()) System.out.println(s);
                    } catch (Exception e) {
                        System.out.println("Game is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                        System.out.println("Play a turn to see changes.");
                    }
                    break;

                case 29:
                    try {
                        System.out.println(control.getGamePlayerWon());
                    } catch (Exception e) {
                        System.out.println("Game player won state is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                    }
                    break;

                case 30:
                    System.out.println("Input a name to save the game file with:");
                    try {
                        control.saveGame(scanner.nextLine());
                    } catch (IOException e) {
                        System.out.println(e.toString());
                    }
                    break;

                case 31:
                    System.out.println("Input the filename to load:");
                    try {
                        control.loadGame(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                    break;

                case 32:
                    try {
                        for (String s : control.getAllSavedGames()) System.out.println(s);
                    } catch (Exception e) {
                        System.out.println("Game is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                    }
                    break;

                case 33:
                    System.out.println("Remember to set a difficulty (option 34) after building a Game");
                    try {
                        System.out.println(control.getGameDifficulty());
                    } catch (Exception e) {
                        System.out.println("Game difficulty is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                        System.out.println("Then, set a difficulty using option 34.");
                    }
                    break;

                case 34:
                    System.out.println("Input the desired difficulty.");
                    System.out.println("'EASY', 'MEDIUM' or 'HARD':");
                    try {
                        control.setGameDifficulty(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("Make sure you have built the Game and set all the params.");
                    }
                    break;

                case 35:
                    try {
                        System.out.println(control.getGameHintsLeft());
                    } catch (Exception e) {
                        System.out.println("Game hints number is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                    }
                    break;

                case 36:
                    try {
                        System.out.println(control.getGameMaxHints());
                    } catch (Exception e) {
                        System.out.println("Game maximum hints number is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                    }
                    break;

                case 37:
                    System.out.println("Remember to set a difficulty (option 34) after building a Game");
                    try {
                        System.out.println(control.getGameHint());
                    } catch (Exception e) {
                        System.out.println("After calling buildGame, set a difficulty (option 34)");
                    }
                    break;

                case 38:
                    for (String s : control.getGameDifficultyLevels()) System.out.println(s);
                    break;

                case 39:
                    System.out.println("This method depends on the type of game created.");
                    System.out.println("At least one playTurn() must have been called.");
                    System.out.println("A game must have been built.");
                    System.out.println("");
                    System.out.println("Input a GuessResult.");
                    System.out.println("Format: WHITE BLACK EMPTY BLACK");
                    try {
                        System.out.println(control.checkValidGuessResult(scanner.nextLine()));
                    } catch (Exception e) {
                        System.out.println("Some of the above conditions did not match.");
                    }
                    break;

                case 40:
                    try {
                        System.out.println(control.isGameValidTurn());
                    } catch (Exception e) {
                        System.out.println("Game is not defined.");
                        System.out.println("When you are done setting parameters, call buildGame.");
                    }
                    break;

                case 41:
                    try {
                        System.out.println(control.getGameCurrentTime());
                    } catch (Exception e) {
                        System.out.println("An error occurred.");
                    }
                    break;

                case 42:
                    try {
                        control.startCountingTime();
                    } catch (Exception e) {
                        System.out.println("An error occurred.");
                    }
                    break;


                case 43:
                    try {
                        control.exitProgram();
                    } catch (Exception e) {
                        System.out.println("An error occurred.");
                    }
                    break;

            }
            System.out.println("Press any key to continue...");
            scanner.nextLine();
            showFunctions();
            option = Integer.parseInt(scanner.nextLine());
        }

    }


}
