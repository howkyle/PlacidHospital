package com.comp3161.placidandroid.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.comp3161.placidandroid.R;
import com.comp3161.placidandroid.fragments.InternsFragment;
import com.comp3161.placidandroid.fragments.MedicationsFragment;
import com.comp3161.placidandroid.fragments.NurseFragment;
import com.comp3161.placidandroid.fragments.PatientsFragment;
import com.comp3161.placidandroid.util.Constants;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBar actionBar;
    private String mUserName;
    private int currentFrag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),RegisterPatientActivity.class);
                startActivity(intent);

            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);
        mUserName = sharedPreferences.getString("name","none");

        initViews();
        setUpActionBar();
        setUpNavDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        switch (currentFrag){
            case 0:
                inflater.inflate(R.menu.menu_patient, menu);
                return true;
            case 1:
                inflater.inflate(R.menu.menu_nurse, menu);
                return true;
            case 2:
                inflater.inflate(R.menu.menu_intern, menu);
                return true;
            case 3:
                inflater.inflate(R.menu.menu_medication, menu);
                return true;
            default:
                inflater.inflate(R.menu.menu_main, menu);
                return true;

        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_drawer);
    }

    private void setUpActionBar()
    {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Patients");
            actionBar.setDisplayHomeAsUpEnabled(true);
            final ActionBarDrawerToggle toggle =
                    new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);

            toggle.setDrawerIndicatorEnabled(true);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }
    }

    private void setUpNavDrawer()
    {
        if(!TextUtils.isEmpty(mUserName))
            ((TextView)navigationView.getHeaderView(0).findViewById(R.id.header_username)).setText(mUserName);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.patients:
                        actionBar.setTitle("Patients");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment, new PatientsFragment())
                                .commit();
                        currentFrag = 0;
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nurse:
                        actionBar.setTitle("Nurses");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment, new NurseFragment())
                                .commit();
                        currentFrag = 1;
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.interns:
                        actionBar.setTitle("Interns");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment, new InternsFragment())
                                .commit();
                        currentFrag = 2;
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.medication:
                        actionBar.setTitle("Medication");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment, new MedicationsFragment())
                                .commit();
                        currentFrag = 3;
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.sign_out:
                        logOut();
                        drawerLayout.closeDrawers();
                        return true;
                    default:
                        drawerLayout.closeDrawers();
                        return true;
                }
            }
        });
        navigationView.setCheckedItem(R.id.patients);
        navigationView.getMenu().performIdentifierAction(R.id.patients, 0);
    }

    private void logOut()
    {
        final Context context =this;
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Logout").setMessage("Are you sure?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        clearCache(); //clears all the user data

                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent); //returns the user to the login page
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { return; } })
                .create();
        dialog.show();

    }

    private void clearCache()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

}
