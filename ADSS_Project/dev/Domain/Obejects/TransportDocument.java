package Domain.Obejects;

public class TransportDocument {
    private String Status;
    private int ID;
    private String Details;

    public TransportDocument(String s, String d, int id){
        this.Status = s;
        this.Details = d;
        this.ID = id;
    }

    public String getStatus() {
        return Status;
    }
    public int getID() {
        return ID;
    }
    public String getDetails() {
        return Details;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String to_String() {
        StringBuilder new_s = new StringBuilder();
        String s = "Transport number: " + ID;
        new_s.append(s);
        s = "\nStatus: " + Status + "\n";;
        new_s.append(s);
        s = Details;
        new_s.append(s);

        return new_s.toString();
    }
}
