package com.example.mahe.eecevents3106.NavActivities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mahe.eecevents3106.Department.AutomobileActivity;
import com.example.mahe.eecevents3106.Department.CSEActivity;
import com.example.mahe.eecevents3106.Department.CivilActivity;
import com.example.mahe.eecevents3106.Department.ECEActivity;
import com.example.mahe.eecevents3106.Department.EEEActivity;
import com.example.mahe.eecevents3106.Department.EIEActivity;
import com.example.mahe.eecevents3106.Department.ITActivity;
import com.example.mahe.eecevents3106.Department.MBAActivity;
import com.example.mahe.eecevents3106.Department.MCAActivity;
import com.example.mahe.eecevents3106.Department.MechanicalActivity;
import com.example.mahe.eecevents3106.HomeActivity;
import com.example.mahe.eecevents3106.R;

public class DeptEventActivity extends AppCompatActivity {

    Button auto, cse, it, eee, ece, mech, eie, civil, mba, mca;
    TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_event);

        auto = findViewById(R.id.btnAUTO);
        civil = findViewById(R.id.btnCIVIL);
        cse = findViewById(R.id.btnCSE);
        ece = findViewById(R.id.btnECE);
        eee = findViewById(R.id.btnEEE);
        eie = findViewById(R.id.btnEIE);
        it = findViewById(R.id.btnIT);
        mech = findViewById(R.id.btnMECH);
        mba  = findViewById(R.id.btnMBA);
        mca = findViewById(R.id.btnMCA);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Capture_it.ttf");

        heading = findViewById(R.id.textHeading);
        heading.setTypeface(font);


        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeptEventActivity.this, AutomobileActivity.class));
                finish();
            }
        });

        civil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeptEventActivity.this, CivilActivity.class));
                finish();
            }
        });

        cse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeptEventActivity.this, CSEActivity.class));
                finish();
            }
        });

        ece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeptEventActivity.this, ECEActivity.class));
                finish();
            }
        });

        eie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeptEventActivity.this, EIEActivity.class));
                finish();
            }
        });

        eee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeptEventActivity.this, EEEActivity.class));
                finish();
            }
        });

        it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeptEventActivity.this, ITActivity.class));
                finish();
            }
        });

        mech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeptEventActivity.this, MechanicalActivity.class));
                finish();
            }
        });

        mba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(DeptEventActivity.this, MBAActivity.class));
                finish();
            }
        });

        mca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(DeptEventActivity.this, MCAActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(DeptEventActivity.this, HomeActivity.class));
        finish();
    }
}
