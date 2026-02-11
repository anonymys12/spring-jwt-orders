package com.example.springjwtorders.service;

import com.example.springjwtorders.entity.Order;
import com.example.springjwtorders.entity.OrderStatus;
import com.example.springjwtorders.entity.Role;
import com.example.springjwtorders.entity.User;
import com.example.springjwtorders.exception.AccessDeniedException;
import com.example.springjwtorders.exception.InvalidOrderStateException;
import com.example.springjwtorders.exception.NotFoundException;
import com.example.springjwtorders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(User owner) {
        Order order = Order.builder()
                .owner(owner)
                .status(OrderStatus.CREATED)
                .build();
        return orderRepository.save(order);
    }

    public Order changeStatus(Long orderId, OrderStatus newStatus, User currentUser) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        if (!order.getOwner().getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Only owner or admin can change status");
        }

        if (order.getStatus() == OrderStatus.PAID && newStatus == OrderStatus.CREATED) {
            throw new InvalidOrderStateException("Cannot change PAID → CREATED");
        }

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    public Page<Order> listOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
}
