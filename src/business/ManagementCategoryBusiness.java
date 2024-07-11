package business;

import models.Category;
import utils.IOFile;
import utils.InputUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ManagementCategoryBusiness {
    private static services.CategoryService categoryService = new services.CategoryService();

    //ADD CATEGORY
    public static void addCategory() {
        try {
            // Enter category information from the user
            System.out.println("---------- Add Category ----------");
            String name = InputUtil.getString("Enter category name: ");

            // Check if the name is not empty
            if (name == null || name.trim().isEmpty()) {
                System.err.println("Category name cannot be empty.");
                return;
            }

            // Create a category object
            Category category = new Category();
            category.setCategoryName(name);

            // Ensure the service is initialized
            if (categoryService == null) {
                System.err.println("Category service is not initialized.");
                return;
            }

            // Get the current list of categories
            List<Category> categories = categoryService.getAll();

            // Find the highest id and increment it by 1 for the new category id
            int maxId = 0;
            for (Category cat : categories) {
                int catId = Integer.parseInt(cat.getCategoryId());
                if (catId > maxId) {
                    maxId = catId;
                }
            }
            // Increment the highest id by 1 for the new category id
            String newCategoryId = String.valueOf(maxId + 1);
            category.setCategoryId(Integer.parseInt(newCategoryId));

            // Add the category to the service
            categoryService.create(category);
            System.out.println("Category added successfully!");

            // Save the category to a file
            IOFile<Category> categoryIOFile = new IOFile<>();
            File file = new File(IOFile.CATEGORY_PATH);
            File dir = file.getParentFile();

            // Create directory if it doesn't exist
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    System.out.println("Directory created: " + dir.getPath());
                } else {
                    System.err.println("Unable to create directory: " + dir.getPath());
                    return;
                }
            }

            // Create file if it doesn't exist
            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        System.out.println("File created: " + file.getPath());
                    }
                } catch (IOException e) {
                    System.err.println("Unable to create file: " + file.getPath());
                    e.printStackTrace();
                    return;
                }
            }

            // Read the category list from the file
            try {
                categories = categoryIOFile.readFromFile(IOFile.CATEGORY_PATH);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Unable to read categories from file");
                e.printStackTrace();
                return;
            }

            // Add the new category to the list
            categories.add(category);

            // Write the updated category list to the file
            try {
                categoryIOFile.writeToFile(IOFile.CATEGORY_PATH, categories);
                System.out.println("Category list saved to file successfully!");
            } catch (IOException e) {
                System.err.println("Unable to write category list to file");
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.err.println("An error occurred while adding the category");
            e.printStackTrace();
        }
    }


    //FIND BY ID
    public static void findById() {
        String id = InputUtil.getString("Enter category ID to find: ");
        Category category = categoryService.read(id);
        if (category != null) {
            System.out.println("ID: " + category.getCategoryId() + ", Name: " + category.getCategoryName());
        } else {
            System.out.println("Category with ID " + id + " not found.");
        }
    }


    //VIEW ALL CATEGORY
    public static void viewCategories() {
        List<Category> categories = categoryService.getAll();
        if (categories.isEmpty()) {
            System.out.println("No categories available.");
        } else {
            System.out.println("ID\tName");
            for (Category category : categories) {
                System.out.println(category.getCategoryId() + "\t" + category.getCategoryName());
            }
        }
    }


    //UPDATE CATEGORY
    public static void updateCategory() {
        IOFile<Category> categoryIOFile = new IOFile<>();
        File file = new File(IOFile.CATEGORY_PATH);
        // Ensure the file exists
        if (!file.exists()) {
            System.out.println("Category file does not exist.");
            return;
        }
        try {
            List<Category> categories = categoryIOFile.readFromFile(IOFile.CATEGORY_PATH);
            String id = InputUtil.getString("Enter the ID of the category to update: ");
            Category category = categoryService.read(id);
            if (category != null) {
                String name = InputUtil.getString("Enter the new category name: ");
                category.setCategoryName(name);
                categoryService.update(category);
                // Update the category in the list
                for (int i = 0; i < categories.size(); i++) {
                    if (categories.get(i).getCategoryId().equals(id)) {
                        categories.set(i, category);
                        break;
                    }
                }
                // Write the updated list to the file
                categoryIOFile.writeToFile(IOFile.CATEGORY_PATH, categories);
                System.out.println("Category updated successfully and saved to file!");
            } else {
                System.out.println("Category not found");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
            e.printStackTrace();
        }
    }


    //Delete Category
    public static void deleteCategory() {
        String id = InputUtil.getString("Enter the ID of the category to delete: ");
        Category category = categoryService.read(id);
        if (category != null) {
            // Delete the category
            categoryService.delete(id);
            System.out.println("Category deleted successfully.");

            // Optionally, remove from file if needed
            IOFile<Category> categoryIOFile = new IOFile<>();
            try {
                List<Category> categories = categoryIOFile.readFromFile(IOFile.CATEGORY_PATH);
                categories.removeIf(c -> c.getCategoryId().equals(id));
                categoryIOFile.writeToFile(IOFile.CATEGORY_PATH, categories);
                System.out.println("Category removed from file.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error updating file after deletion: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No category found with ID " + id);
        }
    }
}
