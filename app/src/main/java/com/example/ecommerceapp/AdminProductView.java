package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.ecommerceapp.Adapters.CartAdapter;
import com.example.ecommerceapp.ModelClass.Cart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminProductView extends AppCompatActivity {
    RecyclerView recyclerView;
    CartAdapter adapter;
    List<Cart> cartList;
    FirebaseDatabase database;
    DatabaseReference cartRef;
    String curr_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_view);
         Intent intent=getIntent();
         recyclerView=findViewById(R.id.adminproduct_recyclerview);
        database=FirebaseDatabase.getInstance();
        cartRef=database.getReference().child("Carts").child("Admin Cart");
        curr_number=intent.getExtras().getString("number");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartList=new ArrayList<>();
        adapter=new CartAdapter(this,cartList);

        recyclerView.setAdapter(adapter);
        cartRef.child(curr_number).child("Carts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               cartList.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
               {
                   Cart car=dataSnapshot1.getValue(Cart.class);
                   cartList.add(car);
                   adapter.notifyDataSetChanged();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}