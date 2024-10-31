package com.example;

import com.example.config.AppConfig;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service.Cart;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Scanner;

public class ShoppingCartApplication {
    private final ProductRepository productRepository;
    private final ApplicationContext context;

    public ShoppingCartApplication(ProductRepository productRepository, ApplicationContext context) {
        this.productRepository = productRepository;
        this.context = context;
    }

    public void run() {
        // Создаем один экземпляр корзины в рамках сессии
        Cart cart = context.getBean(Cart.class);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Options: 1 - View Products, 2 - Add to Cart, 3 - Remove from Cart, 4 - Show Cart, 0 - Exit");
            int choice = scanner.nextInt();

            if (choice == 0) break;

            switch (choice) {
                case 1 -> productRepository.getAllProducts().forEach(System.out::println);
                case 2 -> {
                    System.out.print("Enter product ID to add: ");
                    int idToAdd = scanner.nextInt();
                    Product productToAdd = productRepository.getProductById(idToAdd);
                    if (productToAdd != null) {
                        cart.addProduct(productToAdd);
                        System.out.println("Added to cart: " + productToAdd);
                    } else {
                        System.out.println("Product not found.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter product ID to remove: ");
                    int idToRemove = scanner.nextInt();
                    cart.removeProductById(idToRemove);
                    System.out.println("Removed product with ID: " + idToRemove);
                }
                case 4 -> {
                    System.out.println("Cart contains:");
                    cart.displayCart();
                }
                default -> System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ProductRepository productRepository = context.getBean(ProductRepository.class);
        ShoppingCartApplication app = new ShoppingCartApplication(productRepository, context);
        app.run();
    }
}