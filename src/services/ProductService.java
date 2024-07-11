package services;
import interfaces.CRUD;
import models.Product;
import utils.IOFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ProductService implements CRUD<Product> {
    private Map<String, Product> products = new HashMap<>();

    public ProductService() {
        loadProductsFromFile(); // Load products from file when the service is initialized
    }

    @Override
    public void create(Product product) {
        products.put(String.valueOf(product.getProductId()), product);
        saveProductsToFile(); // Save products to file after adding a new product
    }

    @Override
    public Product read(String id) {
        return products.get(id);
    }

    @Override
    public void update(Product product) {
        products.put(String.valueOf(product.getProductId()), product);
        saveProductsToFile(); // Save products to file after updating a product
    }

    @Override
    public void delete(String id) {
        products.remove(id);
        saveProductsToFile(); // Save products to file after deleting a product
    }

    @Override
    public List<Product> getAll() {
        return products.values().stream().collect(Collectors.toList());
    }

    private void loadProductsFromFile() {
        IOFile<Product> productIOFile = new IOFile<>();
        try {
            List<Product> loadedProducts = productIOFile.readFromFile(IOFile.PRODUCT_PATH);
            for (Product product : loadedProducts) {
                products.put(String.valueOf(product.getProductId()), product);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Unable to read products from file");
            e.printStackTrace();
        }
    }

    public void saveProductsToFile() {
        IOFile<Product> productIOFile = new IOFile<>();
        try {
            productIOFile.writeToFile(IOFile.PRODUCT_PATH, getAll());
            System.out.println("Products saved to file successfully!");
        } catch (IOException e) {
            System.err.println("Unable to write products to file");
            e.printStackTrace();
        }
    }
}
