package com.example.mybarber.Activities;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mybarber.R;
import com.example.mybarber.fireBase.AdminFB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.GeoPoint;
import com.google.type.LatLng;

import java.io.IOException;
import java.util.List;

public class profileActivity extends AppCompatActivity {
    private Button logout;
    private Button chooseTurn;
    private FirebaseAuth firebaseAuth;
    private Button history;
    private Button gallery;
    private ImageButton contactUs;
    private ImageButton waze;
    private TextView welcome;
    String fName, lName;
    String phone;
    String mangerName, managerPhone, managerEmail,managerAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_loged_activity);
        firebaseAuth = firebaseAuth.getInstance();
        Intent iin = getIntent();
        //get extras from register/login activity
        Bundle b = iin.getExtras();
        if (b != null) {
            fName = (String) b.get("firstName");
            lName = (String) b.get("lastName");
            phone = (String) b.get("phone");
        }
        findViews();
        myActivate();
    }

    //set buttons &the text view
    private void findViews() {
        welcome = findViewById(R.id.welcome);
        // privateArea = findViewById(R.id.privateArea);
        chooseTurn = findViewById(R.id.chooseTurn);
        history = findViewById(R.id.history);
        contactUs = findViewById(R.id.contactUsButton);
        logout = findViewById(R.id.logout_button);
        waze = findViewById(R.id.waze);
        gallery = findViewById(R.id.gallery);
    }

    //activate views &buttons
    private void myActivate() {
        //add user name to screen
        welcome.append(fName);

        logout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(profileActivity.this, MainActivity.class));
            }
        }));
        /*
        privateArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to private area
                //Intent i = new Intent(profileActivity.this, registerActivity.class);
                // i.putExtra("user_id", user.getUid());
               // startActivity(i);
            }
        });
        */
        chooseTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(profileActivity.this, selectAppointmentActivity.class);
                i.putExtra("firstName", fName);
                i.putExtra("lastName", lName);
                i.putExtra("phone", phone);
                startActivity(i);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(profileActivity.this, appointmentsHistoryActivity.class);
                i.putExtra("firstName", fName);
                i.putExtra("lastName", lName);
                i.putExtra("phone", phone);
                startActivity(i);
            }
        });
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Admin").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot s : snapshot.getChildren()) {

                            mangerName = s.child("fName").getValue().toString() + " " + s.child("lName").getValue().toString();
                            managerPhone = s.child("phone").getValue().toString();
                            managerEmail = s.child("email").getValue().toString();
                            managerAddress = s.child("address").getValue().toString();
                        }
                        AlertDialog alertDialog = new AlertDialog.Builder(profileActivity.this).create();
                        alertDialog.setTitle("Contact Us");
                        alertDialog.setMessage("Barber: " + mangerName
                                + "\nPhone: " + managerPhone
                                + "\nEmail: " + managerEmail
                                + "\nAddress: " + managerAddress);
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        waze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Admin").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot s : snapshot.getChildren()) {
                            managerAddress = s.child("address").getValue().toString();
                        }
                        Geocoder coder = new Geocoder(profileActivity.this);
                        List<Address> address;
                        GeoPoint p = null;
                        try {
                            address = coder.getFromLocationName(managerAddress, 5);
                            Address location = address.get(0);
                            location.getLatitude();
                            location.getLongitude();
                            p = new GeoPoint(location.getLatitude(), location.getLongitude());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            GeoPoint location = p;
                            String urlWaze = "http://waze.com/ul?ll=" + location.getLatitude() + "," + location.getLongitude()+"&navigate=yes";
                            Intent intentWaze = new Intent(Intent.ACTION_VIEW, Uri.parse(urlWaze));
                            startActivity(intentWaze);
                        } catch (ActivityNotFoundException ex) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(profileActivity.this, galleryActivity.class);
                i.putExtra("firstName", fName);
                i.putExtra("lastName", lName);
                i.putExtra("phone", phone);
                startActivity(i);
            }
        });
    }
}
