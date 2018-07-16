package com.example.mahe.eecevents3106;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahe.eecevents3106.Classes.StringDeclarations;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import static com.example.mahe.eecevents3106.Classes.StringDeclarations.APPLICATION;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.BUSINESS;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.CAD;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.CAR;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.COMMUNICATION;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.COMPY;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.CURRENT;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.FLAG_AUTO;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.FLAG_CIVIL;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.FLAG_CSE;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.FLAG_ECE;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.FLAG_EEE;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.FLAG_EIE;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.FLAG_IT;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.FLAG_MBA;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.FLAG_MCA;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.FLAG_MECH;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.FLAG_VULC;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.INSTRUMENTATION;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.SKYSCRAPER;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.TECHNOLOGY;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.VUL;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.auto_Admin;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.civil_Admin;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.cse_Admin;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.dep;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.ece_Admin;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.eee_Admin;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.eie_Admin;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.it_Admin;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.mba_Admin;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.mca_Admin;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.mech_Admin;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.punisher;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.vul_Admin;

public class AdminActivity extends AppCompatActivity{

    ImageButton  btnImage;
    EditText eventName, date, des, invite;
    Button postevent; //nextevent;
    TextView deptSelect;
    ScrollView layout;

    private Uri filePath;

    public int attCount = 0;
    public int intCount = 0;
    public int notCount = 0;
    int depme = 0;

    //String Values for Displaying the Deparrtment Name.




    //Firebase Declarations.
    DatabaseReference mDatabase;
    FirebaseStorage storage;
    StorageReference storageReference;

    private final int PICK_IMAGE_REQUEST = 71;

    String randomUUID;

    //String values for getting the String and passing onto the UpdateData function
    String event;
    String dateGet;
    String descrip;
    String invitation;
    String adminID;
    String department;
    String image_url;

    public int flag = 1 ;// to check if the upcoming event is chosenor not.

    //String for Shared Preferences to get the password and determine ehich department. Eventually, displaying that particular department Name.
    String SPDept;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnImage  =  findViewById(R.id.BTNuploadPhoto);
        eventName = findViewById(R.id.editEventName);
        des = findViewById(R.id.editEvenDesciption);
        date = findViewById(R.id.dateSelect);
        deptSelect = findViewById(R.id.DeptSpinner);
        postevent = findViewById(R.id.BTNeventPost);
        invite = findViewById(R.id.editEventInvitation);
        layout = findViewById(R.id.scrollView);
       // nextevent = findViewById(R.id.btn_nextevent);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        /*deptSelect.setOnItemSelectedListener(this);
        deptSelect.setPrompt("Select Department");

        depts.add("Automobile");
        depts.add("Civil");
        depts.add("CSE");
        depts.add("ECE");
        depts.add("EEE");
        depts.add("EIE");
        depts.add("IT");
        depts.add("Mechanical");

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, depts);
        // Drop down layout style - list view with radio button
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        deptSelect.setAdapter(adapter); */

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Stencil.ttf");
        deptSelect.setTypeface(typeface);

        SharedPreferences sharedPreferences = getSharedPreferences(StringDeclarations.ADMINSP, MODE_PRIVATE);

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateSet();

            }
        });

        SPDept =  sharedPreferences.getString(StringDeclarations.PASSWORDY, null);

        /*nextevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this)
                        .setTitle("Next Event")
                        .setMessage("Do you want to add the next event?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                punisher = 1;
                                Intent inty = new Intent(AdminActivity.this, AdminUpcomingActivity.class);
                                inty.putExtra("DeptVal",depme);
                                startActivity(inty);

                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alertDialog  = builder.create();
                alertDialog.show();


            }
        });*/

        if(SPDept != null){

            switch(SPDept){

                case StringDeclarations.AUTOMOBILE:
                    deptSelect.setText(CAR);
                    deptSelect.setVisibility(View.VISIBLE);
                    depme = 1;

                    break;

                case StringDeclarations.CIVIL:
                    deptSelect.setText(SKYSCRAPER);
                    deptSelect.setVisibility(View.VISIBLE);
                    depme = 2;

                    break;

                case StringDeclarations.CSE:
                    deptSelect.setText(COMPY);
                    deptSelect.setVisibility(View.VISIBLE);
                    depme = 3;

                    break;

                case StringDeclarations.ECE:
                    deptSelect.setText(COMMUNICATION);
                    deptSelect.setVisibility(View.VISIBLE);
                    depme = 4;

                    break;

                case StringDeclarations.EEE:
                    deptSelect.setText(CURRENT);
                    deptSelect.setVisibility(View.VISIBLE);
                    depme = 5;

                    break;

                case StringDeclarations.EIE:
                    deptSelect.setText(INSTRUMENTATION);
                    deptSelect.setVisibility(View.VISIBLE);
                    depme = 6;

                    break;

                case StringDeclarations.IT:
                    deptSelect.setText(TECHNOLOGY);
                    deptSelect.setVisibility(View.VISIBLE);
                    depme = 7;

                    break;

                case StringDeclarations.MECHANICAL:
                    deptSelect.setText(CAD);
                    deptSelect.setVisibility(View.VISIBLE);
                    depme = 8;

                    break;

                case StringDeclarations.VULCANS:
                    deptSelect.setText(VUL);
                    deptSelect.setVisibility(View.VISIBLE);
                    depme = 9;

                    break;

                case StringDeclarations.MCA:
                    deptSelect.setText(APPLICATION);
                    deptSelect.setVisibility(View.VISIBLE);
                    depme = 10;

                    break;

                case StringDeclarations.MBA:
                    deptSelect.setText(BUSINESS);
                    deptSelect.setVisibility(View.VISIBLE);
                    depme = 11;

                    break;

            }



        }


        postevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch(SPDept){

                    case StringDeclarations.AUTOMOBILE:

                        SharedPreferences sharedPreferencesA = getSharedPreferences(FLAG_AUTO, MODE_PRIVATE);
                        SharedPreferences.Editor editorA = sharedPreferencesA.edit();
                        editorA.clear();
                        editorA.apply();

                        adminID = auto_Admin;
                        department = "Automobile";

                        break;

                    case StringDeclarations.CIVIL:

                        SharedPreferences sharedPreferencesCI = getSharedPreferences(FLAG_CIVIL, MODE_PRIVATE);
                        SharedPreferences.Editor editorCI = sharedPreferencesCI.edit();
                        editorCI.clear();
                        editorCI.apply();

                        adminID = civil_Admin;
                        department = "Civil";

                        break;

                    case StringDeclarations.CSE:

                        SharedPreferences sharedPreferencesCS = getSharedPreferences(FLAG_CSE, MODE_PRIVATE);
                        SharedPreferences.Editor editorCS = sharedPreferencesCS.edit();
                        editorCS.clear();
                        editorCS.apply();

                        adminID = cse_Admin;
                        department = "CSE";

                        break;

                    case StringDeclarations.ECE:

                        SharedPreferences sharedPreferencesEC = getSharedPreferences(FLAG_ECE, MODE_PRIVATE);
                        SharedPreferences.Editor editorEC = sharedPreferencesEC.edit();
                        editorEC.clear();
                        editorEC.apply();

                        adminID = ece_Admin;
                        department = "ECE";

                        break;

                    case StringDeclarations.EEE:

                        SharedPreferences sharedPreferencesEE = getSharedPreferences(FLAG_EEE, MODE_PRIVATE);
                        SharedPreferences.Editor editorEE = sharedPreferencesEE.edit();
                        editorEE.clear();
                        editorEE.apply();

                        adminID = eee_Admin;
                        department = "EEE";

                        break;

                    case StringDeclarations.EIE:

                        SharedPreferences sharedPreferencesEI = getSharedPreferences(FLAG_EIE, MODE_PRIVATE);
                        SharedPreferences.Editor editorEI = sharedPreferencesEI.edit();
                        editorEI.clear();
                        editorEI.apply();

                        adminID = eie_Admin;
                        department = "EIE";

                        break;

                    case StringDeclarations.IT:

                        SharedPreferences sharedPreferencesIT = getSharedPreferences(FLAG_IT, MODE_PRIVATE);
                        SharedPreferences.Editor editorIT = sharedPreferencesIT.edit();
                        editorIT.clear();
                        editorIT.apply();

                        adminID = it_Admin;
                        department = "IT";

                        break;

                    case StringDeclarations.MECHANICAL:

                        SharedPreferences sharedPreferencesME = getSharedPreferences(FLAG_MECH, MODE_PRIVATE);
                        SharedPreferences.Editor editorME = sharedPreferencesME.edit();
                        editorME.clear();
                        editorME.apply();

                        adminID = mech_Admin;
                        department = "Mechanical";

                        break;

                    case StringDeclarations.VULCANS:

                        SharedPreferences sharedPreferencesVU = getSharedPreferences(FLAG_VULC, MODE_PRIVATE);
                        SharedPreferences.Editor editorVU = sharedPreferencesVU.edit();
                        editorVU.clear();
                        editorVU.apply();

                        adminID = vul_Admin;
                        department = "Vulcans";

                        break;

                    case StringDeclarations.MBA:

                        SharedPreferences sharedPreferencesMB = getSharedPreferences(FLAG_MBA, MODE_PRIVATE);
                        SharedPreferences.Editor editorMB = sharedPreferencesMB.edit();
                        editorMB.clear();
                        editorMB.apply();

                        adminID = mba_Admin;
                        department = "MBA";

                        break;

                    case StringDeclarations.MCA:

                        SharedPreferences sharedPreferencesMC = getSharedPreferences(FLAG_MCA, MODE_PRIVATE);
                        SharedPreferences.Editor editorMC = sharedPreferencesMC.edit();
                        editorMC.clear();
                        editorMC.apply();

                        adminID = mca_Admin;
                        department = "MCA";

                }

                event = eventName.getText().toString();
                descrip = des.getText().toString();
                invitation = invite.getText().toString();
                randomUUID = UUID.randomUUID().toString();

                if((event != null) && (dateGet != null) && (descrip != null) && (randomUUID != null) && (invitation != null))
                {

                    //Snackbar snackbar  = Snackbar.make(layout, "Please fill all the fields", Snackbar.LENGTH_LONG);
                    //snackbar.show();

                    uploadImage();

                }

                else
                {
                    Snackbar snackbar  = Snackbar.make(layout, "Please fill all the fields", Snackbar.LENGTH_LONG);
                    snackbar.show();

                    //Toast.makeText(AdminActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

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
                        date.setText(dateGet);

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("WARNING!")
                .setMessage("Are you sure to Quit? \nChanges made, will not be saved!")
                .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        startActivity(new Intent(AdminActivity.this, HomeActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog  = builder.create();
        alertDialog.show();

    }

    private void updateData(String admin_ID, String eventNam, String date, String desc, String inv, String imgURL, String attendCount,
                            String interestedCount, String notIntCount) {

        //AdminActivity admin = new AdminActivity(event, date);



            mDatabase.child(dep).child(admin_ID).child("Event:").setValue(eventNam);
            mDatabase.child(dep).child(admin_ID).child("Description:").setValue(desc);
            mDatabase.child(dep).child(admin_ID).child("Date:").setValue(date);
            mDatabase.child(dep).child(admin_ID).child("Invitation:").setValue(inv);
            mDatabase.child(dep).child(admin_ID).child("Image URL:").setValue(imgURL);
            mDatabase.child(dep).child(admin_ID).child("Going:").setValue(attendCount);
            mDatabase.child(dep).child(admin_ID).child("Interested:").setValue(interestedCount);
            mDatabase.child(dep).child(admin_ID).child("Not Interested:").setValue(notIntCount);

            /*if(punisher == 0)
            {
                mDatabase.child(dep).child(admin_ID).child("Next Event: ").child("Event: ").setValue("NO UPCOMING EVENTS");
                mDatabase.child(dep).child(admin_ID).child("Next Event: ").child("Date: ").setValue("N/A");
                mDatabase.child(dep).child(admin_ID).child("Next Event: ").child("Description: ").setValue("NO DESCRIPTION");

            }
*/

            startActivity(new Intent(AdminActivity.this, HomeActivity.class));
            finish();



        //Toast.makeText(this, "Event:" +eventNam+"\nDate:"+date, Toast.LENGTH_SHORT).show();

    }


    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {


        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            if(randomUUID.equals(""))
            {

                Toast.makeText(this, "Please Insert Image!", Toast.LENGTH_SHORT).show();
            }

            else
            {


                final StorageReference ref = storageReference.child("images/" + department + "/" + event);

               // filePath = Uri.fromFile(new File("Kajak"));


                /*ref.child("images/" + department + "/" + event).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        image_url = uri.toString();



                    }
                });
*/
                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(AdminActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                                image_url = taskSnapshot.getStorage().getDownloadUrl().toString();

                                //String sam = taskSnapshot.getStorage().getPath();

  //                              image_url = taskSnapshot.getStorage().getRoot().toString();
                                Log.d("EEC Events: ",image_url);
                                /*String img = taskSnapshot.getUploadSessionUri().getPath();
                                Log.d("EEC Events: ", img);
                                String img1 = taskSnapshot.getStorage().getPath();
                                Log.d("EEC Events",img1);
                                String img2 = taskSnapshot.getStorage().child("images/" + department + "/" + event).toString();
                                Log.d("EEC Events",img2);
*/

                              //  Log.d("EEC Events: ",image_url);

                                /*.addOnSuccessListener(new OnSuccessListener<Uri>() {*/
                                    /*@Override
                                    public void onSuccess(Uri uri) {

                                        Uri downn = uri;

                                        image_url = downn.toString();

                                        Log.d("EEC EVents: ImageURL", image_url);

                                    }
                                });
*/
                                if(filePath != null){

                                    //image_url = filePath.toString();

                                    String  counti = String.valueOf(intCount);
                                    String counta = String.valueOf(attCount);
                                    String countn = String.valueOf(notCount);

                                    updateData(adminID ,event, dateGet, descrip, invitation, image_url, counta, counti, countn);
                                }

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(AdminActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                //Calculates the Uploading percentage.
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                btnImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
