package Presentation;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    /**
     * Start of the Program
     */
    public static void main(String[] args) throws InterruptedException, SQLException {

        Scanner reader = new Scanner(System.in);

        while (true) {

            StringBuilder menu = new StringBuilder();

            menu.append("\n--------------------------------------------------------");
            menu.append("\n| Hello and Welcome to Super - Li Transportation Menu |\n");
            menu.append("--------------------------------------------------------\n");
            menu.append("\n");

            menu.append("What is your role in the system?\n");
            menu.append("\n'1' -  Manager.\n");
            menu.append("'2' - Driver.\n");
            menu.append("'9' - Exit.");
            System.out.println(menu);

            String type = reader.nextLine();

            while (!type.equals("1") && !type.equals("2") && !type.equals("9")) {

                System.out.println("\nWrong input! please try again..\n");
                System.out.println(menu);

                type = reader.nextLine();
            }
            switch (type) {
                case "1" -> TransportationManagerMenu.transportation_manager();
                case "2" -> DriverMenu.driver_x();
                case "9" -> {
                    return;
                }
            }
        }
    }
}
