package Domain.Obejects;

public class Item {
    private String name;
    private double weight;


    /**
     * Constructor of Item
     */
    public Item(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    /**
     * @return the name of the Item
     */
    public String getName() {
        return name;
    }
    /**
     * @return the Weight of the Item
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @return String represent of the Item
     */
    public String to_string(){
        return "Name: " + name + ", Weight: " + weight;
    }
}
