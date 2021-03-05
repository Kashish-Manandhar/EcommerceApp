package com.example.ecommerceapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShipmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShipmentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShipmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShipmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShipmentFragment newInstance(String param1, String param2) {
        ShipmentFragment fragment = new ShipmentFragment();
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
    EditText nameET,numberET,locationET;

    Button btn;
    FirebaseDatabase database; 
    DatabaseReference adminorder_ref,cart_ref,user_order_ref;
    double totalprice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        totalprice=getArguments().getDouble("total_price");
        View view=inflater.inflate(R.layout.fragment_shipment, container, false);

        database=FirebaseDatabase.getInstance();
        nameET=view.findViewById(R.id.shipment_name);
        numberET=view.findViewById(R.id.shipment_number);
        locationET=view.findViewById(R.id.shipment_location);

        btn=view.findViewById(R.id.shipment_order);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });



        return view;

    }

    private void placeOrder() {
        final String curr_usernum=getActivity().getSharedPreferences("User_details", Context.MODE_PRIVATE).getString("number","");
         String name,number,location;
         name=nameET.getText().toString();
         number=numberET.getText().toString();

         location=locationET.getText().toString();

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curr_date=simpleDateFormat.format(calendar.getTime());
        final Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("number",number);
        map.put("location",location);
        map.put("total_price",totalprice);
        map.put("status","not shipped");

        adminorder_ref=database.getReference().child("Orders").child("Admin Orders");
        user_order_ref=database.getReference().child("Orders").child("User Orders");
        cart_ref=database.getReference().child("Carts");
        adminorder_ref.child(curr_usernum).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if(task.isSuccessful())
              {
                  user_order_ref.child(curr_usernum).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                          if(task.isSuccessful())
                          {
                              cart_ref.child("User Cart").child(curr_usernum).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {
                                      if(task.isSuccessful())
                                      {
                                          Bundle bundle=new Bundle();
                                          bundle.putString("number",curr_usernum);

                                          Fragment framgent=new HomeFragment();
                                          framgent.setArguments(bundle);

                                          FragmentManager manager=getActivity().getSupportFragmentManager();
                                          manager.beginTransaction().replace(R.id.container,framgent).commit();
                                      }
                                  }
                              });
                          }

                      }
                  });

              }
            }
        });




    }
}