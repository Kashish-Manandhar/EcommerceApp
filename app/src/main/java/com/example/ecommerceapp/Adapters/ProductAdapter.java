package com.example.ecommerceapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.ModelClass.Product;
import com.example.ecommerceapp.ProductDetailsActivity;
import com.example.ecommerceapp.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    public interface OnItemClick
    {
        void onCLick(int position);
    }


    Context context;
    List<Product> products;


    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;



    }

    @NonNull
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_product,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, final int position) {

        final Product product=products.get(position);

        holder.pname.setText(product.getProdName());
        Glide.with(context).load(product.getProdImg()).into(holder.pimg);

        holder.pprice.setText("Rs. "+product.getProdPrice());
        holder.pdesc.setText(product.getProdDesc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent product_intent=new Intent(context, ProductDetailsActivity.class);
                product_intent.putExtra("prod_id",product.getProdId());
                product_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(product_intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView pname,pdesc,pprice;

        ImageView pimg;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            pname=itemView.findViewById(R.id.pname);
            pdesc=itemView.findViewById(R.id.pdesc);
            pprice=itemView.findViewById(R.id.pprice);
            pimg=itemView.findViewById(R.id.pimg);


        }
    }
}
