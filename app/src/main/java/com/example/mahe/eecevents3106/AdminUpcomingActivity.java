package com.example.mahe.eecevents3106;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mahe.eecevents3106.Classes.StringDeclarations;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

import static com.example.mahe.eecevents3106.Classes.StringDeclarations.*;

public class AdminUpcomingActivity extends AppCompatActivity {

    int depme;

    TextView tv_depname;
    EditText et_evenam, et_evedat, eet_evedesc;
    Button btn_post;

    String adminID;
    //String department;
    String event;
    String dateGet;
    String descrip;


    DatabaseReference mDatabase;
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_upcoming);

        tv_depname = findViewById(R.id.tv_deptnam);
        et_evenam = findViewById(R.id.et_evnam_adup);
        et_evedat = findViewById(R.id.et_evdat_adup);
        eet_evedesc = findViewById(R.id.et_evdesc_adup);
        btn_post = findViewById(R.id.btn_sub_adup);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Stencil.ttf");
        tv_depname.setTypeface(typeface);


        et_evedat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dateSET();
            }
        });




        Intent inty = getIntent();

        depme = inty.getIntExtra("DeptVal",0);

        switch(depme)
        {
            case 1:
                tv_depname.setText(CAR);

                adminID = auto_Admin;
                //department = "Automobile";

                break;
            case 2:
                tv_depname.setText(SKYSCRAPER);

                adminID = civil_Admin;
                //department = "Civil";

                break;
            case 3:
                tv_depname.setText(COMPY);

                adminID = cse_Admin;
                //department = "CSE";

                break;
            case 4:
                tv_depname.setText(COMMUNICATION);

                adminID = ece_Admin;
                //department = "ECE";

                break;
            case 5:
                tv_depname.setText(CURRENT);

                adminID = eee_Admin;
                //department = "EEE";

                break;
            case 6:
                tv_depname.setText(INSTRUMENTATION);

                adminID = eie_Admin;
                //department = "EIE";

                break;
            case 7:
                tv_depname.setText(TECHNOLOGY);

                adminID = it_Admin;
                //department = "IT";

                break;
            case 8:
                tv_depname.setText(CAD);

                adminID = mech_Admin;
                //department = "Mechanical";

                break;
            case 9:
                tv_depname.setText(VUL);

                adminID = vul_Admin;
                //department = "Vulcans";

                break;
            case 10:
                tv_depname.setText(APPLICATION);

                adminID = mca_Admin;
                //department = "MCA";

                break;
            case 11:
                tv_depname.setText(BUSINESS);

                adminID = mba_Admin;
                //department = "MBA";

                break;
        }

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                event = et_evenam.getText().toString();
                descrip = eet_evedesc.getText().toString();

                if(event == null)
                {
                    event = "NP UPCOMING EVENTS";
                }
                if(descrip == null)
                {
                    descrip = "NO DESCRIPTION";
                }
                if(dateGet == null)
                {
                    dateGet = "N/A";
                }


                postval(adminID, dateGet, descrip, event);


            }
        });
    }

    private void postval(String adminID, String dateGet, String descrip, String event)
    {

        mDatabase.child(dep).child(adminID).child("Next Event: ").child("Event: ").setValue(event);
        mDatabase.child(dep).child(adminID).child("Next Event: ").child("Date: ").setValue(dateGet);
        mDatabase.child(dep).child(adminID).child("Next Event: ").child("Description: ").setValue(descrip);



        startActivity(new Intent(AdminUpcomingActivity.this, AdminActivity.class));
        finish();

    }


    private void dateSET() {

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
                        et_evedat.setText(dateGet);

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {


        startActivity(new Intent(AdminUpcomingActivity.this, AdminActivity.class));
        finish();

    }
}
