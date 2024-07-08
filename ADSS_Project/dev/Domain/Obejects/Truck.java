package Domain.Obejects;

public class Truck {
    private String licence_number;
    private String licence_level;
    private double net_weight;
    private double max_weight;
    private String Status;


    /**
     * Constructor of Truck
     */
    public Truck(String LicenceNum, String LicenceLevel, double net_weight, double max_weight, String s) {
        this.licence_number = LicenceNum;
        this.licence_level = LicenceLevel;
        this.net_weight = net_weight;
        this.max_weight = max_weight;
        this.Status = s;
    }

    /**
     * Getter net_weight
     */
    public double getNet_weight() {
        return net_weight;
    }
    /**
     * Getter max_weight
     */
    public double getMax_weight() {
        return max_weight;
    }
    /**
     * Getter licence_number
     */
    public String getLicence_number() {
        return licence_number;
    }
    public String getLicence_level() {
        return licence_level;
    }
    public String getStatus() {
        return Status;
    }


    public void setStatus(String status) {
        Status = status;
    }
    /**
     * @return String represent of the Truck
     */
    public String to_String(){
        return ("Licence Number: " + licence_number + ", Licence Level: "
                + licence_level + ", Max weight: " + max_weight + ".");
    }
}
