package com.example.mybarber.Objects;

public class Client {

    //variables
    String fName;
    String lName;
    String Email;
    String phone;
    String pass;

    //constructors
    public Client() {
    }
    public Client(String fName, String lName, String email, String phone,String pass) {
        this.fName = fName;
        this.pass= pass;
        this.lName = lName;
        this.Email = email;
        this.phone = phone;
    }

    //getters & setters
    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
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
