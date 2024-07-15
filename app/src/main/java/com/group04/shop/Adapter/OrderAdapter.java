package com.group04.shop.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group04.shop.Domain.Order;
import com.group04.shop.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvPurchaseDate, tvQuantity, tvPaymentMethod;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPurchaseDate = itemView.findViewById(R.id.tvPurchaseDate);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPaymentMethod = itemView.findViewById(R.id.tvPaymentMethod);
        }

        public void bind(Order order) {
            tvProductName.setText("Product Name: " + order.getProductName());
            tvPurchaseDate.setText("Purchase Date: " + order.getOrderDate());
            tvQuantity.setText("Quantity: " + order.getQuantity());
            tvPaymentMethod.setText("Payment Method: " + order.getPaymentMethod());
        }
    }
}