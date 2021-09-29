package com.tecxpert.myeduapplication.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecxpert.myeduapplication.R;
import com.tecxpert.myeduapplication.utill.PrefManager;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefManager=PrefManager.getInstance(this);
        if (prefManager.getuser()==null){
            startActivity(new Intent(MainActivity.this,SignupActivity.class));
            finish();
        }
        else {
            startActivity(new Intent(MainActivity.this,MainScreenActivity.class));
            finish();
        }
    }
}