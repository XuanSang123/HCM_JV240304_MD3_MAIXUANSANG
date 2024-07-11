package business;

import models.Order;
import services.OrderService;
import utils.IOFile;
import utils.InputUtil;

import java.io.IOException;
import java.util.List;

public class ManagementOrderBusiness {
    private static OrderService orderService = new OrderService();
    public static void viewAllOrders() {
        try {
            IOFile<Order> orderIO = new IOFile<>();
            List<Order> orders = orderIO.readFromFile(IOFile.ORDER_PATH);

            if (orders == null || orders.isEmpty()) {
                System.out.println("No orders available.");
                return;
            }

            System.out.println("---------- Orders ----------");
            for (Order order : orders) {
                System.out.println("Name product: " + order.getProductName());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("An error occurred while reading orders from file.");
            e.printStackTrace();
        }
    }

    public static void deleteOrder() {
        String id = InputUtil.getString("Enter order ID to delete: ");
        orderService.delete(id);
        System.out.println("Order delete successfully. !!!");
    }

    public static void updateStatusOrder() {
        String id = InputUtil.getString("Enter order ID to update: ");
        Order order = orderService.read(id);
        if (order != null) {
            boolean status = InputUtil.getBoolean("Enter new status (true/false): ");
            order.setStatus(status);
            orderService.update(order);
            System.out.println("Status update successfully. !!!");
        } else {
            System.out.println("Order not found");
        }
    }
}
