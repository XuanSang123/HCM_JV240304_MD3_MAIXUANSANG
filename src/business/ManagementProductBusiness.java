package business;

import models.Category;
import models.Product;
import services.ProductService;
import utils.IOFile;
import utils.InputUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static utils.IOFile.PRODUCT_PATH;

public class ManagementProductBusiness {
    private static services.CategoryService categoryService = new services.CategoryService();
    private static ProductService productService = new ProductService();

    //ADD PRODUCT
    public static void addProduct() {
        try {
            System.out.println("---------- Add Product ----------");

            String name = InputUtil.getString("Enter product name: ");
            String description = InputUtil.getString("Enter product description: ");
            double price = Double.parseDouble(InputUtil.getString("Enter product price: "));
            int quantity = Integer.parseInt(InputUtil.getString("Enter product quantity: "));

            Category category = selectCategory();

            if (category == null) {
                System.err.println("Category not selected or does not exist.");
                return;
            }

            Product product = new Product(name, description, price, quantity, category);

            if (productService == null) {
                System.err.println("Product service is not initialized.");
                return;
            }

            productService.create(product);
            System.out.println("Product added successfully!");

            IOFile<Product> productIOFile = new IOFile<>();
            File file = new File(PRODUCT_PATH);
            File dir = file.getParentFile();

            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    System.out.println("Directory created: " + dir.getPath());
                } else {
                    System.err.println("Unable to create directory: " + dir.getPath());
                    return;
                }
            }

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

            try {
                List<Product> products = productIOFile.readFromFile(PRODUCT_PATH);
                products.add(product);
                productIOFile.writeToFile(PRODUCT_PATH, products);
                System.out.println("Product list saved to file successfully!");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Unable to read or write product list to file: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.err.println("An error occurred while adding the product");
            e.printStackTrace();
        }
    }
    private static Category selectCategory() {
        List<Category> categories = categoryService.getAll();
        if (categories.isEmpty()) {
            System.err.println("No categories available.");
            return null;
        }

        System.out.println("---------- Select Category ----------");
        System.out.println("ID\tName");
        for (Category category : categories) {
            System.out.println(category.getCategoryId() + "\t" + category.getCategoryName());
        }

        String categoryId = InputUtil.getString("Enter category ID: ");
        for (Category category : categories) {
            if (category.getCategoryId().equals(categoryId)) {
                return category;
            }
        }

        System.err.println("Category not found with ID: " + categoryId);
        return null;
    }
    //DISPLAY PRODUCT
    public static void viewAllProducts() {
        List<Product> products = productService.getAll();
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Product product : products) {
                System.out.println("ID: " + product.getProductId());
                System.out.println("Name: " + product.getProductName());
                System.out.println("Description: " + product.getProductDes());
                System.out.println("Price: " + product.getProductPrice());
                System.out.println("Quantity: " + product.getQuantity());
                System.out.println("Category: " + product.getCategory().getCategoryName());
            }
        }
    }

    public static void findProductById() {
        String id = InputUtil.getString("Nhập ID sản phẩm để tìm: ");
        Product product = productService.read(id);
        if (product != null) {
            System.out.println("ID: " + product.getProductId() +
                    ", Tên: " + product.getProductName() +
                    ", Giá: " + product.getProductPrice() +
                    ", Số lượng: " + product.getQuantity() +
                    ", Mô tả: " + product.getProductDes() +
                    ", ID danh mục: " + product.getCategory().getCategoryName());
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID " + id + ".");
        }
    }


    //UPDATE PRODUCT
    public static void updateProduct() {
        IOFile<Product> productIOFile = new IOFile<>();
        File file = new File(PRODUCT_PATH);
        if (!file.exists()) {
            System.out.println("Product file does not exist.");
            return;
        }
        try {
            List<Product> products = productIOFile.readFromFile(PRODUCT_PATH);
            String id = InputUtil.getString("Enter the ID of the product to update: ");
            Product product = productService.read(id);
            if (product != null) {
                String name = InputUtil.getString("Enter the new product name: ");
                if (!name.isEmpty()) {
                    product.setProductName(name);
                }
                String description = InputUtil.getString("Enter the new product description: ");
                if (!description.isEmpty()) {
                    product.setProductDes(description);
                }
                String priceStr = InputUtil.getString("Enter the new product price: ");
                if (!priceStr.isEmpty()) {
                    double price = Double.parseDouble(priceStr);
                    product.setProductPrice(price);
                }
                String quantityStr = InputUtil.getString("Enter the new product quantity: ");
                if (!quantityStr.isEmpty()) {
                    int quantity = Integer.parseInt(quantityStr);
                    product.setQuantity(quantity);
                }
                Category category = selectCategory();
                if (category != null) {
                    product.setCategory(category);
                }
                productService.update(product);
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getProductId() == product.getProductId()) {
                        products.set(i, product);
                        break;
                    }
                }
                productIOFile.writeToFile(PRODUCT_PATH, products);
                System.out.println("Product updated successfully and saved to file!");
            } else {
                System.out.println("Product not found");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //DELETE PRODUCT
    public static void deleteProduct() {
        String id = InputUtil.getString("Enter the ID of the product to delete: ");
        Product product = productService.read(id);
        if (product != null) {
            productService.delete(id);
            System.out.println("Product deleted successfully.");

            IOFile<Product> productIOFile = new IOFile<>();
            try {
                List<Product> products = productIOFile.readFromFile(PRODUCT_PATH);
                products.removeIf(p -> p.getProductId() == product.getProductId());
                productIOFile.writeToFile(PRODUCT_PATH, products);
                System.out.println("Product removed from file.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error updating file after deletion: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No product found with ID " + id);
        }
    }

    //Save product to file
    public static void saveProductsToFile() {
        IOFile<Product> productIOFile = new IOFile<>();
        try {
            productIOFile.writeToFile(PRODUCT_PATH, productService.getAll());
            System.out.println("Danh sách sản phẩm đã được lưu vào file thành công!");
        } catch (IOException e) {
            System.err.println("Không thể lưu danh sách sản phẩm vào file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
