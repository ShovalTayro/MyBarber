package com.example.mybarber.fireBase;

import com.example.mybarber.Objects.hairCut;
import com.example.mybarber.Objects.message;
import com.google.firebase.database.DatabaseReference;

public class messageFB extends initializeFB {
    static int id = 0;

    public void addMessage(String name, String date, String hour, String action) {
        message mes = new message(name, date, hour, action, id);
        ref.child("messages").child(""+id).setValue(mes);
        id++;
    }
    public DatabaseReference allMessages() {
        return ref.child("messages");
    }
}
