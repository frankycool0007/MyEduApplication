package com.tecxpert.myeduapplication.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecxpert.myeduapplication.R;
import com.tecxpert.myeduapplication.model.User;
import com.tecxpert.myeduapplication.utill.PrefManager;

import java.util.ArrayList;
import java.util.HashMap;
public class SignupActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference classesdatabaseReference,userdatabaseReference;
    TextInputEditText ed_username,ed_password,ed_email,ed_board;
    MaterialButton btn_login;
    AutoCompleteTextView ed_item_uno;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    MaterialTextView sin_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        prefManager=PrefManager.getInstance(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.....");
        sin_txt=(MaterialTextView) findViewById(R.id.sin_txt);
        ed_username=(TextInputEditText) findViewById(R.id.ed_username);
        ed_password=(TextInputEditText) findViewById(R.id.ed_password);
        ed_email=(TextInputEditText) findViewById(R.id.ed_email);
        ed_board=(TextInputEditText) findViewById(R.id.ed_board);
        ed_item_uno=(AutoCompleteTextView) findViewById(R.id.ed_item_uno);
        btn_login=(MaterialButton) findViewById(R.id.btn_login);
        firebaseDatabase = FirebaseDatabase.getInstance();
        classesdatabaseReference = firebaseDatabase.getReference("classes");
        sin_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });
        classesdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap ht = (HashMap<String, Object>) snapshot.getValue();
                    ArrayList<String> strings=new ArrayList<>();
                    strings.add("Select Class");
                    for (Object s:
                         ht.keySet()) {
                        strings.add(s.toString());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (SignupActivity.this, android.R.layout.select_dialog_item, strings);
                    ed_item_uno.setThreshold(1);
                    ed_item_uno.setAdapter(adapter);
                    ed_item_uno.setSelection(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ed_board.getText().toString().isEmpty()&&!ed_email.getText().toString().isEmpty()&&!ed_username.getText().toString().isEmpty()&&!ed_password.getText().toString().isEmpty()&&!ed_item_uno.getText().toString().isEmpty()) {
                    User user=new User(ed_username.getText().toString(),ed_email.getText().toString(),ed_password.getText().toString(),ed_board.getText().toString(),ed_item_uno.getText().toString());
                    firebaseDatabase.getReference("users").child(ed_email.getText().toString().substring(0,ed_email.getText().toString().indexOf("@"))).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()) {
                                firebaseDatabase.getReference("users").child(ed_email.getText().toString().substring(0,ed_email.getText().toString().indexOf("@"))).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(SignupActivity.this,"Successfully Registered",Toast.LENGTH_LONG).show();
                                        prefManager.setUser(user);
                                        startActivity(new Intent(SignupActivity.this, MainScreenActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                            }
                            else {
                                Toast.makeText(SignupActivity.this,"this User is already exist",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });
    }
}