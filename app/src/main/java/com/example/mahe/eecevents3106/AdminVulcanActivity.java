package com.example.mahe.eecevents3106;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahe.eecevents3106.Classes.StringDeclarations;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static com.example.mahe.eecevents3106.Classes.StringDeclarations.FLAG_MCA;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.FLAG_VULC;

public class AdminVulcanActivity extends AppCompatActivity{

    EditText vulcanName;
    Spinner vulcanDept, vulcanYear;
    Button addMem, allAllMem;
    TextView vulHeader;

    RelativeLayout relativeLayout;

    DatabaseReference mDatabase;
    FirebaseStorage storage;
    StorageReference storageReference;
    //String event;

    String dep = "Department";

    String vul_dep;
    String adminID;
    String vul_name;
    String vul_year;

    List<String> vulName = new ArrayList<>();
    List<String> vulDept = new ArrayList<>();
    List<String> vulYear = new ArrayList<>();

    List<String> departmentSelect = new ArrayList<>();
    List<String> yearSelect = new ArrayList<>();

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vulcan_mem);

        vulcanName  = findViewById(R.id.editNameVulcan);
        vulcanDept = findViewById(R.id.editDeptVulcan);
        vulHeader = findViewById(R.id.tvVULADMIN);
        addMem = findViewById(R.id.vulMemBTN);
        allAllMem = findViewById(R.id.vulAddMemBTN);
        vulcanYear = findViewById(R.id.spinYearSelec);
        relativeLayout = findViewById(R.id.relLay);


        yearSelect.add("First Year");
        yearSelect.add("Second Year");
        yearSelect.add("Third Year");
        yearSelect.add("Fourth Year");

        ArrayAdapter adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yearSelect);
        // Drop down layout style - list view with radio button
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        vulcanYear.setAdapter(adapter1);
        vulcanYear.setSelected(false);
        vulcanYear.setSelection(0,true);

        vulcanYear.setOnItemSelectedListener(new VulcanYearClass());

        departmentSelect.add(StringDeclarations.CAR);
        departmentSelect.add(StringDeclarations.SKYSCRAPER);
        departmentSelect.add(StringDeclarations.COMPY);
        departmentSelect.add(StringDeclarations.COMMUNICATION);
        departmentSelect.add(StringDeclarations.CURRENT);
        departmentSelect.add(StringDeclarations.INSTRUMENTATION);
        departmentSelect.add(StringDeclarations.TECHNOLOGY);
        departmentSelect.add(StringDeclarations.CAD);
        departmentSelect.add(StringDeclarations.APPLICATION);
        departmentSelect.add(StringDeclarations.BUSINESS);

        vulcanDept.setOnItemSelectedListener(new VulcanDeptClass());

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departmentSelect);
        // Drop down layout style - list view with radio button
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        vulcanDept.setAdapter(adapter);
        vulcanDept.setSelected(false);
        vulcanDept.setSelection(0,true);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Stencil.ttf");
        vulHeader.setTypeface(typeface);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        addMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adminID = StringDeclarations.vul_mem_Admin;
                vul_name  = vulcanName.getText().toString();
                //vul_dep  = vulcanDept.getText().toString();

                vulName.add(i, vul_name);
                vulDept.add(i, vul_dep);
                vulYear.add(i, vul_year);

                Log.v("Vulcan Name"+i, vulName.get(i));
                Log.v("Vulcan Department"+i, vulDept.get(i));
                Log.v("Vulcan Year"+i, vulYear.get(i));

                i++;


                if(vulName != null && vulDept != null && vul_year != null)
                {

                    Toast.makeText(AdminVulcanActivity.this, "Member Added Successfully", Toast.LENGTH_SHORT).show();
                }

                vulcanName.getText().clear();

            }
        });

        allAllMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences sharedPreferences = getSharedPreferences(FLAG_VULC, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                updateData(adminID, vulName, vulDept, vulYear);


            }
        });
    }

    private void updateData(String admin_ID, List<String> vu_Name, List<String> vu_Dept, List<String> vu_Year)
    {

        SharedPreferences sharedPreferences   = getSharedPreferences(StringDeclarations.tot_val_vul, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        int tot_size = vu_Name.size();

        for(int j=0; j< tot_size ;j++)
        {
            mDatabase.child(dep).child(admin_ID).child("Vulcan "+j+":").setValue(vu_Name.get(j));
            mDatabase.child(dep).child(admin_ID).child("Department "+j+":").setValue(vu_Dept.get(j));
            mDatabase.child(dep).child(admin_ID).child("Year "+j+":").setValue(vu_Year.get(j));
            mDatabase.child(dep).child(admin_ID).child("No of members: ").setValue(tot_size);
        }

        editor.putInt(StringDeclarations.val_use_vul, tot_size);
        editor.apply();

        startActivity(new Intent(AdminVulcanActivity.this, HomeActivity.class));
        finish();

    }


    private class VulcanYearClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            vul_year = adapterView.getItemAtPosition(i).toString();

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private class VulcanDeptClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            vul_dep  = adapterView.getItemAtPosition(i).toString();

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
