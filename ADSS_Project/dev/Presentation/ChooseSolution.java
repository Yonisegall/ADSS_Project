package Presentation;

import Domain.Controllers.SolutionsController;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.Scanner;

public class ChooseSolution {
    public static String chooseTypeOfSolution() {

        Scanner reader = new Scanner(System.in);
        StringBuilder new_string = new StringBuilder();
        new_string.append("\nThere is OverWeight, Please choose from the options bellow:\n");
        new_string.append("Press '1' to - Change Sites.\n");
        new_string.append("Press '2' to - Change Truck.\n");
        new_string.append("Press '3' to - Drop Sites.\n");
        new_string.append("Press '4' to - Drop Items.");

        String s = "0";

        while (!s.equals("1") && !s.equals("2") && !s.equals("3") && !s.equals("4")) {
            System.out.println(new_string);
            s = reader.next();
        }
        return s;
    }
    public static String chooseTruckToReplace(JsonObject j) {
        System.out.println("\nPlease choose a different Truck for the Transport: ");
        return CreateTransportation.printToUser(j.size(), j);
    }
    public static boolean dropSites() {

        Scanner reader = new Scanner(System.in);
        String p = "yes";
        boolean bool = true;
        while (p.equals("yes")) {
            JsonObject sol_w = SolutionsController.ChooseSupplierToDrop();
            System.out.println("\nWhich Supplier do you want to dropped ? ");
            String site_answer = CreateTransportation.printToUser(sol_w.size(), sol_w);
            SolutionsController.drop_Site(site_answer);
            if(sol_w.size() - 1 == 0){
                bool = false;
                break;
            }
            System.out.println("\nWould you like to drop another Site? ");
            System.out.println("Enter 'yes' or 'no':");
            p = reader.next();
        }
        if(!bool) System.out.println("\nYou dropped all the Sits from the Transport ! ");
        return bool;
    }
    public static boolean dropItems() {
        Scanner reader = new Scanner(System.in);

        JsonObject j_item;
        String p = "yes";
        boolean bool = true;
        JsonObject sol_w = SolutionsController.ChooseSupplierToDrop();
        while (p.equals("yes")) {
            System.out.println("\nWhich Site do you want to dropped Items from ? ");
            String site_answer = CreateTransportation.printToUser(sol_w.size(), sol_w);
            System.out.println("\nWhich Item do you want to dropped ? ");
            j_item = SolutionsController.getItem(site_answer);
            String item_answer = CreateTransportation.printToUser(j_item.size(), j_item);

            SolutionsController.drop_Items(item_answer, site_answer);
            sol_w = SolutionsController.ChooseSupplierToDrop();
            if(sol_w.size() == 0){
                bool = false;
                break;
            }
            System.out.println("\nWould you like to drop another Item? ");
            System.out.println("Enter 'yes' or 'no':");
            p = reader.next();
        }
        if(!bool) System.out.println("\nYou dropped all the Items from all the Sites ! ");

        return bool;
    }
    public static void changeSites(String area) throws SQLException {

        JsonObject s = SolutionsController.ChooseSupplierToDrop();

        System.out.println("\nWhich Supplier you want to replace?\n");

        String site_answer = CreateTransportation.printToUser(s.size(), s);

        CreateTransportation.chooseSite(area);

        SolutionsController.replace_documents(site_answer);
    }
    public static boolean changeTruck() throws SQLException {

        JsonObject j = new JsonObject();

        JsonObject sol_w = SolutionsController.getAvailableTruck();
        if (sol_w.size() == 0) {
            System.out.println("There is no available Trucks for this transportation, ");
            System.out.println("please choose other solution");
            return false;
        }
        String s = chooseTruckToReplace(sol_w);

        j.addProperty("Truck", s);

        String d = CreateTransportation.chooseDriver(s);

        if (d == null){
            System.out.println("There is no available Drivers for this transportation!\n");
            System.out.println("please choose other solution!");
            return false;
        }
        j.addProperty("Driver", d);

        SolutionsController.replace_truck_and_driver(j);
        return true;
    }
}
