package com.example.ecommerceapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.ModelClass.Order;
import com.example.ecommerceapp.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    Context context;
    List<Order> orders;
    OrdeVIew orderVIew;

    public interface OrdeVIew{
        public void onView(int postion);
        public void onClick(int position);
    }
    public OrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
        orderVIew=(OrdeVIew) context;
    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_order,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        Order order=orders.get(position);
        holder.name.setText(order.getName());
        holder.price.setText("Rs "+ order.getTotal_price());
        holder.location.setText(order.getLocation());
        holder.number.setText(order.getNumber());


    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView name,price,location,number;
        Button btn;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.adminorder_name);
            price=itemView.findViewById(R.id.adminorder_price);
            location=itemView.findViewById(R.id.adminorder_location);
            number=itemView.findViewById(R.id.adminorder_number);
            btn=itemView.findViewById(R.id.adminorder_view);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    orderVIew.onView(getAdapterPosition());

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderVIew.onClick(getAdapterPosition());
                }
            });

        }
    }

}
