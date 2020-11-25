package com.example.mybarber.fireBase;

import com.example.mybarber.Objects.Owners;
import com.google.firebase.database.DatabaseReference;

public class AdminFB extends initializeFB
{
    public void addAdminToDB(String firstName,String lastName,String phoneNumber,String mail,String pass)
    {
        Owners user = new Owners(firstName,lastName,mail,phoneNumber,pass);
        ref.child("Admin").child(phoneNumber).setValue(user);
    }
    public DatabaseReference getAdmin()
    {
        return ref.child("Admin");
    }
}
