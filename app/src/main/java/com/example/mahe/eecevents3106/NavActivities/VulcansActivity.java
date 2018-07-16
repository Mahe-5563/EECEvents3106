package com.example.mahe.eecevents3106.NavActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mahe.eecevents3106.HomeActivity;
import com.example.mahe.eecevents3106.R;
import com.example.mahe.eecevents3106.VulcanMemActivity;
import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.example.mahe.eecevents3106.Classes.StringDeclarations.vul_Admin;

public class VulcansActivity extends AppCompatActivity {

    Button vulEve, vulMem;
    TextView swaDesc, talDesc, vulDesc;

    String Swagat, Talentia, Vulcan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vulcans);

        vulEve = findViewById(R.id.BTNVulcanEvents);
        vulMem = findViewById(R.id.BTNVulcanTeam);
        swaDesc = findViewById(R.id.tvVulcDesc2);
        talDesc = findViewById(R.id.tvVulcDesc3);
        vulDesc = findViewById(R.id.tvVulcDesc);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Department");



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.getValue()!= null){

                    Swagat = snapshot.child("swagat").getValue().toString();
                    Talentia = snapshot.child("talentia").getValue().toString();
                    Vulcan = snapshot.child("vul_note").getValue().toString();

                    swaDesc.setText(Swagat);
                    swaDesc.setVisibility(View.VISIBLE);

                    talDesc.setText(Talentia);
                    talDesc.setVisibility(View.VISIBLE);

                    vulDesc.setText(Vulcan);
                    vulDesc.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        vulMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(VulcansActivity.this, VulcanMemActivity.class));
                finish();
            }
        });

        vulEve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(VulcansActivity.this, VulcanEventsActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(VulcansActivity.this, HomeActivity.class));
        finish();
    }
}
