package com.example.test.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders o WHERE o.item = :item AND o.created > current_timestamp - interval '10 minutes'" +
            " AND o.quantity > 0 ORDER BY o.price LIMIT 1;",
            nativeQuery = true)
    Order findByItem(@Param("item") String item);
}
