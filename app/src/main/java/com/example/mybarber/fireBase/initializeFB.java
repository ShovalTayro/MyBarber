package com.example.mybarber.fireBase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class initializeFB {

    protected DatabaseReference ref;
    //get root's fireBase
    public initializeFB() {
        ref = FirebaseDatabase.getInstance().getReference();
    }
}
