package business;

import models.Product;
import services.ProductService;
import utils.IOFile;
import utils.InputUtil;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserBusiness {
    private static ProductService productService = new ProductService();
    public static void addToCart() throws IOException {
        try {
            System.out.println("---------- Add to Cart ----------");

            // Retrieve and display available products
            List<Product> products = productService.getAll();
            if (products.isEmpty()) {
                System.out.println("No products available to add to cart.");
                return;
            }

            System.out.println("Products available:");
            for (Product product : products) {
                System.out.println("ID: " + product.getProductId() + ", Name: " + product.getProductName() + ", Price: " + product.getProductPrice());
            }

            // Get product ID from user input
            int productId = Integer.parseInt(InputUtil.getString("Enter product ID to add to cart: "));

            // Retrieve product details from productService
            Product product = productService.read(String.valueOf(productId));

            if (product == null) {
                System.out.println("Product with ID " + productId + " does not exist.");
                return;
            }
            // Create cart entry information
            String cartInfo = product.getProductId() + "," + product.getProductName() + "," + product.getProductPrice();
            // Write cart entry to CART_PATH
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(IOFile.CART_PATH, true))) {
                writer.write(cartInfo);
                writer.newLine();
                System.out.println("Product added to cart successfully.");
            } catch (IOException e) {
                System.err.println("Error writing to cart file: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid product ID format. Please enter a valid number.");
        } catch (Exception e) {
            System.err.println("An error occurred while adding product to cart.");
            e.printStackTrace();
        }
    }
    public static Map<String, Object> viewOrders(boolean doOrder) {
        Map<String, Integer> cart = new HashMap<>();
        Map<String, Double> productTotalAmount = new HashMap<>();
        double grandTotal = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader(IOFile.CART_PATH))) {
            String line;
            boolean cartEmpty = true; // Biến kiểm tra giỏ hàng có trống không
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) { // Dữ liệu chỉ có 3 phần: productId, productName, productPrice
                    String productId = parts[0];
                    String productName = parts[1]; // Sử dụng productName
                    double price = Double.parseDouble(parts[2]); // Lấy giá sản phẩm

                    // Kiểm tra nếu sản phẩm đã tồn tại trong giỏ hàng
                    if (cart.containsKey(productName)) {
                        // Tăng số lượng sản phẩm lên 1
                        cart.put(productName, cart.get(productName) + 1);
                    } else {
                        // Thêm sản phẩm mới vào giỏ hàng với số lượng 1
                        cart.put(productName, 1);
                    }

                    // Cập nhật tổng tiền cho sản phẩm
                    productTotalAmount.put(productName, productTotalAmount.getOrDefault(productName, 0.0) + price);
                    // Cập nhật tổng tiền cho tất cả các sản phẩm
                    grandTotal += price;
                    cartEmpty = false; // Có ít nhất một sản phẩm trong giỏ hàng
                }
            }

            // Kiểm tra nếu giỏ hàng rỗng, hiển thị thông báo và trả về giỏ hàng rỗng
            if (cartEmpty) {
                System.out.println("Giỏ hàng của bạn hiện đang trống.");
                return Collections.emptyMap();
            }

            // Hiển thị thông tin giỏ hàng
            System.out.println("Giỏ hàng:");
            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                String productName = entry.getKey();
                int quantity = entry.getValue();
                double totalAmount = productTotalAmount.get(productName);
                System.out.println("Sản phẩm: " + productName + ", Số lượng: " + quantity + ", Tổng tiền: " + totalAmount);
            }

            // Hiển thị tổng tiền của tất cả các sản phẩm
            System.out.println("Tổng tiền của tất cả các sản phẩm: " + grandTotal);
            System.out.println("Bạn có muốn đặt hàng không? (yes/no)");
            String choice = InputUtil.getString("Nhập lựa chọn: ");
            if (choice.equalsIgnoreCase("yes")) {
                doOrder = true;
            } else {
                doOrder = false;
            }
            if (doOrder) {
                // Lưu thông tin giỏ hàng vào file order
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(IOFile.ORDER_PATH, true))) {
                    for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                        String productName = entry.getKey();
                        int quantity = entry.getValue();
                        double totalAmount = productTotalAmount.get(productName);
                        writer.write(productName + "," + quantity + "," + totalAmount);
                        writer.newLine();
                    }
                    System.out.println("Đặt hàng thành công.");
                } catch (IOException e) {
                    System.err.println("Lỗi khi ghi vào file order: " + e.getMessage());
                }
            } else {
                System.out.println("Không đặt hàng.");
            }
            // Xóa thông tin giỏ hàng trong file cart
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(IOFile.CART_PATH))) {
                writer.write("");
                System.out.println("Xóa giỏ hàng thành công.");
            } catch (IOException e) {
                System.err.println("Lỗi khi xóa file cart: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc dữ liệu từ file cart: " + e.getMessage());
        }

        // Nếu không order hoặc có lỗi xảy ra, trả về thông tin giỏ hàng như ban đầu
        Map<String, Object> result = new HashMap<>();
        result.put("cart", cart);
        result.put("productTotalAmount", productTotalAmount);
        result.put("grandTotal", grandTotal);
        return result;
    }
}
