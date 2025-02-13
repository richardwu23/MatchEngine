package cn.bw.model;

import lombok.Data;

@Data
public class Order {

    private int id;
    private OrderSide side;
    private double price;
    private int quantity;

    public Order(OrderSide side,int id,double price, int quantity){
        this.side =side;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

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
