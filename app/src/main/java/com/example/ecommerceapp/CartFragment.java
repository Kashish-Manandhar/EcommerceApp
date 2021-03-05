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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Adapters.CartAdapter;
import com.example.ecommerceapp.ModelClass.Cart;
import com.example.ecommerceapp.ModelClass.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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

    RecyclerView recyclerView;
    String number;
    DatabaseReference databaseReference;
    List<Cart> cartList;
    CartAdapter adapter;
    Button button;

//    public interface OnItemClickListener{
//        void onClick(int positon);
//    }
//    OnItemClickListener mclick;
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        try {
//            mclick=(OnItemClickListener) context;
//        }
//        catch (ClassCastException e)
//        {
//            throw new ClassCastException(context.toString()+ "must be implemented");
//        }

//    }
    DatabaseReference shipment_ref;
    TextView msg;
    double totalprice=0;
    TextView error_msg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("User_details", Context.MODE_PRIVATE);
        recyclerView = view.findViewById(R.id.cart_list);
        cartList = new ArrayList<>();
        msg=view.findViewById(R.id.msg_text_cart);
        number = sharedPreferences.getString("number", "");

        adapter = new CartAdapter(getActivity(), cartList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        msg.setVisibility(View.GONE);
        button=view.findViewById(R.id.buy_order);
        error_msg=view.findViewById(R.id.error_msg);
        error_msg.setVisibility(View.GONE);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Carts");
        shipment_ref=FirebaseDatabase.getInstance().getReference();
        shipment_ref.child("Orders").child("User Orders").child(number).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.child("status").getValue(String.class).equals("not shipped"))
                    {

                        msg.setVisibility(View.VISIBLE);
                        button.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    else
                    {

                        msg.setVisibility(View.GONE);
                        button.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("User Cart").child(number).child("Carts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren())
                {

                    cartList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Cart cart = dataSnapshot1.getValue(Cart.class);

                        cartList.add(cart);


                    }
                    adapter.notifyDataSetChanged();
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });











        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<cartList.size();i++)
                {
                    Cart cart=cartList.get(i);
                    totalprice+=cart.getPrice()*(Integer.parseInt(cart.getQuantity()));
                    Bundle bundle=new Bundle();
                    bundle.putDouble("total_price",totalprice);
                    Fragment fragment=new ShipmentFragment();
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
                }

            }
        });
        return view;

    }

}