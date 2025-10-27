//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
package shop;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String[] args) throws InterruptedException {


        Product p1 = new Product("Laptop", 1200.0, 10);
        Product p2 = new Product("Smartphone", 800.0, 15);
        Product p3 = new Product("Headphones", 150.0, 20);
        Product p4 = new Product("Keyboard", 100.0, 30);
        Product[] products = {p1, p2, p3, p4};

        WareHouse warehouse = new WareHouse();
        for (Product p : products) {
            warehouse.addProduct(p, p.getQuantity());
        }


        BlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(50);

        Analytics analytics = new Analytics(warehouse);

        Thread customer1 = new Thread(new Customer("Alice", orderQueue, products));
        Thread customer2 = new Thread(new Customer("Bob", orderQueue, products));
        Thread customer3 = new Thread(new Customer("Charlie", orderQueue, products));


        Thread worker1 = new Thread(() -> {
            Worker worker = new Worker(orderQueue, warehouse);
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Order order = orderQueue.take();
                    warehouse.processOrder(order);
                    analytics.recordOrder(order);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread worker2 = new Thread(() -> {
            Worker worker = new Worker(orderQueue, warehouse);
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Order order = orderQueue.take();
                    warehouse.processOrder(order);
                    analytics.recordOrder(order);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });


        customer1.start();
        customer2.start();
        customer3.start();
        worker1.start();
        worker2.start();


        customer1.join();
        customer2.join();
        customer3.join();

        worker1.interrupt();
        worker2.interrupt();
        worker1.join();
        worker2.join();

        warehouse.printStock();
        analytics.printAnalytics();
    }
}
