package com.example.mahe.eecevents3106;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mahe.eecevents3106.Classes.StringDeclarations;
import com.example.mahe.eecevents3106.Login_Signup.LoginAct;
import com.example.mahe.eecevents3106.NavActivities.DeptEventActivity;
import com.example.mahe.eecevents3106.NavActivities.SportsActivity;
import com.example.mahe.eecevents3106.NavActivities.SympoActivity;
import com.example.mahe.eecevents3106.NavActivities.VulcansActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView head;

    ImageView aad, vulc, dept;
    TextView aadTV, vulcTV, deptTV;

    String urlDept = "https://firebasestorage.googleapis.com/v0/b/eecevents3106-b9367.appspot.com/o/images%2FJust%20Images%2F34972-Easwari-Engineering-College-768x437.jpg?alt=media&token=25a0b13b-0c9b-4eb5-8bcf-cf7d78db3679";
    String urlAAD = "https://firebasestorage.googleapis.com/v0/b/eecevents3106-b9367.appspot.com/o/images%2FJust%20Images%2Ffootball.jpg?alt=media&token=20860c28-5d09-4b5e-a671-188e3509cba8";
    String urlVulc= "https://firebasestorage.googleapis.com/v0/b/eecevents3106-b9367.appspot.com/o/images%2FJust%20Images%2Fvulcanslog.png?alt=media&token=d28ff5ca-6010-4975-b0f3-78e978a4cdf6";

    //String  dateAUTO, dateCSE, dateCIVIL, dateECE, dateEEE, dateEIE, dateIT, dateMECH;
    //DatabaseReference fsAUTO, fsCIVIL, fsCSE, fsECE, fsEEE, fsEIE, fsIT, fsMECH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("EEC Events");
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        head = findViewById(R.id.textEVENTSINCOL);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/Capture_it.ttf");
        head.setTypeface(font);

        aad = findViewById(R.id.imgAadEVENT);
        vulc = findViewById(R.id.imgVulcanEVENT);
        dept = findViewById(R.id.imgDeptEVENT);

        deptTV = findViewById(R.id.textViewVisitDept);
        vulcTV = findViewById(R.id.textviewVisitVulcan);
        aadTV = findViewById(R.id.textviewVisitAAD);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        //checkNetwork();

        Glide.with(getApplicationContext()).load(urlVulc).into(vulc);
        Glide.with(getApplicationContext()).load(urlAAD).into(aad);
        Glide.with(getApplicationContext()).load(urlDept).into(dept);


        deptTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeActivity.this, DeptEventActivity.class));
                finish();
            }
        });

        vulcTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeActivity.this, VulcansActivity.class));
                finish();
            }
        });

        aadTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeActivity.this, SportsActivity.class));
                finish();
            }
        });
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            return true;
        }
        else if (id == R.id.action_admin){

            checkPasswordPrompt();

        }

        return super.onOptionsItemSelected(item);
    }

    private void checkPasswordPrompt() {

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.password_prompt_admin, null);

        SharedPreferences sharedPreferences = getSharedPreferences(StringDeclarations.ADMINSP, MODE_PRIVATE);
        final SharedPreferences.Editor editor  = sharedPreferences.edit();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(promptsView);

        final TextInputEditText userInput = promptsView
                .findViewById(R.id.adminPasswordPrompt);



        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                String passCheck = userInput.getText().toString();

                                if(passCheck.equals(StringDeclarations.AUTOMOBILE) || passCheck.equals(StringDeclarations.CIVIL) ||
                                        passCheck.equals(StringDeclarations.CSE) ||  passCheck.equals(StringDeclarations.ECE) ||
                                        passCheck.equals(StringDeclarations.EEE) || passCheck.equals(StringDeclarations.EIE)||
                                        passCheck.equals(StringDeclarations.IT) || passCheck.equals(StringDeclarations.MECHANICAL)||
                                        passCheck.equals(StringDeclarations.VULCANS) || passCheck.equals(StringDeclarations.MBA)||
                                        passCheck.equals(StringDeclarations.MCA))
                                {

                                    editor.putString(StringDeclarations.PASSWORDY, passCheck);
                                    editor.apply();

                                    startActivity(new Intent(HomeActivity.this, AdminActivity.class));
                                    finish();

                                }

                                else if( passCheck.equals(StringDeclarations.AADUKALAM))
                                {
                                    startActivity(new Intent(HomeActivity.this, AdminSportsActivity.class));
                                    finish();
                                }

                                else if ( passCheck.equals(StringDeclarations.VULCAN_MEM))
                                {
                                    startActivity(new Intent(HomeActivity.this, AdminVulcanActivity.class));
                                    finish();
                                }

                                else
                                {

                                    Toast.makeText(HomeActivity.this, "INVALID  PASSWORD", Toast.LENGTH_SHORT).show();

                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dept)
        {
            startActivity(new Intent(HomeActivity.this, DeptEventActivity.class));
        }
        else if (id == R.id.nav_vulcans)
        {
            startActivity(new Intent(HomeActivity.this, VulcansActivity.class));
        }
        else if (id == R.id.nav_sportseve)
        {
            startActivity(new Intent(HomeActivity.this, SportsActivity.class));
        }
        else if (id == R.id.nav_sympo)
        {
            startActivity(new Intent(HomeActivity.this, SympoActivity.class));
        }
        else if (id == R.id.nav_logout)
        {
            logoutFunc();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutFunc() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("WARNING!")
                .setMessage("Are You sure you want to Logout?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        /*
                        //Shared  Preferences of Automibile
                        SharedPreferences sharedPreferencesA = getSharedPreferences(FLAG_AUTO, MODE_PRIVATE);
                        SharedPreferences.Editor editorA = sharedPreferencesA.edit();
                        editorA.clear();
                        editorA.apply();

                        //Shared Preferences of Civil
                        SharedPreferences sharedPreferencesCI = getSharedPreferences(FLAG_CIVIL, MODE_PRIVATE);
                        SharedPreferences.Editor editorCI = sharedPreferencesCI.edit();
                        editorCI.clear();
                        editorCI.apply();

                        //Shared Preferences of CSE
                        SharedPreferences sharedPreferencesCS = getSharedPreferences(FLAG_CSE, MODE_PRIVATE);
                        SharedPreferences.Editor editorCS = sharedPreferencesCS.edit();
                        editorCS.clear();
                        editorCS.apply();

                        //Shared Preferences of ECE
                        SharedPreferences sharedPreferencesEC = getSharedPreferences(FLAG_ECE, MODE_PRIVATE);
                        SharedPreferences.Editor editorEC = sharedPreferencesEC.edit();
                        editorEC.clear();
                        editorEC.apply();

                        //Shared Preferences of EEE
                        SharedPreferences sharedPreferencesEE = getSharedPreferences(FLAG_EEE, MODE_PRIVATE);
                        SharedPreferences.Editor editorEE = sharedPreferencesEE.edit();
                        editorEE.clear();
                        editorEE.apply();

                        //Sharred Preferences of  EIE
                        SharedPreferences sharedPreferencesEI = getSharedPreferences(FLAG_EIE, MODE_PRIVATE);
                        SharedPreferences.Editor editorEI = sharedPreferencesEI.edit();
                        editorEI.clear();
                        editorEI.apply();

                        //Shared Preferences of IT
                        SharedPreferences sharedPreferencesIT = getSharedPreferences(FLAG_IT, MODE_PRIVATE);
                        SharedPreferences.Editor editorIT = sharedPreferencesIT.edit();
                        editorIT.clear();
                        editorIT.apply();

                        //Shared preferences of Mech
                        SharedPreferences sharedPreferencesME = getSharedPreferences(FLAG_MECH, MODE_PRIVATE);
                        SharedPreferences.Editor editorME = sharedPreferencesME.edit();
                        editorME.clear();
                        editorME.apply();

                        //Shared Preferences of MBA
                        SharedPreferences sharedPreferencesMB = getSharedPreferences(FLAG_MBA, MODE_PRIVATE);
                        SharedPreferences.Editor editorMB = sharedPreferencesMB.edit();
                        editorMB.clear();
                        editorMB.apply();

                        //Shared Preferences of MCA
                        SharedPreferences sharedPreferencesMC = getSharedPreferences(FLAG_MCA, MODE_PRIVATE);
                        SharedPreferences.Editor editorMC = sharedPreferencesMC.edit();
                        editorMC.clear();
                        editorMC.apply();

                        //Shared Preferences of Vulcans
                        SharedPreferences sharedPreferencesVU = getSharedPreferences(FLAG_VULC, MODE_PRIVATE);
                        SharedPreferences.Editor editorVU = sharedPreferencesVU.edit();
                        editorVU.clear();
                        editorVU.apply(); */


                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(HomeActivity.this, LoginAct.class));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }
}
