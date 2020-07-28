package com.example.test.order;

import com.example.test.exception.OrderNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.bind.ValidationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    OrderRepository orderRepository;
    private OrderService orderService;

    @AfterEach
    public void check() {
        verifyNoMoreInteractions(orderRepository);
    }

    @BeforeEach
    public void init() {
        reset(orderRepository);
        orderService = new OrderService(orderRepository);
    }

    @Test
    @DisplayName("Create order.")
    void createOrderExpectedOk() throws ValidationException {
        OrderDto orderDto = new OrderDto();
        orderDto.setQuantity(5);
        orderDto.setItem("Item");
        orderDto.setPrice(1000);

        Order order = new Order();
        order.setQuantity(5);
        order.setPrice(1000);
        order.setItem("Item");
        order.setId("1");

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDto result = orderService.createOrder(orderDto);

        assertNotNull(result);
        assertEquals(orderDto, result);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("Try create order with empty \"Item\".")
    void createOrderWithEmptyItemExpectedException() {
        OrderDto orderDto = new OrderDto();
        orderDto.setQuantity(5);
        orderDto.setItem("");
        orderDto.setPrice(1000);

        assertThrows(ValidationException.class,
                () -> orderService.createOrder(orderDto),
                "Item can not be blank.");
    }

    @Test
    @DisplayName("Try create order without \"Item\".")
    void createOrderWithoutItemExpectedException() {
        OrderDto orderDto = new OrderDto();
        orderDto.setQuantity(5);
        orderDto.setItem(null);
        orderDto.setPrice(1000);

        assertThrows(ValidationException.class,
                () -> orderService.createOrder(orderDto),
                "Item can not be blank.");
    }

    @Test
    @DisplayName("Get order.")
    void getOrderExpectedOk() throws ValidationException {
        OrderDto request = new OrderDto();
        request.setQuantity(5);
        request.setItem("Item");

        OrderDto response = new OrderDto();
        response.setQuantity(5);
        response.setItem("Item");
        response.setPrice(1000);

        Order order = new Order();
        order.setQuantity(5);
        order.setPrice(1000);
        order.setItem("Item");
        order.setId("1");

        when(orderRepository.findByItem(any(String.class))).thenReturn(order);

        OrderDto result = orderService.getOrder(request);

        assertNotNull(result);
        assertEquals(response, result);
        verify(orderRepository).save(any(Order.class));
        verify(orderRepository).findByItem(anyString());
    }

    @Test
    @DisplayName("Get order quantity more than have in db.")
    void getOrderQuantityMoreThanHaveExpectedOk() throws ValidationException {
        OrderDto request = new OrderDto();
        request.setQuantity(5);
        request.setItem("Item");

        OrderDto response = new OrderDto();
        response.setQuantity(2);
        response.setItem("Item");
        response.setPrice(1000);

        Order order = new Order();
        order.setQuantity(2);
        order.setPrice(1000);
        order.setItem("Item");
        order.setId("1");

        when(orderRepository.findByItem(any(String.class))).thenReturn(order);

        OrderDto result = orderService.getOrder(request);

        assertNotNull(result);
        assertEquals(response, result);
        verify(orderRepository).save(any(Order.class));
        verify(orderRepository).findByItem(anyString());
    }

    @Test
    @DisplayName("Try get order without \"Item\".")
    void getOrderWithoutItem() {
        OrderDto request = new OrderDto();
        request.setQuantity(5);
        request.setItem(null);

        assertThrows(ValidationException.class,
                () -> orderService.getOrder(request),
                "Item can not be blank.");
    }

    @Test
    @DisplayName("Try get order with empty \"Item\".")
    void getOrderWithEmptyItem() {
        OrderDto request = new OrderDto();
        request.setQuantity(5);
        request.setItem("");

        assertThrows(ValidationException.class,
                () -> orderService.getOrder(request),
                "Item can not be blank.");
    }

    @Test
    @DisplayName("Try get order with wrong \"Item\".")
    void getOrderWithWrongItem() {
        OrderDto request = new OrderDto();
        request.setQuantity(5);
        request.setItem("wrongItem");

        when(orderRepository.findByItem(any(String.class))).thenReturn(null);

        assertThrows(OrderNotFoundException.class,
                () -> orderService.getOrder(request),
                "Order not found.");
    }


}
