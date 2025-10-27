package shop;

import shop.Order;
import shop.Product;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WareHouse {
    private final Map<Product, Integer> stock = new ConcurrentHashMap<>();
    private double totalProfit = 0.0;

    public void addProduct(Product product, int quantity) {
        stock.put(product, quantity);
    }
    public synchronized void processOrder(Order order) {
        Product product = order.getProduct();
        int orderedQty = order.getQuantity();

        if (stock.containsKey(product)) {
            int available = stock.get(product);

            if (available >= orderedQty) {
                stock.put(product, available - orderedQty);

                double profit = product.getPrice() * orderedQty;
                totalProfit += profit;

                System.out.println(order.getCustomerName() + " successfully bought "
                        + orderedQty + " x " + product.getName());
            } else {
                System.out.println("Not enough stock for " + product.getName()
                        + " (requested: " + orderedQty + ", available: " + available + ")");
            }
        } else {
            System.out.println("shop.Product not found: " + product.getName());
        }
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void printStock() {
        System.out.println("\nCurrent warehouse stock:");
        stock.forEach((p, q) -> System.out.println(p.getName() + ": " + q + " pcs"));
    }
}
