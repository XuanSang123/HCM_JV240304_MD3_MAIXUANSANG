package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFile<T> {
    public static final String USER_PATH = "data/user.txt";
    public static final String CATEGORY_PATH = "data/category.txt";
    public static final String PRODUCT_PATH = "data/product.txt";
    public static final String ORDER_PATH = "data/order.txt";
    public static final String CART_PATH = "data/cart.txt";
    public List<T> readFromFile(String filePath) throws IOException, ClassNotFoundException {
        File file = new File(filePath);
        if (file.length() == 0) {
            return new ArrayList<>();
        }
        List<T> objects;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            objects = (List<T>) ois.readObject();
        }
        return objects;
    }

    public void writeToFile(String filePath, List<T> objects) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(objects);
        }
    }
}