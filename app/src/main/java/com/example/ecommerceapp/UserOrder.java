package com.example.ecommerceapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerceapp.Adapters.UserOrderAdapter;
import com.example.ecommerceapp.ModelClass.Cart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserOrder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserOrder extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserOrder() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserOrder.
     */
    // TODO: Rename and change types and number of parameters
    public static UserOrder newInstance(String param1, String param2) {
        UserOrder fragment = new UserOrder();
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
    SharedPreferences sharedpreferences;

    FirebaseDatabase datbase;
    List<Cart> cartList;
    RecyclerView recyclkerview;

    UserOrderAdapter adapter;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_user_order, container, false);
        sharedpreferences=getActivity().getSharedPreferences("User_details", Context.MODE_PRIVATE);
        datbase=FirebaseDatabase.getInstance();
        recyclkerview=view.findViewById(R.id.user_order_recycler);

        recyclkerview.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));




        number=sharedpreferences.getString("number","");

        cartList=new ArrayList<>();


        adapter=new UserOrderAdapter(getActivity().getApplicationContext(),cartList,number);



        recyclkerview.setAdapter(adapter);

        datbase.getReference().child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    recyclkerview.setVisibility(View.VISIBLE);
                }
                else
                {
                    recyclkerview.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        datbase.getReference().child("Carts").child("Admin Cart").child(number).child("Carts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    cartList.clear();
                    for(DataSnapshot ds:dataSnapshot.getChildren())
                    {

                        Cart cart=ds.getValue(Cart.class);

                        cartList.add(cart);

                    }

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