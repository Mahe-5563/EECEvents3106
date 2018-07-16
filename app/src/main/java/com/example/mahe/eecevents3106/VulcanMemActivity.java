package com.example.mahe.eecevents3106;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mahe.eecevents3106.Classes.StringDeclarations;
import com.example.mahe.eecevents3106.NavActivities.VulcansActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.mahe.eecevents3106.Classes.StringDeclarations.vul_mem_Admin;

public class VulcanMemActivity extends AppCompatActivity {

    ListView listVulMem;

    TextView vulMemHead;


    String samName;
    String samDept;
    String samYear;
    String samSize;

    String eventVulcanFull;

    int val;
    int tot;

    List<String> vulMembers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vulcan_mem2);

        listVulMem = findViewById(R.id.listVulcanMem);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Department/"+vul_mem_Admin);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Capture_it.ttf");
        vulMemHead = findViewById(R.id.tvHeadVulMem);
        vulMemHead.setTypeface(font);

        SharedPreferences sharedPreferences = getSharedPreferences(StringDeclarations.tot_val_vul, MODE_PRIVATE);

        val = sharedPreferences.getInt(StringDeclarations.val_use_vul, 0);
        Log.v("Size", String.valueOf(val));


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.getValue()!= null){

                    samSize = snapshot.child("No of members: ").getValue().toString();

                    tot = Integer.parseInt(samSize);

                    for(int j = 0; j<tot; j++)
                    {

                        samName  = snapshot.child("Vulcan "+j+":").getValue().toString();
                        samDept = snapshot.child("Department "+j+":").getValue().toString();
                        samYear = snapshot.child("Year "+j+":").getValue().toString();


                        Log.v("Vulcan", samName);
                        Log.v("Department", samDept);
                        Log.v("Year", samYear);

                        eventVulcanFull = "\n"+samName+"\n--"+samDept+"\n--"+samYear+"\n";

                        vulMembers.add(eventVulcanFull);
                    }


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        @SuppressLint("ResourceType")
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                vulMembers);

        listVulMem.setAdapter(arrayAdapter);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(VulcanMemActivity.this, VulcansActivity.class));
        finish();
    }
}
