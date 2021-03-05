package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {

    TextView nameTV, priceTV, descTV, catNameTV;
    ImageView img;
    String id;
    Toolbar tool;
    FirebaseDatabase database;
    String name, img_name, desc, cat;
    double price;
    ElegantNumberButton quant_btn;
    Button addtocart;
    String quantity;
    DatabaseReference catRef;
    DatabaseReference shipref;
    String status = "";

    String number;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("User_details", MODE_PRIVATE);

        number = sharedPreferences.getString("number", "");

        shipref.child(number).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.child("status").getValue().toString().equals("not shipped"))
                    {
                        status="not shipped";
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Intent intent = getIntent();
        id = intent.getExtras().getString("prod_id");
        tool = findViewById(R.id.product_details_tool);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Carts");
        getSupportActionBar().setHomeButtonEnabled(true);
        nameTV = findViewById(R.id.product_name_details);
        priceTV = findViewById(R.id.product_price_details);
        descTV = findViewById(R.id.product_desc_details);
        catNameTV = findViewById(R.id.product_cat_details);
        img = findViewById(R.id.product_image_details);
        database = FirebaseDatabase.getInstance();
        catRef = database.getReference().child("Carts");
        shipref =database.getReference().child("Orders");
        database.getReference().child("Products").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("prodName").getValue().toString();
                desc = dataSnapshot.child("prodDesc").getValue().toString();
                cat = dataSnapshot.child("catName").getValue().toString();
                img_name = dataSnapshot.child("prodImg").getValue().toString();
                price = Double.parseDouble(dataSnapshot.child("prodPrice").getValue().toString());

                nameTV.setText(name);
                descTV.setText(desc);
                catNameTV.setText(cat);
                Glide.with(getApplicationContext()).load(img_name).into(img);
                priceTV.setText("Rs. " + price);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        quant_btn = findViewById(R.id.quant_btn);

        addtocart = findViewById(R.id.addtoCartbtn);

        quantity = quant_btn.getNumber();

        quant_btn.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                quantity = quant_btn.getNumber();

            }
        });


        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ProductDetail", "onClick: "+status);

                if(status.equals("not shipped")) {
                    Toast.makeText(ProductDetailsActivity.this,"You can place your order after the order has been placed",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    AddtoCart();
                }
            }
        });
    }

    private void AddtoCart() {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String date = simpleDateFormat.format(calendar.getTime());
        final Map<String, Object> map = new HashMap<>();
        map.put("cartid", date);
        map.put("pid", id);
        map.put("pname", name);
        map.put("price", price);
        map.put("description", desc);
        map.put("quantity", quantity);
        map.put("img_name", img_name);
        map.put("cat_name", cat);


        catRef.child("User Cart").child(number).child("Carts").child(date).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    catRef.child("Admin Cart").child(number).child("Carts").child(date).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(ProductDetailsActivity.this, "Successfukky added to cart", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(ProductDetailsActivity.this, UserHome.class));
                                finish();

                            } else {
                                Toast.makeText(ProductDetailsActivity.this, "Failed added to add to cart", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {

                }

            }
        });

    }
}
