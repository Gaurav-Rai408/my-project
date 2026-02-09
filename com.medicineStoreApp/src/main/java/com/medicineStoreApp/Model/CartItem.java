package com.medicineStoreApp.Model;

public class CartItem {
    private Product product; // Product object containing product details
    private int quantity;    // Quantity of the product in the cart
    private double totalPrice; // Total price for this cart item
    private int cart_id;
    
    // Constructor
    public CartItem(Product product, int quantity , int cart_id) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = quantity * product.getPrice(); // Calculate total price
        this.cart_id = cart_id;
    }
    
    public CartItem() {
    	
    }

    // Getters and Setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        // Update total price when the product changes
        this.totalPrice = this.quantity * this.product.getPrice();
    }

    public int getQuantity() {
        return quantity;
    }
    
    public int getCartId() {
        return cart_id;
    }
    
    public void setCartId(int id) {
        this.cart_id = id;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
        // Update total price when quantity changes
        this.totalPrice = this.quantity * this.product.getPrice();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    // Optionally add a toString method for debugging or display purposes
    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
