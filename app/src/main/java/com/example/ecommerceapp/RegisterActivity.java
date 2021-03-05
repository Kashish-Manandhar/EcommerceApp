package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameET,numberET,emailET,passwordET;
    private Button login,register;
    FirebaseDatabase database;
    DatabaseReference user_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameET=findViewById(R.id.register_name);
        numberET=findViewById(R.id.register_number);
        emailET=findViewById(R.id.register_email);
        passwordET=findViewById(R.id.register_password);
        register=findViewById(R.id.register_btn);
        login=findViewById(R.id.register_login);
        database=FirebaseDatabase.getInstance();
        user_ref=database.getReference();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
              finish();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,number,email,password;
                name=nameET.getText().toString();
                number=numberET.getText().toString();
                email=emailET.getText().toString();
                password=passwordET.getText().toString();

                if(TextUtils.isEmpty(name))
                {
                    Toast.makeText(RegisterActivity.this,"Enter youyr credentials",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(RegisterActivity.this,"Enter youyr credentials",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(number))
                {
                    Toast.makeText(RegisterActivity.this,"Enter youyr credentials",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(RegisterActivity.this,"Enter youyr credentials",Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<8)
                {
                    Toast.makeText(RegisterActivity.this,"The password must be greater nthan 8 characters",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    createUser(name,number,email,password);

                }
            }
        });

    }

    private void createUser(String name, final String number, String email, String password) {

        final HashMap<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("number",number);
        map.put("email",email);
        map.put("password",password);
        map.put("image","default");

        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!(dataSnapshot.child("Users").child(number).exists()))
                {
                    user_ref.child("Users").child(number).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegisterActivity.this,"Successfully created a user",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                {
                    Toast.makeText(RegisterActivity.this,number +" Exists",Toast.LENGTH_SHORT).show();
            }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RegisterActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }
}