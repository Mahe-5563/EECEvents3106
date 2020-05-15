package com.example.mahe.eecevents3106;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahe.eecevents3106.Classes.StringDeclarations;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdminSportsActivity extends AppCompatActivity {


    EditText eventName, eventDate, invi;
    Button postevent, addevent;
    TextView sports;
    RelativeLayout relativeLayout;

    String dateGet; //
    String adminID; // ID for the firebase Key
    String eventN;
    String  inv;

    DatabaseReference mDatabase;
    FirebaseStorage storage;
    StorageReference storageReference;
    //String event;

    String dep = "Department";


    List<String> eveName = new ArrayList<>();
    List<String> eveDate = new ArrayList<>();

    int i = 0;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sports);

        eventName  = findViewById(R.id.editEventNameSport);
        eventDate = findViewById(R.id.dateSelectSport);
        postevent = findViewById(R.id.BTNeventPostSport);
        addevent = findViewById(R.id.BtnAddEvents);
        sports = findViewById(R.id.DeptSport);
        invi = findViewById(R.id.editSportInvitation);
        relativeLayout = findViewById(R.id.relLLAY);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Stencil.ttf");
        sports.setText("Sports Department");
        sports.setTypeface(typeface);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateSet();

            }
        });


        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adminID = StringDeclarations.sport_Admin;
                eventN  = eventName.getText().toString();
                inv = invi.getText().toString();

                eveName.add(i, eventN);
                eveDate.add(i, dateGet);

                Log.v("Event"+i, eveName.get(i));
                Log.v("Date"+i, eveDate.get(i));

                i++;

                if(eveName != null && eveDate != null)
                {

                    Toast.makeText(AdminSportsActivity.this, "Values Added", Toast.LENGTH_SHORT).show();
                }

                eventName.getText().clear();
                eventDate.getText().clear();

            }
        });

        postevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((eveName != null) && (eveDate != null) && (inv != null))
                {
                    updateData(adminID, eveName, eveDate, inv);
                }
                else
                {
                    Snackbar snackbar = Snackbar.make(relativeLayout, "Please enter The Sports Events details", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });


    }


    private void updateData(String admin_ID, List<String> event, List<String> date, String invitation) {

        SharedPreferences sharedPreferences   = getSharedPreferences(StringDeclarations.tot_val, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int tot_size = eveName.size();

        for(int j=0; j< tot_size ;j++)
        {
            mDatabase.child(dep).child(admin_ID).child("Event"+j+":").setValue(event.get(j));
            mDatabase.child(dep).child(admin_ID).child("Date"+j+":").setValue(date.get(j));
            mDatabase.child(dep).child(admin_ID).child("Invitation:").setValue(invitation);
        }

        editor.putInt(StringDeclarations.val_use, tot_size);
        editor.apply();

        Toast.makeText(this, "Values Posted Successfully", Toast.LENGTH_SHORT).show();


        startActivity(new Intent(AdminSportsActivity.this, HomeActivity.class));
        finish();

    }

    private void dateSet() {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateGet = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        eventDate.setText(dateGet);

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("WARNING!")
                .setMessage("Are you sure you want to Quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        startActivity(new Intent(AdminSportsActivity.this, HomeActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                    }
                });

        AlertDialog al = builder.create();
        al.show();
    }
}
