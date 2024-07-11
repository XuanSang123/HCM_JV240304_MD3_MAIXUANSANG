package models;

import java.util.List;

public class Cart {
    private int userId;
    private List<Product> products;

    // Constructor, getters, and setters

    public Cart(int userId, List<Product> products) {
        this.userId = userId;
        this.products = products;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
