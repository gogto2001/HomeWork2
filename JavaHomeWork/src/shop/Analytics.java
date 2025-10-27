package shop;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Analytics {

    private final WareHouse warehouse;
    private final Map<Product, Integer> productSales = new ConcurrentHashMap<>();
    private int totalOrders = 0;

    public Analytics(WareHouse warehouse) {
        this.warehouse = warehouse;
    }

    public synchronized void recordOrder(Order order) {
        totalOrders++;
        Product product = order.getProduct();
        int qty = order.getQuantity();
        productSales.merge(product, qty, Integer::sum);
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public double getTotalProfit() {
        return warehouse.getTotalProfit();
    }


    public List<Map.Entry<Product, Integer>> getTopSellingProducts(int topN) {
        List<Map.Entry<Product, Integer>> list = new ArrayList<>(productSales.entrySet());
        list.sort((a, b) -> b.getValue().compareTo(a.getValue())); // descending order
        return list.subList(0, Math.min(topN, list.size()));
    }


    public void printAnalytics() {
        System.out.println("\n--- Analytics ---");
        System.out.println("Total orders processed: " + getTotalOrders());
        System.out.println("Total profit: $" + getTotalProfit());

        System.out.println("Top selling products:");
        List<Map.Entry<Product, Integer>> topProducts = getTopSellingProducts(3);
        for (Map.Entry<Product, Integer> entry : topProducts) {
            System.out.println(entry.getKey().getName() + " - " + entry.getValue() + " pcs sold");
        }
    }
}

