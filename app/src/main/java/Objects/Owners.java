package Objects;

public class Owners
{
    String fName ;
    String lName ;
    String user  ;
    String Email ;
    String phone ;
    int incomes;

    public Owners() {
    }

    public Owners(String fName, String lName, String user, String email, String phone, int incomes) {
        this.fName = fName;
        this.lName = lName;
        this.user =  user;
        this.Email = email;
        this.phone = phone;
        this.incomes = incomes;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIncomes(int incomes) {
        this.incomes += incomes;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getUser() {
        return user;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return phone;
    }

    public int getIncomes() {
        return incomes;
    }
}
