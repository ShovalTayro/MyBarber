package com.example.mybarber.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybarber.Adapter.appointmentClientAdapter;
import com.example.mybarber.Adapter.messageAdapter;
import com.example.mybarber.Objects.Appointment;
import com.example.mybarber.Objects.message;
import com.example.mybarber.R;
import com.example.mybarber.fireBase.appointmentFB;
import com.example.mybarber.fireBase.messageFB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class allMessageActivity extends AppCompatActivity {
    private Button back;
    private FirebaseAuth fa;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<message> messageList;
    private String fName;
    private com.example.mybarber.Adapter.messageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_messages);
        Intent iin= getIntent();
        //get extras from register/login activity
        Bundle b = iin.getExtras();
        if(b!=null) {
            fName =(String) b.get("firstName");
        }
        findViews();
        myActivate();
    }

    //set buttons &the text view
    private void findViews() {
        back = findViewById(R.id.backButton);
        recyclerView = findViewById(R.id.recV3);
    }

    //activate views &buttons
    private void myActivate() {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        messageList = new ArrayList<>();
        messageAdapter = new messageAdapter(allMessageActivity.this);
        recyclerView.setAdapter(messageAdapter);
        messageFB message = new messageFB();
        //get all appointments from FB
        DatabaseReference dr = message.allMessages();
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            //update appointments
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //add all appointments to list
                for (DataSnapshot s : snapshot.getChildren()) {
                    messageList.add(s.getValue(message.class));
                }
                messageAdapter.setMessagesList(messageList);
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(allMessageActivity.this, managerActivity.class);
                i.putExtra("firstName", fName);
                startActivity(i);
            }
        });
    }
}
