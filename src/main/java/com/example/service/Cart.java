package com.example.service;

import com.example.model.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Scope("prototype") // Каждый раз создается новый экземпляр корзины
public class Cart {
    private final List<Product> cartItems = new ArrayList<>();

    public void addProduct(Product product) {
        cartItems.add(product);
    }

    public void removeProductById(int id) {
        cartItems.removeIf(product -> product.getId() == id);
    }

    public List<Product> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public void displayCart() {
        if (cartItems.isEmpty()) {
            System.out.println("The cart is empty.");
        } else {
            cartItems.forEach(System.out::println);
        }
    }
}
