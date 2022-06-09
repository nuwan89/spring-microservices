package com.zirconlabz.ordermanager;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrdersRepository repository;
    private AccountFeignClient accountClient;

    public OrderService(OrdersRepository repository, AccountFeignClient accountClient) {
        this.repository = repository;
        this.accountClient = accountClient;
    }

    public List<OrderEntity> getOrders() {
        return repository.findAll();
    }

    public OrderEntity submitOrder(OrderEntity order){
        int nano = order.getCreated().toInstant().getNano();
        System.out.println("Order nano: "+nano);
        int verified = accountClient.verify(nano);
        System.out.println("Verfied Order: "+verified);
        return repository.save(order);
    }

    @HystrixCommand(fallbackMethod = "failingFallBack")
    public String failingRemoteCall() {
        return this.accountClient.remoteError();
    }

    public String failingFallBack() {
        return "REMOTE UNAVAILABLE";
    }
}
