package com.example.ecommerceapp.Adapters;

import android.content.Context;
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

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CaretViewHolder> {
    Context context;
    List<Cart> cartList;


    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;

    }

    @NonNull
    @Override
    public CartAdapter.CaretViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cart, parent, false);
        return new CaretViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CaretViewHolder holder, final int position) {
              Cart cart=cartList.get(position);

              holder.price.setText("Rs "+cart.getPrice());
              holder.name.setText(cart.getPname());
              holder.quantity.setText(cart.getQuantity());
              holder.desc.setText(cart.getDescription());
              holder.cat.setText(cart.getCat_name());
              Glide.with(context).load(cart.getImg_name()).into(holder.img);



    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CaretViewHolder extends RecyclerView.ViewHolder {
        TextView price, name, desc, quantity,cat;
        ImageView img;
        public CaretViewHolder(@NonNull View itemView) {
            super(itemView);


            price=itemView.findViewById(R.id.cart_price);
            name=itemView.findViewById(R.id.cart_pname);
            desc=itemView.findViewById(R.id.cart_pdesc);
            quantity=itemView.findViewById(R.id.car_quantity);
            cat=itemView.findViewById(R.id.cart_cat);
            img=itemView.findViewById(R.id.cart_img);

        }
    }
}
