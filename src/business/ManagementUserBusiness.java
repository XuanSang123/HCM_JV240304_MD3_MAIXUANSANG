package business;

import models.User;
import services.UserService;
import utils.IOFile;
import utils.InputUtil;

import java.io.IOException;
import java.util.List;

public class ManagementUserBusiness {
    private static UserService userService = new UserService();
    public static void viewAllUsers() {
        try {
            IOFile<User> userIO = new IOFile<>();
            List<User> users = userIO.readFromFile(IOFile.USER_PATH);

            if (users == null || users.isEmpty()) {
                System.out.println("No users registered yet.");
                return;
            }

            System.out.println("---------- Registered Users ----------");
            for (User user : users) {
                System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail()
                        + ", Phone: " + user.getPhone() + ", Address: " + user.getAddress());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("An error occurred while reading users from file.");
            e.printStackTrace();
        }
    }
    public static void findUsersByName() {
        try {
            String name = InputUtil.getString("Enter name to search for: ");
            IOFile<User> userIO = new IOFile<>();
            List<User> users = userIO.readFromFile(IOFile.USER_PATH);

            if (users == null || users.isEmpty()) {
                System.out.println("No users registered yet.");
                return;
            }

            System.out.println("---------- Users Found ----------");
            for (User user : users) {
                if (user.getName().equalsIgnoreCase(name.trim())) {
                    System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail()
                            + ", Phone: " + user.getPhone() + ", Address: " + user.getAddress());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("An error occurred while searching for users.");
            e.printStackTrace();
        }
    }
    public static void updateStatus(){

    }
}
