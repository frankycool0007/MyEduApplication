package com.tecxpert.myeduapplication.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {
    TextInputEditText ed_username,ed_password;
    MaterialButton btn_login;
    FirebaseDatabase firebaseDatabase;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    MaterialTextView sin_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefManager=PrefManager.getInstance(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.....");
        ed_username=(TextInputEditText) findViewById(R.id.ed_username);
        ed_password=(TextInputEditText) findViewById(R.id.ed_password);
        btn_login=(MaterialButton) findViewById(R.id.btn_login);
        sin_txt=(MaterialTextView) findViewById(R.id.sin_txt);
        firebaseDatabase = FirebaseDatabase.getInstance();
        sin_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                if (!ed_username.getText().toString().isEmpty() && !ed_password.getText().toString().isEmpty()) {
                    firebaseDatabase.getReference("users").child(ed_username.getText().toString().substring(0, ed_username.getText().toString().indexOf("@"))).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            progressDialog.dismiss();
                            if (snapshot.exists()) {
                                User user = snapshot.getValue(User.class);
                                if (user.getPassword().equals(ed_password.getText().toString())) {
                                    prefManager.setUser(user);
                                    startActivity(new Intent(LoginActivity.this, MainScreenActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Username or password incorrect", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Username or password incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });

    }
}