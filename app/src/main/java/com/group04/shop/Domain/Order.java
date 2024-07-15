package com.group04.shop.Domain;

public class Order {
    private String orderId;
    private String productName;
    private String orderDate;
    private int quantity;
    private String paymentMethod;

    private String orderPic;

    // Required default constructor
    public Order() {
        // Default constructor required for calls to DataSnapshot.getValue(Order.class)
    }

    public Order(String orderId, String productName, String orderDate, int quantity, String paymentMethod, String orderPic) {
        this.orderId = orderId;
        this.productName = productName;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.paymentMethod = paymentMethod;
        this.orderPic = orderPic;
    }

    // Getters and setters (if not already defined)
    // ...

    public String getOrderPic() {
        return orderPic;
    }

    public void setOrderPic(String orderPic) {
        this.orderPic = orderPic;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}