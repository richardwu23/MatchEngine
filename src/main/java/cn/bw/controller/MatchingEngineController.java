package cn.bw.controller;

import cn.bw.model.Order;
import cn.bw.model.OrderSide;
import cn.bw.service.MatchingEngine;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchingEngineController {

    @Resource
    private MatchingEngine matchingEngine;

    private static final Logger log = LoggerFactory.getLogger(MatchingEngineController.class);

    @RequestMapping("/m")
    public String testMatch(){
        testAddSomeOrders();
        return "done";
    }


    @RequestMapping("/printOB")
    public String printOB(){
        matchingEngine.printOrderBook();
        return "print orderBook";

    }


    private void testAddSomeOrders(){
        matchingEngine.processOrder(new Order(OrderSide.SELL,1,10.0,110));
        matchingEngine.processOrder(new Order(OrderSide.SELL,2,8.0,120));
        matchingEngine.processOrder(new Order(OrderSide.SELL,3,9.5,50));
        matchingEngine.processOrder(new Order(OrderSide.SELL,4,15.0,20));
        matchingEngine.processOrder(new Order(OrderSide.SELL,5,6.0,10));

        matchingEngine.processOrder(new Order(OrderSide.BUY,6,11.0,90));
        matchingEngine.processOrder(new Order(OrderSide.BUY,7,2.0,60));
        matchingEngine.processOrder(new Order(OrderSide.BUY,8,1.0,100));
        matchingEngine.processOrder(new Order(OrderSide.BUY,9,8.0,30));
        matchingEngine.processOrder(new Order(OrderSide.BUY,10,10.0,10));
    }

}
