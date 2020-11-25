package com.example.mybarber.fireBase;

import com.example.mybarber.Objects.hairCut;
import com.google.firebase.database.DatabaseReference;

public class hairCutsFB extends initializeFB
{
    public void addHairCut(String haircut , String price)
    {
        hairCut HC = new hairCut(haircut,price);
        ref.child("haircuts").child(haircut).setValue(HC);
    }

    public DatabaseReference allHairCuts()
    {
        return ref.child("haircuts");
    }
    public DatabaseReference getSpecHaircut(String haircut)
    {
        return ref.child("haircuts").child(haircut);
    }

}
