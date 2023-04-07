public class Delivery {
    private Order order;
    private Driver driver;
    private TimeStamp time;

    public Delivery(Order order, Driver driver, TimeStamp time) {
        this.order = order;
        this.driver = driver;
        this.time = time;
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

    public TimeStamp getTime() {
        return time;
    }

    public void setTime(TimeStamp time) {
        this.time = time;
    }

    public void printDelivery()
    {
        order.printOrder();
        driver.printDriver();
        time.printTime();
    }
}



