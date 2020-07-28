package com.example.test.order;

import java.util.Objects;

public class OrderDto {
    private int price;
    private int quantity;
    private String item;

    public OrderDto() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return price == orderDto.price &&
                quantity == orderDto.quantity &&
                item.equals(orderDto.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, quantity, item);
    }
}
