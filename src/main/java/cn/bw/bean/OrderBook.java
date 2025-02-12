package cn.bw.bean;

import cn.bw.OrderSide;
import cn.bw.bean.Order;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;

public class OrderBook {

    private final TreeMap<Double, Queue<Order>> buyOrders = new TreeMap<>(Collections.reverseOrder());
    private final TreeMap<Double, Queue<Order>> sellOrders = new TreeMap<>();

    public TreeMap<Double, Queue<Order>> getBuyOrders() {
        return buyOrders;
    }

    public TreeMap<Double, Queue<Order>> getSellOrders() {
        return sellOrders;
    }


    public void addOrder(Order order) {
        if (order.getSide() == OrderSide.BUY) {
            buyOrders.computeIfAbsent(order.getPrice(), i->new LinkedList<>()).add(order);
        } else {
            sellOrders.computeIfAbsent(order.getPrice(),i->new LinkedList<>()).add(order);
        }
    }
}
