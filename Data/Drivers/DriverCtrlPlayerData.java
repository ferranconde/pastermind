package Data.Drivers;

import Data.CtrlPlayerData;
import Domain.Classes.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DriverCtrlPlayerData implements IDataDriver {

    private static void showFunctions() {
        System.out.println("1. (CtrlGenericData) Object get(String identifier)");
        System.out.println("2. (CtrlGenericData) List<Object> getAll()");
        System.out.println("3. (CtrlGenericData) File[] getAllFiles()");
        System.out.println("4. (CtrlGenericData) void store(String identifier, Object object)");
        System.out.println("5. (CtrlGenericData) boolean exists(String identifier)");
        System.out.println("6. (CtrlPlayerData) CtrlPlayerData getInstance()");
        System.out.println("0. Exit driver");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("CtrlPlayerData class driver");
        System.out.println("Since CtrlGenericData is abstract, this driver");
        System.out.println("also tests the CtrlGenericData methods.");
        System.out.println("");

        showFunctions();
        int option = Integer.parseInt(scanner.nextLine());
        CtrlPlayerData control = CtrlPlayerData.getInstance();

        while (option != 0) {
            switch (option) {
                case 1:
                    System.out.println("Input a file name:");
                    try {
                        Player player = (Player) control.get(scanner.nextLine());
                        if (player != null) {
                            System.out.println("Player stored was:");
                            System.out.println(player.toString());
                        }
                    } catch (ClassNotFoundException e) {
                        System.out.println("Class not found!");
                    } catch (IOException e2) {
                        System.out.println("Exception during I/O.");
                    }
                    break;

                case 2:
                    System.out.println("Calling getAll...");
                    try {
                        List<Object> objects = control.getAll();
                        for (Object o : objects) System.out.println(o.toString());
                    } catch (ClassNotFoundException e) {
                        System.out.println("Class not found!");
                    } catch (IOException e2) {
                        System.out.println("Exception during I/O.");
                    }
                    break;

                case 3:
                    try {
                        File[] files = control.getAllFiles();
                        for (File f : files) System.out.println(f.getName());
                    } catch (NullPointerException e) {
                        System.out.println("No files found!");
                    }
                    break;

                case 4:
                    System.out.println("Input a new file name:");
                    String filename = scanner.nextLine();
                    System.out.println("Create a test Player to store it");
                    System.out.println("Input name, username and password, each in a new line:");
                    Player player = new Player(scanner.nextLine(), scanner.nextLine(), scanner.nextLine());
                    System.out.println("Calling store...");
                    try {
                        control.store(filename, player);
                    } catch (IOException e) {
                        System.out.println("Exception during I/O.");
                    }
                    break;

                case 5:
                    System.out.println("Input a file name:");
                    String checkName = scanner.nextLine();
                    System.out.println("Calling exists...");
                    System.out.println(control.exists(checkName));
                    break;

                case 6:
                    System.out.println("Calling getInstance...");
                    System.out.println(control.getInstance().toString());
                    break;

            }
            System.out.println("Press any key to continue...");
            scanner.nextLine();
            showFunctions();
            option = Integer.parseInt(scanner.nextLine());
        }

    }
}
