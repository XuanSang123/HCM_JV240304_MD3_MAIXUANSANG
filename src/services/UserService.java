package services;

import interfaces.CRUD;
import models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService implements CRUD<User> {
    private Map<String, User> users = new HashMap<>();

    @Override
    public void create(User user) {
        users.put(String.valueOf(user.getId()),user);
    }

    @Override
    public User read(String id) {
        return users.get(id);
    }

    @Override
    public void update(User user) {
        users.put(String.valueOf(user.getId()), user);
    }

    @Override
    public void delete(String id) {
        users.remove(id);
    }

    @Override
    public List<User> getAll() {
        return users.values().stream().collect(Collectors.toList());
    }
    public List<User> getAllUsers() {
        return (List<User>) this.users;
    }
}