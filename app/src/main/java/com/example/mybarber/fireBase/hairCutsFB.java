package com.example.mybarber.fireBase;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mybarber.Objects.hairCut;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.logging.Handler;

public class hairCutsFB extends initializeFB {

    public void addHairCut(String haircut,String price, String url)
    {
        hairCut HC = new hairCut(haircut ,price , url );
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
