package cn.bw.service;

import cn.bw.model.Order;
import cn.bw.model.OrderBook;
import cn.bw.model.OrderSide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * simple matching engine
 *
 */
@Service
public class MatchingEngine {
    private static final Logger log = LoggerFactory.getLogger(MatchingEngine.class);

    private final OrderBook orderBook = new OrderBook();

    public void processOrder(Order order){
        log.info("MatchingEngine.processOrder orderId:{}",order.getId());
        if(order.getSide()== OrderSide.BUY){
            matchBuyOrder(order);
        }else {
            matchSellOrder(order);
        }
    }

    public void printOrderBook(){
        log.warn("--------- print order book ------------");
        log.warn("--------- sell order, price from low to high ------------");

        log.warn("--------- buy order price from high to low ------------");
    }


    private void matchBuyOrder(Order buyOrder){
        TreeMap<Double, Queue<Order>> sellOrders = orderBook.getSellOrders();

        while (buyOrder.getQuantity()>0 && !sellOrders.isEmpty()){
            Map.Entry<Double,Queue<Order>> bestSellEntry = sellOrders.firstEntry();
            double bestSellPrice = bestSellEntry.getKey();
            //如果最低卖价 依然高于 买价，则无法成交
            if(bestSellPrice>buyOrder.getPrice())
                break;

            Queue<Order> sellQueue = bestSellEntry.getValue();
            while (buyOrder.getQuantity()>0 && !sellQueue.isEmpty()){
                Order sellOrder = sellQueue.peek();

                int matchQuantity = Math.min(sellOrder.getQuantity(), buyOrder.getQuantity());
                log.info("matchBuyOrder,buy_order_id:{},sell_order_id:{},match_quantity:{}",buyOrder.getId(),sellOrder.getId(),matchQuantity);

                int boQ = buyOrder.getQuantity()-matchQuantity;
                int soQ = sellOrder.getQuantity()-matchQuantity;

                buyOrder.setQuantity(boQ);
                sellOrder.setQuantity(soQ);

                if(sellOrder.getQuantity()==0){
                    sellQueue.poll();
                }
            }

            if(sellQueue.isEmpty()){
                sellOrders.remove(bestSellPrice);
            }
        }

        if(buyOrder.getQuantity()>0){
            log.info("matchBuyOrder add to book, buy_order_id:{},quantity:{}",buyOrder.getId(),buyOrder.getQuantity());
            orderBook.addOrder(buyOrder);
        }
    }

    private void matchSellOrder(Order sellOrder){
        TreeMap<Double,Queue<Order>> buyOrders = orderBook.getBuyOrders();

        while (sellOrder.getQuantity()>0 && !buyOrders.isEmpty()){

            Map.Entry<Double,Queue<Order>> buyEntry = buyOrders.firstEntry();
            double buyPrice = buyEntry.getKey();

            if(buyPrice< sellOrder.getPrice())break;

            Queue<Order> buyQueue = buyEntry.getValue();
            while (sellOrder.getQuantity()>0 && !buyQueue.isEmpty()){
                Order buyOrder = buyQueue.peek();
                int matchQuantity = Math.min(sellOrder.getQuantity(), buyOrder.getQuantity());
                log.info("matchSellOrder,sell_order_id:{},buy_order_id:{},match_quantity:{}",sellOrder.getId(),buyOrder.getId(),matchQuantity);

                int soq = sellOrder.getQuantity()-matchQuantity;
                int boq = buyOrder.getQuantity() -matchQuantity;

                sellOrder.setQuantity(soq);
                buyOrder.setQuantity(boq);

                if(buyOrder.getQuantity()==0){
                    buyQueue.poll();
                }
            }

            if(buyQueue.isEmpty()){
                buyOrders.remove(buyPrice);
            }
        }

        if(sellOrder.getQuantity()>0){
            log.info("matchSellOrder add to book, sell_order_id:{},quantity:{}",sellOrder.getId(),sellOrder.getQuantity());
            orderBook.addOrder(sellOrder);
        }
    }




}
