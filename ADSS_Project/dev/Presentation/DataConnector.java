package Presentation;

import Domain.Controllers.DataController;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.Scanner;

public class DataConnector {

    /**
     * Input from the user for Driver arguments
     */
    public static void add_driver() throws SQLException {

        JsonObject new_json = new JsonObject();
        Scanner reader = new Scanner(System.in);

        String answer = "yes";

        while (answer.equals("yes")){
            System.out.println("\nEnter the name and last name of the driver:");
            String name = reader.nextLine();

            System.out.println("\nEnter the license leve of the driver: 'A', 'B', 'C'");
            String license = reader.nextLine();

            while (!license.equals("A") && !license.equals("B") && !license.equals("C")){
                System.out.println("\nWrong input! try again..\n");
                System.out.println("Enter the license leve of the driver: 'A', 'B', 'C'");
                license = reader.nextLine();
            }

            System.out.println("\nEnter password of 8 digit:");
            String password = reader.nextLine();

            while (password.length() != 8){
                System.out.println("\nWrong input! The password should hava a 8 digit. try again..\n");
                System.out.println("Enter password of 8 digit:");
                password = reader.nextLine();
            }

            new_json.addProperty("Name",name);
            new_json.addProperty("Licence",license);
            new_json.addProperty("Password",password);


            DataController.AddDriver(new_json);

            System.out.println("\nDriver added successfully!\n");

            System.out.println("Would you like to add another driver? Enter 'yes' or 'no'.");
            answer = reader.nextLine();
        }
    }
    /**
     * Input from the user for Site arguments
     * @param Type - String type represent Store / Supplier
     */
    public static void add_site(String Type) throws SQLException{

        JsonObject new_json = new JsonObject();

        new_json.addProperty("Type", Type);

        Scanner reader = new Scanner(System.in);

        String a = "yes";

        while (a.equals("yes")) {

            System.out.println("\nEnter name:");
            new_json.addProperty("Name", reader.nextLine());

            System.out.println("\nEnter address:");
            new_json.addProperty("Address", reader.nextLine());

            System.out.println("\nEnter phone number:");
            new_json.addProperty("Phone number", reader.nextLine());

            System.out.println("\nEnter contact:");
            new_json.addProperty("Contact", reader.nextLine());

            System.out.println("\nEnter Shipping area:");
            new_json.addProperty("Shipping area", reader.nextLine());

            DataController.AddSite(new_json);

            System.out.println("\n" + Type + " added successfully!\n");
            System.out.println("Would you like to add another " + Type + "? Press 'yes' or 'no'.");
            a = reader.nextLine();
        }
    }
    /**
     * Input from the user for Truck arguments
     */
    public static void add_truck()throws SQLException {

        JsonObject new_json = new JsonObject();

        Scanner reader = new Scanner(System.in);

        String answer = "yes";

        while (answer.equals("yes")){

            System.out.println("\nEnter licence_number of 8 digits like this - 000-00-000: ");
            String licence_number = reader.nextLine();

            while (licence_number.length() != 10){
                System.out.println("\nWrong input! The licence_number should hava a 8 digit. try again..");
                System.out.println("Enter licence_number of 8 digits like this - 000-00-000: ");
                licence_number = reader.nextLine();
            }

            System.out.println("\nEnter the license leve of the Truck: 'A', 'B', 'C'");
            String licence_level = reader.nextLine();

            while (!licence_level.equals("A") && !licence_level.equals("B") && !licence_level.equals("C")){
                System.out.println("\nWrong input! try again..");
                System.out.println("Enter the license leve of the driver: 'A', 'B' OR 'C'");
                licence_level = reader.nextLine();
            }

            String net_weight = null;
            while (net_weight == null){
                System.out.println("\nPlease enter net weight:");
                net_weight = reader.nextLine();

                try {
                    Double.parseDouble(net_weight);
                }
                catch (Exception e){
                    System.out.println("\nWrong input! try again..");
                    net_weight = null;
                }
            }

            String max_weight = null;
            while (max_weight == null){
                System.out.println("\nPlease enter max weight:");
                max_weight = reader.nextLine();

                try {
                    Double.parseDouble(max_weight);
                }
                catch (Exception e){
                    System.out.println("\nWrong input! try again..");
                    max_weight = null;
                }
            }

            new_json.addProperty("Licence number", licence_number);
            new_json.addProperty("Licence level", licence_level);
            new_json.addProperty("Net weight", net_weight);
            new_json.addProperty("Max weight", max_weight);

            DataController.AddTruck(new_json);

            System.out.println("\nTruck added successfully!");

            System.out.println("\nWould you like to add another truck? Enter 'yes' or 'no'");
            answer = reader.nextLine();
        }
    }

    public static JsonObject printAllTransports() throws SQLException {
        return DataController.chooseAllTransports();
    }
}
