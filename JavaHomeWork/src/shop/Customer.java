package shop;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Customer implements Runnable {

    private final String name;
    private final BlockingQueue<Order> orderQueue;
    private final Product[] products;
    private final Random random = new Random();

    public Customer(String name, BlockingQueue<Order> orderQueue, Product[] products) {
        this.name = name;
        this.orderQueue = orderQueue;
        this.products = products;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 3; i++) {
                placeRandomOrder();
                Thread.sleep(400 + random.nextInt(300)); // მცირე პაუზა (400-700 ms)
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(name + " stopped ordering.");
        }
    }

    private void placeRandomOrder() throws InterruptedException {
        Product product = products[random.nextInt(products.length)];
        int quantity = random.nextInt(3) + 1;

        Order order = new Order(name, product, quantity);
        orderQueue.put(order);

        System.out.println(name + " placed order: " + order);
    }
}
