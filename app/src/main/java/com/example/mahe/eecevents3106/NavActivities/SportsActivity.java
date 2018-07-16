package com.example.mahe.eecevents3106.NavActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahe.eecevents3106.Classes.StringDeclarations;
import com.example.mahe.eecevents3106.HomeActivity;
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

import java.util.ArrayList;
import java.util.List;

public class SportsActivity extends AppCompatActivity {


    ListView lv;

    TextView sportHead;

    String date, event, description, imageURL;

    String samDate;
    String  samEvent;

    String eventDateFull;

    int val;

    int val1;
    List<String> eventDates = new ArrayList<>();

    String invi;

    Uri deepLink;

    String deeplink;
    String fbkey;

    FirebaseAnalytics analytics;

    TextView sHare;

    //List<String> events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        lv  = findViewById(R.id.listEvents);
        sHare = findViewById(R.id.sHareSportsTV);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Department/aadukalam");
        //StorageReference storageReference = FirebaseStorage.getInstance().getReference();

         Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Capture_it.ttf");
        sportHead = findViewById(R.id.SportEventHeading);
        sportHead.setTypeface(font);

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)

                        if (pendingDynamicLinkData != null) {

                            deepLink = pendingDynamicLinkData.getLink();
                            analytics = FirebaseAnalytics.getInstance(SportsActivity.this);
                            deeplink = deepLink.toString();
                            Log.v("Deep Link:", deeplink);
                            Toast.makeText(SportsActivity.this, "" + deeplink, Toast.LENGTH_SHORT).show();

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



        SharedPreferences sharedPreferences = getSharedPreferences(StringDeclarations.tot_val, MODE_PRIVATE);

        val = sharedPreferences.getInt(StringDeclarations.val_use, 0);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.getValue()!= null){

                    for(int j = 0; j<val; j++)
                    {

                        samDate  = snapshot.child("Date"+j+":").getValue().toString();
                        samEvent = snapshot.child("Event"+j+":").getValue().toString();
                        invi = snapshot.child("Invitation:").getValue().toString();

                        eventDateFull = samEvent + " - " +samDate;

                        eventDates.add(eventDateFull);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                eventDates);

        lv.setAdapter(arrayAdapter);

        sHare.setOnClickListener(new View.OnClickListener() {
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
                                .setTitle("Aadukalam")
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
        super.onBackPressed();

        startActivity(new Intent(SportsActivity.this, HomeActivity.class));
        finish();
    }



}
