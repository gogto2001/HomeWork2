package shop;

import shop.Product;

public class Order {
    private String customerName;
    private Product product;
    private int quantity;

    public Order(String customerName, Product product, int quantity) {
        this.customerName = customerName;
        this.product = product;
        this.quantity = quantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "shop.Order{" +
                "customerName='" + customerName + '\'' +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}