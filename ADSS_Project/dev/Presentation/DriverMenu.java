package Presentation;

import com.google.gson.JsonObject;
import Domain.Controllers.DriverController;


import java.sql.SQLException;
import java.util.Scanner;

public class DriverMenu {

    /**
     * Driver menu in the System
     */
    public static void driver_x() throws InterruptedException, SQLException {

        JsonObject new_Json = new JsonObject();

        Scanner reader = new Scanner(System.in);

        System.out.println("\nEnter your name:");
        String name = reader.nextLine();

        System.out.println("\nEnter your password:");
        String password = reader.nextLine();

        new_Json.addProperty("Name", name);
        new_Json.addProperty("Password", password);

        String driver_name = DriverController.driverLogIn(new_Json);

        while (driver_name == null) {
            System.out.println("\nThe name or password are wrong, try again..\n");
            System.out.println("Enter name:");
            name = reader.nextLine();

            System.out.println("\nEnter password:");
            password = reader.nextLine();

            new_Json.addProperty("Name", name);
            new_Json.addProperty("Password", password);

            driver_name = DriverController.driverLogIn(new_Json);
        }
        String d = DriverController.printDriverRoute(new_Json);


        String a = "yes";
        while (a.equals("yes")) {
            StringBuilder new_s = new StringBuilder();
            new_s.append("\n-----------------------------------------------------");
            new_s.append("\n|                  Driver Menu                    |\n");
            new_s.append("-----------------------------------------------------\n");

            String s = "\nHello " + driver_name + "!\n";

            new_s.append(s);

            if (!d.equals("Not Have"))
                new_s.append(d);

            new_s.append("\nWhat do you want to do?\n");
            new_s.append("'1' - Report on exit to transportation \n");
            new_s.append("'2' - Report on back from transportation\n");
            new_s.append("'9' - Exit to the Main menu.");

            System.out.println(new_s);
            String answer = reader.nextLine();

            while (!answer.equals("1") && !answer.equals("2") && !answer.equals("9") ) {
                System.out.println("\nWrong input, try again..\n");
                System.out.println(new_s);

                answer = reader.nextLine();

            }
            if (answer.equals("9"))
                break;

            switch (answer) {
                case "1" -> leaving(new_Json);
                case "2" -> back(new_Json);
            }

            System.out.println("\nDo you want to do something else? Enter 'yes' or 'no':");
            a = reader.nextLine();
        }
    }

    /**
     * Update leaving
     * @param j - JsonObject argument represent the Driver
     */
    public static void leaving (JsonObject j) throws SQLException {
        String a = DriverController.updateLeavingDriver(j);
        System.out.println(a);
    }
    /**
     * Update come back
     * @param j - JsonObject argument represent the Driver
     */
    public static void back (JsonObject j) throws SQLException {
        String a = DriverController.updateBackDriver(j);
        System.out.println(a);
    }
}

