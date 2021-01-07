package com.example.mybarber.fireBase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class initializeFB {
    protected DatabaseReference ref;
    //get root's fireBase
    public initializeFB() {
        ref = FirebaseDatabase.getInstance().getReference();
    }
}
