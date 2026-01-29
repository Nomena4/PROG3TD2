package com.example.restaurant.service;

import com.example.restaurant.model.*;
import com.example.restaurant.repository.DataRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

/**
 * Example service demonstrating the usage of the new features
 */
@Service
public class OrderSaleService {

    @Autowired
    private DataRetriever dataRetriever;

    /**
     * Example: Create an order and mark it as paid
     */
    public Order createAndPayOrder(String reference) {
        // Create a new order
        Order order = new Order(reference, LocalDateTime.now());
        order.setPaymentStatus(PaymentStatus.UNPAID);

        // Save the order
        order = dataRetriever.saveOrder(order);

        // Mark order as paid
        order.setPaymentStatus(PaymentStatus.PAID);
        order = dataRetriever.saveOrder(order);

        return order;
    }

    /**
     * Example: Try to modify a paid order (should throw exception)
     */
    public void attemptToModifyPaidOrder(String reference) {
        Order order = dataRetriever.findOrderByReference(reference);

        try {
            // This should throw an exception if order is paid
            order.setReference("NEW_REFERENCE");
            dataRetriever.saveOrder(order);
        } catch (IllegalStateException e) {
            System.out.println("Expected exception: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Example: Create a sale from a paid order
     */
    public Sale createSaleForOrder(String orderReference) {
        Order order = dataRetriever.findOrderByReference(orderReference);

        // Ensure order is paid
        if (order.getPaymentStatus() != PaymentStatus.PAID) {
            order.setPaymentStatus(PaymentStatus.PAID);
            order = dataRetriever.saveOrder(order);
        }

        // Create the sale
        Sale sale = dataRetriever.createSaleFrom(order);

        return sale;
    }

    /**
     * Example: Try to create a sale from an unpaid order (should throw exception)
     */
    public void attemptToCreateSaleFromUnpaidOrder(String orderReference) {
        Order order = dataRetriever.findOrderByReference(orderReference);

        try {
            // This should throw an exception if order is not paid
            dataRetriever.createSaleFrom(order);
        } catch (IllegalStateException e) {
            System.out.println("Expected exception: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Example: Try to create duplicate sale (should throw exception)
     */
    public void attemptToCreateDuplicateSale(String orderReference) {
        Order order = dataRetriever.findOrderByReference(orderReference);

        // Create first sale
        dataRetriever.createSaleFrom(order);

        try {
            // This should throw an exception as sale already exists
            dataRetriever.createSaleFrom(order);
        } catch (IllegalStateException e) {
            System.out.println("Expected exception: " + e.getMessage());
            throw e;
        }
    }
}
