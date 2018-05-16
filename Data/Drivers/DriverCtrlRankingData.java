package Data.Drivers;

import Data.CtrlRankingData;
import java.util.Scanner;

public class DriverCtrlRankingData implements IDataDriver {

    private static void showFunctions() {
        System.out.println("1. CtrlGameData getInstance()");
        System.out.println("0. Exit driver");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("CtrlRankingData class driver");
        System.out.println("");

        showFunctions();
        int option = Integer.parseInt(scanner.nextLine());
        CtrlRankingData control = CtrlRankingData.getInstance();

        while (option != 0) {
            switch (option) {

                case 1:
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
