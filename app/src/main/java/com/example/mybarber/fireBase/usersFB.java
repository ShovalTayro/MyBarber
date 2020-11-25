package com.example.mybarber.fireBase;

import com.example.mybarber.Objects.Client;
import com.google.firebase.database.DatabaseReference;

public class usersFB extends initializeFB
{
    public void addUserToDB(String firstName,String lastName,String phoneNumber,String mail,String pass)
    {
        Client user = new Client(firstName,lastName,mail,phoneNumber,pass);
        ref.child("users").child(phoneNumber).setValue(user);
    }
    public DatabaseReference getAllusers()
    {
        return ref.child("users");
    }
    public DatabaseReference getUserByPhone(String phone)
    {
        return ref.child("users").child(phone);
    }
}
