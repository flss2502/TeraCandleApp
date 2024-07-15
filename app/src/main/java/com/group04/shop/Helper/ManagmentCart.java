package com.group04.shop.Helper;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group04.shop.Domain.ItemsDomain;
import com.group04.shop.Domain.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ManagmentCart {

    private Context context;
    private TinyDB tinyDB;
    private DatabaseReference ordersRef; // Firebase Realtime Database reference

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
        this.ordersRef = FirebaseDatabase.getInstance("https://shopping-56bed-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("orders"); // Firebase orders reference
    }

    public void insertItem(ItemsDomain item) {
        ArrayList<ItemsDomain> listitem = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int y = 0; y < listitem.size(); y++) {
            if (listitem.get(y).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = y;
                break;
            }
        }
        if (existAlready) {
            listitem.get(n).setNumberinCart(item.getNumberinCart());
        } else {
            listitem.add(item);
        }
        tinyDB.putListObject("CartList", listitem);

        // Create and save order to Firebase
        createAndSaveOrder(item);

        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }

    private void createAndSaveOrder(ItemsDomain item) {
        // Create a new order object
        String orderId = ordersRef.push().getKey(); // Generate unique order ID
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        String orderPic = String.valueOf(item.getPicUrl());

        Order order = new Order(orderId, item.getTitle(), currentDate, item.getNumberinCart(), "Zalo Pay", orderPic);

        // Save order to Firebase Realtime Database
        ordersRef.child(orderId).setValue(order);
    }

    public ArrayList<ItemsDomain> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public void minusItem(ArrayList<ItemsDomain> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listItem.get(position).getNumberinCart() == 1) {
            listItem.remove(position);
        } else {
            listItem.get(position).setNumberinCart(listItem.get(position).getNumberinCart() - 1);
        }
        tinyDB.putListObject("CartList", listItem);
        changeNumberItemsListener.changed();
    }

    public void plusItem(ArrayList<ItemsDomain> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listItem.get(position).setNumberinCart(listItem.get(position).getNumberinCart() + 1);
        tinyDB.putListObject("CartList", listItem);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee() {
        ArrayList<ItemsDomain> listfood2 = getListCart();
        double fee = 0;
        for (int i = 0; i < listfood2.size(); i++) {
            fee = fee + (listfood2.get(i).getPrice() * listfood2.get(i).getNumberinCart());
        }
        return fee;
    }
}