package com.example.test.order;

import com.example.test.exception.OrderNotFoundException;
import com.example.test.exception.OrderValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDto createOrder(OrderDto orderDto) {
        validate(orderDto);
        Order order = orderDtoToOrder(orderDto);
        orderRepository.save(order);
        return orderToOrderDto(order);
    }

    @Transactional
    public OrderDto getOrder(OrderDto orderDto) {
        validate(orderDto);
        Order order = orderRepository.findByItem(orderDto.getItem());
        if (order != null) {
            OrderDto res;
            if (order.getQuantity() >= orderDto.getQuantity()) {
                order.setQuantity(order.getQuantity() - orderDto.getQuantity());
                res = orderToOrderDto(order);
                res.setQuantity(orderDto.getQuantity());
            } else {
                res = orderToOrderDto(order);
                order.setQuantity(0);
            }
            orderRepository.save(order);
            return res;
        }
        throw new OrderNotFoundException("Order not found.");
    }

    private void validate(OrderDto orderDto) {
        if (orderDto.getItem() == null || orderDto.getItem().isBlank()) {
            throw new OrderValidationException("Item can not be blank.");
        }
    }

    private OrderDto orderToOrderDto(Order order) {
        if (order != null) {
            OrderDto orderDto = new OrderDto();
            orderDto.setItem(order.getItem());
            orderDto.setPrice(order.getPrice());
            orderDto.setQuantity(order.getQuantity());
            return orderDto;
        }
        return null;
    }

    private Order orderDtoToOrder(OrderDto orderDto) {
        if (orderDto != null) {
            Order order = new Order();
            order.setItem(orderDto.getItem());
            order.setPrice(orderDto.getPrice());
            order.setQuantity(orderDto.getQuantity());
            return order;
        }
        return null;
    }
}
