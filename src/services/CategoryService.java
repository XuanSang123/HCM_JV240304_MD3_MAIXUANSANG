package services;
import interfaces.CRUD;
import models.Category;
import utils.IOFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryService implements CRUD<Category> {
    private Map<String, Category> categories = new HashMap<>();

    public CategoryService() {
        loadCategoriesFromFile(); // Load categories from file when the service is initialized
    }

    @Override
    public void create(Category category) {
        categories.put(category.getCategoryId(), category);
        saveCategoriesToFile(); // Save categories to file after adding a new category
    }

    @Override
    public Category read(String id) {
        return categories.get(id);
    }

    @Override
    public void update(Category category) {
        categories.put(category.getCategoryId(), category);
        saveCategoriesToFile(); // Save categories to file after updating a category
    }

    @Override
    public void delete(String id) {
        categories.remove(id);
        saveCategoriesToFile(); // Save categories to file after deleting a category
    }

    @Override
    public List<Category> getAll() {
        return categories.values().stream().collect(Collectors.toList());
    }

    private void loadCategoriesFromFile() {
        IOFile<Category> categoryIOFile = new IOFile<>();
        try {
            List<Category> loadedCategories = categoryIOFile.readFromFile(IOFile.CATEGORY_PATH);
            for (Category category : loadedCategories) {
                categories.put(category.getCategoryId(), category);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Unable to read categories from file");
            e.printStackTrace();
        }
    }

    private void saveCategoriesToFile() {
        IOFile<Category> categoryIOFile = new IOFile<>();
        try {
            categoryIOFile.writeToFile(IOFile.CATEGORY_PATH, getAll());
            System.out.println("Categories saved to file successfully!");
        } catch (IOException e) {
            System.err.println("Unable to write categories to file");
            e.printStackTrace();
        }
    }
}
