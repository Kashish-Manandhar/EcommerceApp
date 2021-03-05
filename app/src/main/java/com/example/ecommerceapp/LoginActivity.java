package com.example.ecommerceapp;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.ModelClass.User;
import com.google.android.gms.common.FirstPartyScopes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText numberET, passwordET;
    private Button login;
    private TextView user, admin, new_accnt;
    String database = "Users";
    FirebaseDatabase firebaseDatabase;
    CheckBox checkBox;
    boolean login_det;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = findViewById(R.id.tVuser);
        admin = findViewById(R.id.tVadmin);
        new_accnt = findViewById(R.id.tVsignUp);
        login = findViewById(R.id.login_btn);
        numberET = findViewById(R.id.login_number);
        passwordET = findViewById(R.id.login_password);
        user.setVisibility(View.GONE);
        checkBox = findViewById(R.id.is_loggedin);


        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.setVisibility(View.GONE);
                user.setVisibility(View.VISIBLE);
                login.setText("Login as Admin");
                database = "Admin";

            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.setVisibility(View.VISIBLE);
                user.setVisibility(View.GONE);
                database = "Users";

            }
        });

        new_accnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String number, password;
                number = numberET.getText().toString();
                password = passwordET.getText().toString();
                LoginUser(number, password, database);

            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    login_det = true;
                } else {
                    login_det = false;
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences;
        sharedPreferences = this.getSharedPreferences("IsLogged_details", Context.MODE_PRIVATE);
        boolean is_logged = sharedPreferences.getBoolean("keep_logged", false);

        if (is_logged) {

            SharedPreferences usersharedpreferences=getSharedPreferences("User_details",MODE_PRIVATE);

            String num = usersharedpreferences.getString("number", "");
            String pass = usersharedpreferences.getString("password", "");
            String db = usersharedpreferences.getString("user_type", "");
            LoginUser(num, pass, db);

            Log.d("Help", "onStart: " + num + "\n password" + pass);
        }
    }

    private void LoginUser(final String number, final String password, final String ref_name) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference().child(ref_name);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(number).exists()) {
                    User user = dataSnapshot.child(number).getValue(User.class);
                    if (user.getNumber().equals(number)) {
                        if (user.getPassword().equals(password)) {
                            if (login_det) {
                                SharedPreferences sharedPreferences = getSharedPreferences("IsLogged_details", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("keep_logged", login_det);
                                editor.commit();
                            }

                            storeUserDetails(number, password, ref_name);


                            if (ref_name.equals("Admin")) {
                                startActivity(new Intent(LoginActivity.this, AdminHome.class));
                                finish();

                            } else {
                                Intent userintent = new Intent(LoginActivity.this, UserHome.class);
                                userintent.putExtra("number", number);
                                startActivity(userintent);
                                finish();

                            }

                        } else {
                            Toast.makeText(LoginActivity.this, "Doesnt exist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Doesnt exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Doesnt exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void storeUserDetails(String number, String password, String ref_name) {


        SharedPreferences sharedPreferences=getSharedPreferences("User_details",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("number",number);
        editor.putString("password",password);
        editor.putString("user_type",ref_name);
        editor.commit();
    }


}