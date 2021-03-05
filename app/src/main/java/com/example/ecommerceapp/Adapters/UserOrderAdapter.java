package com.example.ecommerceapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.ModelClass.Cart;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.UserHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.UserOrderViewHolder> {

    Context context;
    List<Cart> cartList;
    String num;
    FirebaseDatabase database=FirebaseDatabase.getInstance();


    public UserOrderAdapter(Context context, List<Cart> cartList,String num) {
        this.context = context;
        this.cartList = cartList;
        this.num=num;
    }

    @NonNull
    @Override
    public UserOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cart, parent, false);
        return new UserOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserOrderViewHolder holder, final int position) {

        Cart cart = cartList.get(position);
        Glide.with(context).load(cart.getImg_name()).into(holder.img);
        holder.t1.setText(cart.getPname());
        holder.t2.setText(cart.getDescription());
        holder.t3.setText("Rs " + cart.getPrice());
        holder.t4.setText(cart.getQuantity());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Has your product arrived ?");

                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;

                    }
                }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        shipmentdelete();



                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class UserOrderViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView t1, t2, t3, t4;

        public UserOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cart_img);
            t1 = itemView.findViewById(R.id.cart_pname);
            t2 = itemView.findViewById(R.id.cart_pdesc);
            t3 = itemView.findViewById(R.id.cart_price);
            t4 = itemView.findViewById(R.id.car_quantity);

        }
    }


    void shipmentdelete()
    {
        database.getReference().child("Orders").child("User Orders").child(num).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if(task.isComplete())
              {
                  database.getReference().child("Carts").child("Admin Cart").child(num).removeValue()
                          .addOnCompleteListener(new OnCompleteListener<Void>() {
                              @Override
                              public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isComplete())
                                   {
                                       context.startActivity(new Intent(context, UserHome.class));
                                   }
                              }
                          });
              }
            }
        });
    }

}
