package Presentation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.Scanner;

public class TransportationManagerMenu {
    public static void transportation_manager() throws SQLException {

        Scanner reader = new Scanner(System.in);

        System.out.println("\nEnter password:");
        String password = reader.nextLine();

        while (!password.equals("123456789")) {
            System.out.println("Wrong password, try again..\n");
            System.out.println("Enter password:");
            password = reader.nextLine();
        }

        while (true) {
            StringBuilder manager_menu = new StringBuilder();
            manager_menu.append("\n-----------------------------------------------------");
            manager_menu.append("\n|            Transportation Manger Menu             |\n");
            manager_menu.append("-----------------------------------------------------\n");
            manager_menu.append("\n");
            manager_menu.append("Hello Transportation manager, what do you want to do?\n");
            manager_menu.append("\nChoose from the options bellow:\n");
            manager_menu.append("\n'1' - Add Driver.\n");
            manager_menu.append("'2' - Add Truck.\n");
            manager_menu.append("'3' - Add Store.\n");
            manager_menu.append("'4' - Add Supplier.\n");
            manager_menu.append("'5' - Make Transportation.\n");
            manager_menu.append("'6' - Show all Transport.\n");
            manager_menu.append("'9' - Exit to the Main menu.");

            System.out.println(manager_menu);

            String answer = reader.nextLine();

            while (!answer.equals("1") && !answer.equals("2") && !answer.equals("3") &&
                    !answer.equals("4") && !answer.equals("5") && !answer.equals("6") && !answer.equals("9")) {

                System.out.println("\nWrong input! please write your ans ware again..\n");
                System.out.println(manager_menu);

                answer = reader.nextLine();
            }

            if (answer.equals("9"))
                break;

            switch (answer) {
                case "1" -> add_driver();
                case "2" -> add_truck();
                case "3" -> add_store();
                case "4" -> add_supplier();
                case "5" -> createTransportation();
                case "6" -> showAllTransportation();
            }
        }
    }

    /**
     * Adding a Driver from the user
     */
    public static void add_driver() throws SQLException {
        DataConnector.add_driver();
    }
    /**
     * Adding a Truck from the user
     */
    public static void add_truck() throws SQLException {
        DataConnector.add_truck();
    }
    /**
     * Adding a Store from the user
     */
    public static void add_store() throws SQLException {
        DataConnector.add_site("Store");
    }
    /**
     * Adding a Supplier from the user
     */
    public static void add_supplier() throws SQLException {
        DataConnector.add_site("Supplier");
    }


    /**
     * Making Transport
     */
    public static void createTransportation() throws SQLException {
        CreateTransportation.createTransport();
    }
    /**
     * Showing all Transport
     */
    public static void showAllTransportation() throws SQLException {

        JsonObject j = DataConnector.printAllTransports();

        if(j == null){
            System.out.println("\nNo transport has left the shipping area ! \n");
        }
        else {
            System.out.println("\n");
            System.out.println("\n\n\nAll the transport that left \n");
            for (String key : j.keySet()) {
                System.out.println("----------------------------------------------------------------\n");
                JsonElement element = j.get(key);
                String s = element.getAsString();
                System.out.println(s);
            }
        }
    }
}

