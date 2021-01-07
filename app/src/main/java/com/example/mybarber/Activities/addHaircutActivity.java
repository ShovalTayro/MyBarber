package com.example.mybarber.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mybarber.R;
import com.example.mybarber.fireBase.hairCutsFB;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;

import io.grpc.Context;

public class addHaircutActivity extends AppCompatActivity {
    //xml characterises
    private EditText haircut;
    private EditText price;
    private Button add;
    private Button back;
    private Button add_picture;
    private ImageView picture;
    //uri for picture
    private Uri image_Selected = null;

    //FB dataBase
    private FirebaseAuth mAuth;
    private StorageReference msStorageReference;
    String fName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_haircut);
        mAuth = FirebaseAuth.getInstance();
        msStorageReference = FirebaseStorage.getInstance().getReference("images");
        findViews();
        myActivate();
    }

    //set buttons &the text view
    private void findViews() {
        haircut = findViewById(R.id.haircut_editText);
        price = findViewById(R.id.price_editText);
        add = findViewById(R.id.add_button);
        back = findViewById(R.id.back_button);
        add_picture = findViewById(R.id.add_picture);

        picture = findViewById(R.id.picture);

        Intent iin = getIntent();
        //get extras from register/login activity
        Bundle b = iin.getExtras();
        if (b != null) {
            fName = (String) b.get("firstName");
        }
    }

    //activate views & buttons
    private void myActivate() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addHaircutActivity.this, allHaircutActivity.class));
            }
        });

        add_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_picture();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create the info as a string without any leading and trailing white space
                String haircutName = haircut.getText().toString().trim();
                String priceNum = price.getText().toString().trim();
                //if info is missing
                if (TextUtils.isEmpty(haircutName) || TextUtils.isEmpty(priceNum) || image_Selected == null) {
                    haircut.setError("Some fields are missing");
                    return;
                }
                //create a new haircut and add to the Storage
                else {
                    StorageReference storageReference2 = msStorageReference.child(haircutName + "." + getFileExtension(image_Selected));
                    storageReference2.putFile(image_Selected)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                                        @Override
                                        public void onSuccess(Uri uri) {
                                            final Uri download_url = uri;
                                            hairCutsFB haircut = new hairCutsFB();
                                            haircut.addHairCut(haircutName, priceNum, download_url.toString());
                                        }
                                    });
                                }
                            });
                    ////////check if exist//////////
                    Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(addHaircutActivity.this, allHaircutActivity.class).putExtra("massage", fName));
                }
            }
        });
    }

    private void choose_picture() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            image_Selected = data.getData();
            @SuppressLint("SimpleDateFormat") String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timestamp + "." + getFileExtension(image_Selected);
            Log.d("tag", "onActivityResult : Gallery Image Uri :" + imageFileName);
            picture.setImageURI(image_Selected);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}