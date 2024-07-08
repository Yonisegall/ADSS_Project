package Presentation;

import Domain.Controllers.DataController;
import Domain.Controllers.TransportationController;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.Scanner;

public class CreateTransportation {
    private static JsonObject new_json = new JsonObject();

    /**
     * Create new Transport with the Inputs from the user
     */
    public static void createTransport() throws SQLException {

        Scanner reader = new Scanner(System.in);

        String answer = "yes";

        while (answer.equals("yes")) {

            System.out.println("\nPlease enter the address of the source place of the transportation:");
            new_json.addProperty("Source", reader.nextLine());

            System.out.println("\nPlease enter the date of the transportation:");
            new_json.addProperty("Date", reader.nextLine());

            System.out.println("\nPlease enter the leaving time of the transportation:");
            new_json.addProperty("Leaving time", reader.nextLine());

            String truck = ChooseTruck();

            if (truck == null) {
                System.out.println("Sorry but we can't arrange the transport, " +
                                   "because there are no truck that available");
                return;
            }

            String driver = chooseDriver(truck);

            if (driver == null) {
                System.out.println("Sorry but we can't arrange the transport, " +
                                   "because there are no driver that available");
                return;
            }

            new_json.addProperty("Truck", truck);
            new_json.addProperty("Driver", driver);

            TransportationController.createTransport(new_json);

            String area = chooseArea();

            String a = "yes";


            while (a.equals("yes")) {

                chooseSite(area);
                System.out.println("\nDo you want to add another site for the transport? Enter 'yes' or 'no'.");
                a = reader.nextLine();
                while (!a.equals("yes") && !a.equals("no")) {
                    System.out.println("\nWrong input!, try again..");
                    System.out.println("\nDo you want to add another site for the transport? Enter 'yes' or 'no'.");
                    a = reader.nextLine();
                }
            }

            int result = TransportationController.checkTransport();

            boolean bool = true;
            //HeadLine Weight Solution
            while (result != 0) {
                switch (result) {
                    case 1 -> {
                        System.out.println("\nThere are still products in the truck!\n");
                        System.out.println("Please continue with the Transportation making\n");
                        System.out.println("Please add more sites to the transportation!\n");

                        chooseSite(area);

                    }
                    case 2 -> {
                        String sol =  ChooseSolution.chooseTypeOfSolution();
                        switch (sol) {

                            case "1" -> ChooseSolution.changeSites(area);
                            case "2" -> {
                                if (!ChooseSolution.changeTruck()) continue;
                            }
                            case "3" -> {
                                bool = ChooseSolution.dropSites();
                            }
                            case "4" -> bool = ChooseSolution.dropItems();

                        }
                    }
                }

                if(!bool) break;
                result = TransportationController.checkTransport();
            }

            if(bool)
                System.out.println("\nTransportation added successfully!");

            else
                System.out.println("\nThe Transportation canceled..");

            System.out.println("\nWould you like to make a new transportation? Enter 'yes' or 'no':");
            answer = reader.nextLine();
        }
    }

    /**
     * Selection methods from the User
     */
    public static String ChooseTruck() throws SQLException {

        System.out.println("\nPlease choose an Truck:");

        JsonObject new_trucks = DataController.ChooseTruck();

        if (new_trucks.size() == 0)
            return null;

        return printToUser(new_trucks.size(), new_trucks);
    }
    public static String chooseDriver(String truck) throws SQLException {

        System.out.println("\nPlease choose a Driver:");

        String truckLicence = truck.substring(43, 44);

        JsonObject new_drivers = DataController.ChooseDriver(truckLicence);

        if (new_drivers.size() == 0)
            return null;

        return printToUser(new_drivers.size(), new_drivers);
    }
    public static String chooseArea() throws SQLException {

        System.out.println("\nPlease choose a Shipping area:");

        JsonObject new_areas = DataController.ChooseArea();

        return printToUser(new_areas.size(), new_areas);
    }
    public static String chooseTypeOfSite(String area, String type) throws SQLException {

        System.out.println("\nPlease choose a " + type + " :");

        JsonObject new_Site = DataController.ChooseSite(area, type);

        return printToUser(new_Site.size(), new_Site);
    }
    public static void chooseSite(String area) throws SQLException {

        Scanner reader = new Scanner(System.in);

        StringBuilder str_Sup_Sto = new StringBuilder();

        str_Sup_Sto.append("\nPlease choose Supplier or Store:");
        str_Sup_Sto.append("\n'1' - Supplier");
        str_Sup_Sto.append("\n'2' - Store");
        System.out.println(str_Sup_Sto);

        String site = reader.next();
        while (!site.equals("1") && !site.equals("2")) {
            System.out.println("\nWrong input!, try again..\n");
            System.out.println(str_Sup_Sto);
            site = reader.next();
        }

        //HeadLine Supplier / Store choose
        switch (site) {
            case "1" -> {
                String supplier = chooseTypeOfSite(area, "Supplier");
                String it = "yes";
                while (it.equals("yes")) {
                    addItems();

                    System.out.println("\nItem added successfully!");

                    System.out.println("\nDo you want to add another item? Enter 'yes' or 'no'.");
                    it = reader.next();
                    while (!it.equals("yes") && !it.equals("no")) {
                        System.out.println("\nWrong input!, try again..");
                        System.out.println("\nDo you want to add another item? Enter 'yes' or 'no'.");
                        it = reader.next();
                    }
                }
                createDocument(supplier, "Supplier", area);
            }

            case "2" -> {
                JsonObject Item_j = new JsonObject();

                String store = chooseTypeOfSite(area, "Store");
                String it = "yes";
                while (it.equals("yes")) {

                    JsonObject j = TransportationController.chooseItems();

                    if (j.size() != 0) {
                        System.out.println();

                        String item = printToUser(j.size(), j);

                        int amount = TransportationController.amountOfItems(item);

                        System.out.println("\nPlease enter the amount: (The maximum products you can order is - " + amount + ").");
                        int a = reader.nextInt();

                        while (a > amount) {
                            System.out.println("\nYou tried to order too much of this product!, try again..");
                            System.out.println("\nPlease enter the amount: (The maximum products you can order is - " + amount + ").");
                            a = reader.nextInt();
                        }
                        Item_j.addProperty("Item", item);
                        Item_j.addProperty("Amount", String.valueOf(a));

                        TransportationController.AddItem(Item_j, "Store");

                        System.out.println("\nItem added successfully!");
                        System.out.println("\nDo you want to add another item? Enter 'yes' or 'no'.");
                        it = reader.next();
                        while (!it.equals("yes") && !it.equals("no")) {
                            System.out.println("\nWrong input!, try again..");
                            System.out.println("\nDo you want to add another item? Enter 'yes' or 'no'.");
                            it = reader.next();
                        }
                    }
                    else {
                        System.out.println("There are no more product in the truck!");
                        break;
                    }
                }
                createDocument(store, "Store", area);
            }
        }
    }


    /**
     * Add Item from the user
     */
    public static void addItems() {
        JsonObject j = new JsonObject();

        Scanner reader = new Scanner(System.in);

        System.out.println("\nPlease enter details of the item:");

        System.out.println("\nName: ");
        j.addProperty("Name", reader.nextLine());

        while (true) {
            System.out.println("\nWeight: ");
            String Weight = reader.nextLine();
            try {
                Double.parseDouble(Weight);
                j.addProperty("Weight", Weight);
                break;
            } catch (Exception e) {
                System.out.println("\nWrong input! try again..");
            }
        }

        while (true) {
            System.out.println("\nAmount: ");
            String Amount = reader.nextLine();
            try {
                Integer.parseInt(Amount);
                j.addProperty("Amount", Amount);
                break;
            } catch (Exception e) {
                System.out.println("\nWrong input! try again..");
            }
        }
        TransportationController.AddItem(j, "Supplier");
    }
    /**
     * Creation methods
     */
    public static void createDocument(String site, String type, String area) throws SQLException {
        JsonObject j = new JsonObject();

        j.addProperty("Site", site);
        j.addProperty("Type", type);
        j.addProperty("Area", area);

        TransportationController.AddDocument(j);
    }
    /**
     * Prints to the user the possible choices depending on the stage he is in
     */
    public static String printToUser(int size, JsonObject j) {
        Scanner reader = new Scanner(System.in);

        String answer;

        while (true) {
            for (int i = 1; i <= size; i++) {
                System.out.println("press " + i + " for - " + j.get(String.valueOf(i)));
            }

            answer = reader.nextLine();

            try {
                Integer.parseInt(answer);
                if (Integer.parseInt(answer) > size || Integer.parseInt(answer) <= 0) {
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("\nWrong input! try again..");
            }
        }
        return j.get(answer).getAsString();
    }
}
