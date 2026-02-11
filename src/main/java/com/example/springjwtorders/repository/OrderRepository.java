package com.example.springjwtorders.repository;

import com.example.springjwtorders.entity.Order;
import com.example.springjwtorders.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByOwner(User owner, Pageable pageable);
}
