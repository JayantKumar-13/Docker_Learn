package com.jayant.InventoryService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "Order-Service", path = "/orders")
public interface OrdersFeignClient {
    
    @GetMapping("/core/helloOrders")
    String helloOrders();
    
}
