package com.example.buzzapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HelpActivity extends AppCompatActivity {
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_page);
        Spinner menu = findViewById(R.id.menu1);
        listView = findViewById(R.id.expView);
        initData();
        listAdapter = new ExpandableAdapterList(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);









        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.menu1, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menu.setAdapter(adapter1);

        menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem1 = parent.getItemAtPosition(position).toString();
                if(selectedItem1.equals("Maps")){
                    Intent i = new Intent(HelpActivity.this, MapsActivity.class);
                    startActivity(i);
                }
                if(selectedItem1.equals("Settings")){
                    Intent help = new Intent (HelpActivity.this , SettingsActivity.class);
                    startActivity(help);
                }
                if(selectedItem1.equals("Help")){

                }
                if(selectedItem1.equals("Logout")){
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    Intent i = new Intent(HelpActivity.this, LoginActivity.class);
                    startActivity(i);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        getSupportFragmentManager()
                .beginTransaction()
                //.replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }





    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();





        listDataHeader.add("Bus Stop");
        listDataHeader.add("TimeTable");
        listDataHeader.add("Alarm");
        listDataHeader.add("Contact Us");
        listDataHeader.add("Frequently Ask Question");

        List<String> Bus_stop = new ArrayList<>();
        Bus_stop.add("This is Expandable ListView");



        List<String> Timetable = new ArrayList<>();
        Timetable.add("Image");

        List<String> Alarm = new ArrayList<>();
        Alarm.add("How To Set the alarm");

        List<String> Contact = new ArrayList<>();
        Contact.add(" Call Us : 44 9031 321322 (We wont answer the phone by the way)");



        List<String> frequent = new ArrayList<>();
        frequent.add("How to search ");


        listHash.put(listDataHeader.get(0),Bus_stop);
        listHash.put(listDataHeader.get(1), Timetable);
        listHash.put(listDataHeader.get(2),Alarm);
        listHash.put(listDataHeader.get(3),Contact);
        listHash.put(listDataHeader.get(4),frequent);




    }
    }











