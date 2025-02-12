package cn.bw.bean;

import cn.bw.OrderSide;

public class Order {

    private int id;
    private OrderSide side;
    private double price;
    private int quantity;

    public void setSide(OrderSide side) {
        this.side = side;
    }

    public OrderSide getSide() {
        return side;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
