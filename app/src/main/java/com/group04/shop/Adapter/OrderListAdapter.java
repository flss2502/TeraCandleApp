package com.group04.shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.group04.shop.Domain.Order;
import com.group04.shop.R;

import java.util.List;

public class OrderListAdapter extends ArrayAdapter<Order> {
    private Context context;
    private List<Order> orderList;

    public OrderListAdapter(Context context, List<Order> orderList) {
        super(context, 0, orderList);
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.order_list, parent, false);
        }

        Order currentOrder = orderList.get(position);

        ImageView pic = listItemView.findViewById(R.id.pic);
        TextView tvProductName = listItemView.findViewById(R.id.tvProductName);
        TextView tvPurchaseDate = listItemView.findViewById(R.id.tvPurchaseDate);
        TextView tvQuantity = listItemView.findViewById(R.id.tvQuantity);
        TextView tvPaymentMethod = listItemView.findViewById(R.id.tvPaymentMethod);

        tvProductName.setText("Sản phẩm: " + currentOrder.getProductName());
        tvPurchaseDate.setText(currentOrder.getOrderDate());
        tvQuantity.setText("Số lượng: " + currentOrder.getQuantity());
        tvPaymentMethod.setText("Thanh toán: " + currentOrder.getPaymentMethod());

        Glide.with(context)
                .load(currentOrder.getOrderPic())
                .into(pic);

        return listItemView;
    }
}