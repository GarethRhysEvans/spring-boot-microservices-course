package org.devit.bookstore.orders.domain;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepository extends JpaRepository<@NotNull OrderEntity, @NotNull Long> { }
