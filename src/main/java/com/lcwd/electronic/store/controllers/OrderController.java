package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.entities.Order;
import com.lcwd.electronic.store.services.OrderService;
import com.lcwd.electronic.store.services.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //create
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderDto order = orderService.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId) {
        orderService.removeOrder(orderId);

        ApiResponseMessage response = ApiResponseMessage.builder().status(HttpStatus.OK).message("order is removed").success(true).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //get orders of the user
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId) {
            List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(userId);
            return new ResponseEntity<>(ordersOfUser, HttpStatus.OK);
    }



    @GetMapping()
    public ResponseEntity<PageableResponse<OrderDto>> getOrders(
                @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
                @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
                @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
                @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ) {
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable String orderId, @RequestBody CreateOrderRequest request){
        OrderDto orderDto = orderService.updateOrder(request, orderId);
        return new ResponseEntity<>(orderDto,HttpStatus.OK);
    }

}
