package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminHome extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    String cat_name;
    ImageView shoes,sports,thshirt,laptop,mobile,glass,femaledress,purse;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        toolbar=findViewById(R.id.admin_toolbar);
        btn=findViewById(R.id.adminhome_orderbtn);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin Page");
        shoes=findViewById(R.id.shoes);
        thshirt=findViewById(R.id.tshirts);
        laptop=findViewById(R.id.laptops);
        sports=findViewById(R.id.sports);
        mobile=findViewById(R.id.mobiles);
        glass=findViewById(R.id.glasses);
        femaledress=findViewById(R.id.female_dresses);
        purse=findViewById(R.id.purses);


        shoes.setOnClickListener(this);
        thshirt.setOnClickListener(this);
        purse.setOnClickListener(this);
        sports.setOnClickListener(this);
        femaledress.setOnClickListener(this);
        laptop.setOnClickListener(this);
        mobile.setOnClickListener(this);
        glass.setOnClickListener(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminHome.this,AdminOrder.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;

}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
            case R.id.logout:
                SharedPreferences sharedPreferences=getSharedPreferences("User_details",MODE_PRIVATE);
                SharedPreferences sharedPreferences1=getSharedPreferences("IsLogged_details", Context.MODE_PRIVATE);
                sharedPreferences1.edit().clear().commit();
                sharedPreferences.edit().clear().commit();
                startActivity(new Intent(this,LoginActivity.class));
                finish();

                return true;

            default:
                return false;

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tshirts:
                cat_name="tshirts";
                break;
            case R.id.shoes:
                cat_name="shoes";
                break;
            case R.id.female_dresses:
                cat_name="female_dress";
                break;
            case R.id.laptops:
                cat_name="laptops";
                break;
            case R.id.mobiles:
                cat_name="mobiles";
                break;

            case R.id.purses:
                cat_name="purses";
                break;
            case R.id.sports:
                cat_name="sports";
                break;
            case R.id.glasses:
                cat_name="glasses";
                break;
            default:
                break;
        }

        Intent intent1=new Intent(this,AddProductActivity.class);
        intent1.putExtra("category",cat_name);
        startActivity(intent1);
    }
}