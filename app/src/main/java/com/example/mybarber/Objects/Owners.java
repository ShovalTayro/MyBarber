package com.example.mybarber.Objects;

public class Owners {

    //variables
    String fName ;
    String lName ;
    String Email ;
    String phone ;
    String pass ;
    String address;

    //constructors
    public Owners() {
    }
    public Owners(String fName, String lName, String email, String phone, String pass, String address ) {
        this.fName = fName;
        this.lName = lName;
        this.Email = email;
        this.pass = pass;
        this.phone = phone;
        this.address = address;
    }

    //getters & setters
    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setPhone(String phone) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) { this.address = address;}
}
