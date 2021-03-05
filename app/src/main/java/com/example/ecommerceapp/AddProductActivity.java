package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddProductActivity extends AppCompatActivity {

  Toolbar toolbar;
  String cat_name;
  EditText productnameEt,descET,priceEt,quantityEt;
  FirebaseDatabase db;
  String date;

  Button save_btn;

  ImageView imageView;
  private final int Galley_pick=1;
  Uri uri;
  StorageReference ref;
  SimpleDateFormat sdf;
  Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        toolbar=findViewById(R.id.productadd_tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Products");

        final Intent intent=getIntent();
        cat_name=intent.getExtras().getString("category");
        getSupportActionBar().setTitle("Add Products " + cat_name);

        imageView=findViewById(R.id.productadd_img);
        productnameEt=findViewById(R.id.productadd_name);
        descET=findViewById(R.id.productadd_description);
        priceEt=findViewById(R.id.productadd_price);
        quantityEt=findViewById(R.id.productadd_quantity);
        db=FirebaseDatabase.getInstance();


        sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent gallery_intent=new Intent();
                gallery_intent.setType("image/*");
                gallery_intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(gallery_intent,Galley_pick);

            }
        });

        save_btn=findViewById(R.id.priductadd_save);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                 date=sdf.format(calendar.getTime());
                ref= FirebaseStorage.getInstance().getReference();

                ref.child(date).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                           @Override
                           public void onComplete(@NonNull Task<Uri> task) {
                                if(task.isSuccessful())
                                {
                                    Uri img_uri=task.getResult();
                                addProduct(img_uri);
                                }
                           }
                       });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddProductActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });










    }

    private void addProduct(Uri img_uri) {

      String name,description;
      Double price;
      int quantity;
      String pr_im;
      pr_im=img_uri.toString();

      name=productnameEt.getText().toString();
      price=Double.parseDouble(priceEt.getText().toString());
      description=descET.getText().toString();
      quantity=Integer.parseInt(quantityEt.getText().toString());


        HashMap<String,Object> hashmap=new HashMap<>();
        hashmap.put("prodName",name);
        hashmap.put("prodDesc",description);
        hashmap.put("prodPrice",price);
        hashmap.put("prodQuant",quantity);
        hashmap.put("prodImg",pr_im);
        hashmap.put("catName",cat_name);

        db.getReference().child("Products").child(date).setValue(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                 if(task.isSuccessful())
                 {
                      Toast.makeText(AddProductActivity.this,"Added product",Toast.LENGTH_SHORT).show();
                      Intent prev_intent=new Intent(AddProductActivity.this,AdminHome.class);
                      prev_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                      startActivity(prev_intent);
                 }
                 else
                 {
                     Toast.makeText(AddProductActivity.this,"Error",Toast.LENGTH_SHORT).show();
                 }
            }
        });

  }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Galley_pick&&resultCode==RESULT_OK)
        {

             uri=data.getData();
        }

  }
}