package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Delivery {
    int deliveryId;
    private Order order;
    private Driver driver;
    private LocalDateTime time;

    public Delivery(int deliveryId, Order order, Driver driver, LocalDateTime time) {
        this.deliveryId = deliveryId;
        this.order = order;
        this.driver = driver;
        this.time = time;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void printDelivery()
    {
        order.printOrder();
        driver.printDriver();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("Time: " + time.format(formatter));
    }
}



