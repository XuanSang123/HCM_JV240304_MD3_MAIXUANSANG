import business.*;
import models.Order;
import models.User;
import services.OrderService;
import utils.IOFile;
import utils.InputUtil;
import java.io.*;
import java.util.*;



public class Main {
    private static ManagementCategoryBusiness managementCategoryBusiness = new ManagementCategoryBusiness();
    private static ManagementProductBusiness managementProductBusiness = new ManagementProductBusiness();
    private static ManagementUserBusiness managementUserBusiness = new ManagementUserBusiness();
    private static UserBusiness userBusiness = new UserBusiness();
    private static ManagementOrderBusiness managementOrderBusiness = new ManagementOrderBusiness();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("1. Admin login");
            System.out.println("2. Register");
            System.out.println("3. Login");
            System.out.println("4. Exit");
            int choice = Integer.parseInt(InputUtil.getString("Enter choice: "));
            switch (choice) {
                case 1:
                    loginAdmin();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    login();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //-----------------ADMIN MENU-----------------

    private static void adminMenu() throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("----------Admin Menu----------");
            System.out.println("1. Manage Categories");
            System.out.println("2. Manage Products");
            System.out.println("3. Manage Users");
            System.out.println("4. Manage Orders");
            System.out.println("5. Logout");
            int choice = Integer.parseInt(InputUtil.getString("Enter choice: "));
            switch (choice) {
                case 1:
                    manageCategories();
                    break;
                case 2:
                    manageProducts();
                    break;
                case 3:
                    manageUsers();
                    break;
                case 4:
                    manageOrders();
                    break;
                case 5:
                    System.out.println("Logout successful!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //-----------------USER MENU-----------------

    private static void userMenu() throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("----------User Menu----------");
            System.out.println("1. User Information");
            System.out.println("2. View Products");
            System.out.println("3. Add To Cart");
            System.out.println("4. View Orders");
            System.out.println("5. Logout");
            int choice = Integer.parseInt(InputUtil.getString("Enter choice: "));
            switch (choice) {
                case 1:
                    viewUserInfo();
                    break;
                case 2:
                    managementProductBusiness.viewAllProducts();
                    break;
                case 3:
                    userBusiness.addToCart();
                    break;
                case 4:
                    userBusiness.viewOrders(true);
                    break;
                case 5:
                    System.out.println("Logout successful!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //-----------------CATEGORY MANAGEMENT-----------------
    public static void manageCategories() throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("----------Manage Categories----------");
            System.out.println("1. Add Category");
            System.out.println("2. Find Category by ID");
            System.out.println("3. View Categories");
            System.out.println("4. Update Category");
            System.out.println("5. Delete Category");
            System.out.println("6. Back to Admin Menu");
            int choice = Integer.parseInt(InputUtil.getString("Enter choice: "));
            switch (choice) {
                case 1:
                    managementCategoryBusiness.addCategory();
                    break;
                case 2:
                    managementCategoryBusiness.findById();
                    break;
                case 3:
                    managementCategoryBusiness.viewCategories();
                    break;
                case 4:
                    managementCategoryBusiness.updateCategory();
                    break;
                case 5:
                    managementCategoryBusiness.deleteCategory();
                    break;
                case 6:
                    System.out.println("Back to Admin Menu");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //-----------------PRODUCT MANAGEMENT-----------------
    private static void manageProducts() throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("-----------Manage Products-----------");
            System.out.println("1. Add Product");
            System.out.println("2. Find By Id");
            System.out.println("3. View Products");
            System.out.println("4. Update Product");
            System.out.println("5. Delete Product");
            System.out.println("6. Back to Admin Menu");
            int choice = Integer.parseInt(InputUtil.getString("Enter choice: "));
            switch (choice) {
                case 1:
                    managementProductBusiness.addProduct();
                    break;
                case 2:
                    managementProductBusiness.findProductById();
                    break;
                case 3:
                    managementProductBusiness.viewAllProducts();
                    break;
                case 4:
                    managementProductBusiness.updateProduct();
                    break;
                case 5:
                    managementProductBusiness.deleteProduct();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //-----------------USER MANAGEMENT-----------------
    private static void manageUsers() {
        while (true) {
            System.out.println("-----------Manage Users-----------");
            System.out.println("1. View Users");
            System.out.println("2. Update Status User");
            System.out.println("3. Find Users By Name");
            System.out.println("4. Back to Admin Menu");
            int choice = Integer.parseInt(InputUtil.getString("Enter choice: "));
            switch (choice) {
                case 1:
                    managementUserBusiness.viewAllUsers();
                    break;
                case 2:
                    managementUserBusiness.updateStatus();
                    break;
                case 3:
                    managementUserBusiness.findUsersByName();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //-----------------ORDER MANAGEMENT-----------------
    private static void manageOrders() {
        while (true) {
            System.out.println("----------Manage Orders----------");
            System.out.println("1. View Orders");
            System.out.println("2. Delete Order");
            System.out.println("3. Back to Admin Menu");
            int choice = Integer.parseInt(InputUtil.getString("Enter choice: "));
            switch (choice) {
                case 1:
                    managementOrderBusiness.viewAllOrders();
                    break;
                case 2:
                    managementOrderBusiness.deleteOrder();
                    break;
                case 3:
                    managementOrderBusiness.updateStatusOrder();
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //-----------VIEW USER INFO-----------
    public static void viewUserInfo() throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("-----------USER INFORMATION-----------");
            System.out.println("1. View User");
            System.out.println("2. Edit User");
            System.out.println("3. Back to User Menu");
            int choice = Integer.parseInt(InputUtil.getString("Enter choice: "));
            switch (choice) {
                case 1:
//                    userInfo();
                    break;
                case 2:
//                    editUser();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    //-----------LOGIN ADMIN-----------
    public static void loginAdmin() throws IOException, ClassNotFoundException {
        System.out.println("----------Welcome Admin----------");
        String adminName = InputUtil.getString("Enter admin name: ");
        String adminPassword = InputUtil.getString("Enter admin password: ");
        if (adminName.equals("admin") && adminPassword.equals("admin")) {
            System.out.println("Login successful!");
            adminMenu();
        } else {
            System.out.println("Login failed. Please try again.");
        }
    }


    //----------REGISTER USER-------------
    public static void register() {
        try {
            System.out.println("---------- Register ----------");
            String name = InputUtil.getString("Enter your name: ");
            String email = InputUtil.getString("Enter your email: ");
            String password = InputUtil.getString("Enter your password: ");
            String phone = InputUtil.getString("Enter your phone number: ");
            String address = InputUtil.getString("Enter your address: ");

            // Validate input fields
            if (name.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty() || phone.trim().isEmpty() || address.trim().isEmpty()) {
                System.err.println("All fields are required.");
                return;
            }

            // Read users from file
            IOFile<User> userIO = new IOFile<>();
            List<User> users = new ArrayList<>();
            try {
                users = userIO.readFromFile(IOFile.USER_PATH);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Unable to read users from file.");
                e.printStackTrace();
            }

            // Check if email already exists
            for (User u : users) {
                if (u.getEmail().equals(email)) {
                    System.out.println("Email already exists. Please use a different email.");
                    return;
                }
            }

            // Find the highest id and increment it by 1 for the new user id
            int maxId = 0;
            for (User usr : users) {
                if (usr.getId() > maxId) {
                    maxId = usr.getId();
                }
            }
            // Increment the highest id by 1 for the new user id
            int newUserId = maxId + 1;

            // Create new user and add to list
            User user = new User(newUserId, name, email, password, phone, address);
            users.add(user);
            System.out.println("Register successful!");

            // Save users to file
            try {
                userIO.writeToFile(IOFile.USER_PATH, users);
                System.out.println("Users have been written to file: " + IOFile.USER_PATH);
            } catch (IOException e) {
                System.err.println("Unable to write users to file.");
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("An error occurred during registration.");
            e.printStackTrace();
        }
    }


    //----------LOGIN USER-------------------
    public static void login() {
        try {
            System.out.println("---------- Login ----------");
            String email = InputUtil.getString("Enter your email: ");
            String password = InputUtil.getString("Enter your password: ");

            // Validate input fields
            if (email.trim().isEmpty() || password.trim().isEmpty()) {
                System.err.println("Email and password are required.");
                return;
            }

            // Read users from file
            IOFile<User> userIO = new IOFile<>();
            List<User> users = userIO.readFromFile(IOFile.USER_PATH);

            // Check if users exist
            if (users == null) {
                System.err.println("No users found. Please register first.");
                return;
            }
            // Validate login credentials
            boolean loggedIn = false;
            for (User user : users) {
                if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    loggedIn = true;
                    System.out.println("Login successful!");
                    userMenu();
                    break;
                }
            }
            if (!loggedIn) {
                System.err.println("Invalid email or password. Please try again.");
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("An error occurred during login.");
            e.printStackTrace();
        }
    }
}


