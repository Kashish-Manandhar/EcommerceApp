package com.example.ecommerceapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecommerceapp.Adapters.ProductAdapter;
import com.example.ecommerceapp.ModelClass.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    String number;
    List<Product> products;
    ProductAdapter adapter;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        number=getArguments().getString("number");


        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.product_list);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        products=new ArrayList<>();
        adapter=new ProductAdapter(getActivity().getApplicationContext(),products);

        recyclerView.setAdapter(adapter);

        firebaseDatabase.getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                products.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {

                    String id,name,cat,desc,img;
                    double price;
                    int quant;
                    id=dataSnapshot1.getKey();
                    name=dataSnapshot1.child("prodName").getValue().toString();
                    cat=dataSnapshot1.child("catName").getValue().toString();
                    desc=dataSnapshot1.child("prodDesc").getValue().toString();
                    quant=Integer.parseInt(dataSnapshot1.child("prodQuant").getValue().toString());
                    price=Double.parseDouble(dataSnapshot1.child("prodPrice").getValue().toString());
                    img=dataSnapshot1.child("prodImg").getValue().toString();


                    Product product=new Product(name,desc,img,cat,id,quant,price);









                    products.add(product);
                    adapter.notifyDataSetChanged();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        return view;

    }
}