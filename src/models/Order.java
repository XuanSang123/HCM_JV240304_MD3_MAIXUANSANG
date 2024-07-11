package models;

import java.util.Date;
import java.util.List;

public class Order {
    private String orderId;
    private String userId;
    private List<Product> products;
    private double totalAmount;
    private Date date;
    private boolean status;

    // Constructor, getters, and setters

    public Order(String orderId, String userId, List<Product> products, double totalAmount, Date date) {
        this.orderId = orderId;
        this.userId = userId;
        this.products = products;
        this.totalAmount = totalAmount;
    }
    public Order() {}

    public Order(int productId, String productName, double productPrice) {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void addProduct(Product product) {
    }

    public String getProductName() {
        return null;
    }

    public String getProductPrice() {
        return null;
    }
}
