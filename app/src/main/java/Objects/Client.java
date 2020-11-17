package Objects;

public class Client {

    String fName ;
    String lName ;

    String Email ;
    String phone ;

    public Client()
    {

    }

    public Client(String fName, String lName,  String email, String phone) {
        this.fName = fName;
        this.lName = lName;

        this.Email = email;
        this.phone = phone;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return phone;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }


    public void setEmail(String email) {
        Email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
