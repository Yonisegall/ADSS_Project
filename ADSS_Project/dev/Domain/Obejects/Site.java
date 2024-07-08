package Domain.Obejects;

import Domain.Controllers.DataController;

public class Site {

    private String name;
    private String address;
    private String phone;
    private String contact;
    private String shipping_area;
    private String type;
    private int Site_ID;

    /**
     * Constructor of Transport
     */
    public Site(String name, String address, String phone, int id,
                String contact, String shipping_area, String type) {
        this.type = type;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.Site_ID = id;
        this.contact = contact;
        this.shipping_area = shipping_area;
    }

    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getPhone() {
        return phone;
    }
    public String getContact() {
        return contact;
    }
    public String getShipping_area() {
        return shipping_area;
    }
    public String getType() {
        return type;
    }




    /**
     * @return String represent of the Site
     */
    public String to_string(){
        return ("Address: " + address + ", Name: " + name + ", Shipping area: " +
                "" + shipping_area + ", Type: " + getType() + ".");
    }

}
