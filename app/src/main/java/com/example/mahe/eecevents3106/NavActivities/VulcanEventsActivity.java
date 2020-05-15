package com.example.mahe.eecevents3106.NavActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahe.eecevents3106.Classes.StringDeclarations;
import com.example.mahe.eecevents3106.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.appinvite.FirebaseAppInvite;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.mahe.eecevents3106.Classes.StringDeclarations.FLAG_MBA;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.FLAG_MECH;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.FLAG_VULC;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.MECHFL;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.VULCFL;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.dep;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.flagMech;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.flagVulc;
import static com.example.mahe.eecevents3106.Classes.StringDeclarations.vul_Admin;

public class VulcanEventsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    TextView vulHead, vulDesc, vulDate, vulShare;

    TextView vulcanHead, vulTV;

    ImageView vulIMG;

    String date, event, description, imageURL;

    Uri deepLink;

    String deeplink;
    String fbkey;
    String invi;

    FirebaseAnalytics analytics;

    Spinner inviteLog;

    String choice;

    String intCount;
    String gngCount;
    String ntCount;

    int count1 = 0;
    int count2 = 0;
    int count3 = 0;



    List<String> attendance = new ArrayList<>();

    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vulcan_events);

        vulHead = findViewById(R.id.textVULCAN);
        vulDesc = findViewById(R.id.textVULCANDesc);
        vulDate = findViewById(R.id.dateXVULCAN);
        vulShare = findViewById(R.id.VULCANGoTo);
        vulIMG  = findViewById(R.id.IMGVULVULCAN);
        inviteLog = findViewById(R.id.vulINVITELog);
        vulTV = findViewById(R.id.tvPeopAttendVul);

        inviteLog.setEnabled(false);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Department/"+vul_Admin);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Capture_it.ttf");
        vulcanHead = findViewById(R.id.vulcanEventHeading);
        vulcanHead.setTypeface(font);

        inviteLog.setOnItemSelectedListener(this);

        attendance.add(StringDeclarations.att_str);
        attendance.add(StringDeclarations.int_str);
        attendance.add(StringDeclarations.not_int);

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, attendance);
        // Drop down layout style - list view with radio button
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        inviteLog.setAdapter(adapter);


        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)

                        if (pendingDynamicLinkData != null) {

                            deepLink = pendingDynamicLinkData.getLink();
                            analytics = FirebaseAnalytics.getInstance(VulcanEventsActivity.this);
                            deeplink = deepLink.toString();
                            Log.v("Deep Link:", deeplink);
                            Toast.makeText(VulcanEventsActivity.this, "" + deeplink, Toast.LENGTH_SHORT).show();

                            FirebaseAppInvite invite = FirebaseAppInvite.getInvitation(pendingDynamicLinkData);
                            if (invite != null) {
                                String invID = invite.getInvitationId();
                                if (!TextUtils.isEmpty(invID)) {
                                    Log.v("Invitation ID:", invID);
                                }
                            }

                        }

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("hey", "getDynamicLink:onFailure", e);
                    }
                });




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.getValue()!= null){

                    date = snapshot.child("Date:").getValue().toString();
                    event = snapshot.child("Event:").getValue().toString();
                    description = snapshot.child("Description:").getValue().toString();
                    imageURL = snapshot.child("Image URL:").getValue().toString();
                    invi = snapshot.child("Invitation:").getValue().toString();

                    imageURL = imageURL.replaceAll("\\]","");
                    imageURL = imageURL.replaceAll("\\[","");

                    gngCount = snapshot.child("Going:").getValue().toString();
                    intCount = snapshot.child("Interested:").getValue().toString();
                    ntCount = snapshot.child("Not Interested:").getValue().toString();

                    count1 = Integer.parseInt(gngCount);
                    count2 = Integer.parseInt(intCount);
                    count3 = Integer.parseInt(ntCount);

                    Log.v("go:", String.valueOf(count1));
                    Log.v("go1:",gngCount);
                    Log.v("int:", String.valueOf(count2));
                    Log.v("int1:", intCount);
                    Log.v("ntInt:", String.valueOf(count3));
                    Log.v("ntInt1:", ntCount);

                    /* ------------------------------------------------------------------ */


                    vulcanHead.setText(event);
                    vulcanHead.setVisibility(View.VISIBLE);

                    vulHead.setText(event);
                    vulHead.setVisibility(View.VISIBLE);

                    vulDesc.setText(description);
                    vulDesc.setVisibility(View.VISIBLE);

                    vulDate.setText(date);
                    vulDate.setVisibility(View.VISIBLE);

                    Picasso.with(VulcanEventsActivity.this).load(imageURL).into(vulIMG);

                    if(count1 >= 10)
                    {
                        vulTV.setText(count1+" People are attending this event");
                        vulTV.setVisibility(View.VISIBLE);
                    }

                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        vulShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gcreatedynamiclink();

            }
        });


    }

    private void gcreatedynamiclink() {

        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://f63b2.app.goo.gl/Tbeh"))
                .setDynamicLinkDomain("f63b2.app.goo.gl")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("com.example.mahe.eecevents3106")
                                .build())

                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle("Vulcans")
                                .setDescription(event)
                                .build())


                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {

                            Uri shortLink = task.getResult().getShortLink();
                            String a = shortLink.toString();

                            shareit(a);

                        }
                        else
                        {

                        }
                    }
                });

    }

    private void shareit(String msg) {
        if (msg != null) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    invi +"\n" + msg);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else
            Toast.makeText(this, "NO", Toast.LENGTH_LONG).show();

    }




    @Override
    public void onBackPressed() {


        SharedPreferences sharedPreferences = getSharedPreferences(FLAG_VULC, MODE_PRIVATE);

        int flag = sharedPreferences.getInt(VULCFL, 0);

        if(flag == 0)
        {
            displayDialog();
        }
        else
        {
            startActivity(new Intent(VulcanEventsActivity.this, VulcansActivity.class));
            finish();
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        choice = adapterView.getItemAtPosition(i).toString();
        inviteLog.setEnabled(true);


    }


    private void displayDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Your decision on attending the event: "+choice)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        pushVal();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void pushVal() {

        switch (choice) {

            case StringDeclarations.att_str:

                count1 = count1 +1;
                Log.v("count:", String.valueOf(count1));
                String tagg = "Going:";
                String tag_id1 = String.valueOf(count1);
                updateData(tagg, tag_id1);

                break;

            case StringDeclarations.int_str:

                count2 = count2 +1;
                Log.v("count2", String.valueOf(count2));
                String tag = "Interested:";
                String tag_id2 = String.valueOf(count2);
                updateData(tag, tag_id2);

                break;

            case StringDeclarations.not_int:

                count3 = count3 +1;
                Log.v("count3", String.valueOf(count3));
                String  taggg = "Not Interested:";
                String tag_id3 = String.valueOf(count3);
                updateData(taggg, tag_id3);

                break;
        }



    }

    private void updateData(String tag, String count) {

        SharedPreferences sharedPreferences = getSharedPreferences(FLAG_VULC, MODE_PRIVATE);
        SharedPreferences.Editor  editor = sharedPreferences.edit();

        Log.v(tag, String.valueOf(count));

        mDatabase.child(dep).child(vul_Admin).child(tag).setValue(count);
        flagVulc = 1;

        editor.putInt(VULCFL, flagVulc);
        editor.apply();

        startActivity(new Intent(VulcanEventsActivity.this, VulcansActivity.class));
        finish();
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
