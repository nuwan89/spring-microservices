package com.zirconlabz.ordermanager;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersResource {

    private OrderService orderService;

    public OrdersResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public List<OrderEntity> getOrders() {
        System.out.println("Get Orders");
        return orderService.getOrders();
    }

    @GetMapping("/{order_id}")
    public String getOrder(@PathVariable("order_id") int orderId){
        return "Order: " + orderId;
    }

    @PostMapping()
    public String submitOrder() {
        return "saved!";
    }

    @GetMapping("/test")
    public OrderEntity _submitOrder() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCreated(new Date());
        return orderService.submitOrder(orderEntity);
    }
}
