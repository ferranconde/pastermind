package Domain.Drivers;


import Domain.Classes.Player;

import java.util.Scanner;

public class DriverPlayer implements IDomainDriver {

    private static void showFunctions() {
        System.out.println("1. String getName()");
        System.out.println("2. String getUsername()");
        System.out.println("3. String getPassword()");
        System.out.println("4. String toString()");
        System.out.println("5. boolean equals(Object other)");
        System.out.println("6. int hashCode()");
        System.out.println("0. Exit driver");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Player class driver");
        System.out.println("");

        System.out.println("Input the player's name:");
        String name = scanner.nextLine();
        System.out.println("Input the player's username:");
        String user = scanner.nextLine();
        System.out.println("Input the player's password (only for testing purposes!):");
        String password = scanner.nextLine();

        Player player = new Player(user, name, password);

        showFunctions();
        int option = Integer.parseInt(scanner.nextLine());

        while (option != 0) {
            switch (option) {
                case 1:
                    System.out.println("Calling getName...");
                    System.out.println(player.getName());
                    break;

                case 2:
                    System.out.println("Calling getUsername...");
                    System.out.println(player.getUsername());
                    break;

                case 3:
                    System.out.println("Calling getPassword...");
                    System.out.println(player.getPassword());
                    break;

                case 4:
                    System.out.println("Calling toString...");
                    System.out.println(player.toString());
                    break;

                case 5:
                    System.out.println("Input a new player's name:");
                    String name2 = scanner.nextLine();
                    System.out.println("Input the new player's username:");
                    String user2 = scanner.nextLine();
                    System.out.println("Input the new player's password (only for testing purposes!):");
                    String password2 = scanner.nextLine();
                    Player player2 = new Player(user2, name2, password2);

                    System.out.println("Calling equals...");
                    System.out.println(player.equals(player2));
                    break;

                case 6:
                    System.out.println("Calling hashCode...");
                    System.out.println(player.hashCode());
                    break;

            }
            System.out.println("Press any key to continue...");
            scanner.nextLine();
            showFunctions();
            option = Integer.parseInt(scanner.nextLine());
        }
    }
}
