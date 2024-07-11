package services;

import interfaces.CRUD;
import models.Order;
import interfaces.CRUD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderService implements CRUD<Order> {
    private Map<String, Order> orders = new HashMap<>();

    @Override
    public void create(Order order) {
        orders.put(order.getOrderId(), order);
    }

    @Override
    public Order read(String id) {
        return orders.get(id);
    }

    @Override
    public void update(Order order) {
        orders.put(order.getOrderId(), order);
    }

    @Override
    public void delete(String id) {
        orders.remove(id);
    }

    @Override
    public List<Order> getAll() {
        return orders.values().stream().collect(Collectors.toList());
    }

    public void save(Order order) {
    }

    public List<Order> getAll(int userId) {
        return null;
    }
}
