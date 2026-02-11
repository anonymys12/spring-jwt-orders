package com.example.springjwtorders.controller;

import com.example.springjwtorders.entity.Order;
import com.example.springjwtorders.entity.OrderStatus;
import com.example.springjwtorders.entity.User;
import com.example.springjwtorders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestAttribute("currentUser") User currentUser) {
        return orderService.createOrder(currentUser);
    }

    @PutMapping("/{id}/status")
    public Order changeStatus(@PathVariable Long id,
                              @RequestParam OrderStatus status,
                              @RequestAttribute("currentUser") User currentUser) {
        return orderService.changeStatus(id, status, currentUser);
    }

    @GetMapping
    public Page<Order> listOrders(@RequestParam int page,
                                  @RequestParam int size,
                                  @RequestParam(defaultValue = "id") String sortField,
                                  @RequestParam(defaultValue = "true") boolean asc) {
        PageRequest pageable = PageRequest.of(page, size, asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        return orderService.listOrders(pageable);
    }
}
