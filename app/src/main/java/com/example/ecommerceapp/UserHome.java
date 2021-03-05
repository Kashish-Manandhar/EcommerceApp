package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecommerceapp.Adapters.CartAdapter;
import com.example.ecommerceapp.Adapters.UserOrderAdapter;
import com.google.android.material.navigation.NavigationView;

public class UserHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;

//    @Override
//    public void onClick(int positon) {
//        Toast.makeText(this,positon+"was clicked",Toast.LENGTH_SHORT).show();
//    }

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Fragment fragment;
    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        toolbar=findViewById(R.id.userhome_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Home");
        Intent intent=getIntent();
        number=intent.getStringExtra("number");
        drawerLayout=findViewById(R.id.drawerLayout);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        getSupportActionBar().setHomeButtonEnabled(true);

        navigationView=findViewById(R.id.navigation_view);
        View view=navigationView.getHeaderView(0);

        Bundle bundle=new Bundle();
        bundle.putString("number",number);
        fragment=new HomeFragment();
        fragment.setArguments(bundle);


        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container,fragment).commit();

        navigationView.setNavigationItemSelectedListener(this);




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


                startActivity(new Intent(UserHome.this,LoginActivity.class));
                finish();
                return true;
//            case R.id.cart:
//
//                getSupportFragmentManager().beginTransaction().replace(R.id.container,new CartFragment()).addToBackStack(null).commit();
//
//                return true;

            default:
                return false;

        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.men_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack(null).commit();
                return true;

            case R.id.cart_men:

                getSupportFragmentManager().beginTransaction().replace(R.id.container,new CartFragment()).addToBackStack(null).commit();

                return true;

            case R.id.orders:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new UserOrder()).addToBackStack(null).commit();
                return true;




            default:
            return false;
        }
        }



//    @Override
//    public void onClickListener(int position) {
//
//
//
//    }
}