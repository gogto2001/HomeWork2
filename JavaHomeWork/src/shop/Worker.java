package shop;

import java.util.concurrent.BlockingQueue;

public class Worker implements Runnable {
    private final BlockingQueue<Order> orderQueue;
    private final WareHouse warehouse;

    public Worker(BlockingQueue<Order> orderQueue, WareHouse warehouse) {
        this.orderQueue = orderQueue;
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Order order = orderQueue.take();
                warehouse.processOrder(order);
            }
        } catch (InterruptedException e) {
            System.out.println("Worker interrupted. Stopping gracefully...");
            Thread.currentThread().interrupt();
        }
    }
}



