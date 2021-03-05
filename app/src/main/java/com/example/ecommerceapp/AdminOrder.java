package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.ecommerceapp.Adapters.OrderAdapter;
import com.example.ecommerceapp.ModelClass.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminOrder extends AppCompatActivity implements OrderAdapter.OrdeVIew {
    RecyclerView orderrecyclerView;
    OrderAdapter orderAdapter;
    RecyclerView.LayoutManager manager;
    List<Order> orders;
    FirebaseDatabase database;
    DatabaseReference ref;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order);
        toolbar = findViewById(R.id.admin_order_tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Orders Admin");
        orderrecyclerView = findViewById(R.id.order_recyclerview);
        manager = new LinearLayoutManager(this);
        orderrecyclerView.setLayoutManager(manager);
        orders = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        orderAdapter = new OrderAdapter(this, orders);
        ref = database.getReference().child("Orders").child("Admin Orders");
        orderrecyclerView.setAdapter(orderAdapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orders.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String name, location, curr_user_id, number, status;
                    double total_price;
                    name = dataSnapshot1.child("name").getValue().toString();
                    location = dataSnapshot1.child("location").getValue().toString();
                    number = dataSnapshot1.child("number").getValue().toString();
                    total_price = Double.parseDouble(dataSnapshot1.child("total_price").getValue().toString());
                    status = dataSnapshot1.child("status").getValue().toString();
                    curr_user_id = dataSnapshot1.getKey();

                    orders.add(new Order(location, name, number, status, curr_user_id, total_price));
                    orderAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onView(int postion) {
        Intent intent = new Intent(this, AdminProductView.class);
        intent.putExtra("number", orders.get(postion).getCur_user_num());
        startActivity(intent);
    }

    @Override
    public void onClick(int position) {
        final String curr_id = orders.get(position).getCur_user_num();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Have you already shipped the orders or not?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.getReference().child("Orders").child("Admi" +
                        "n Orders").child(curr_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Map<String,Object> map=new HashMap<>();
                            map.put("status","shipped");
                            database.getReference().child("Orders").child("User Orders").child(curr_id).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    startActivity(new Intent(AdminOrder.this, AdminHome.class));
                                    finish();
                                }
                            });

                        }
                    }
                });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}