package farm.sales;

import farm.inventory.product.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A shopping cart that stores the customer products until they check out.
 */
public class Cart {
    private List<Product> items;

    /**
     * A constructor for the Cart.
     */
    public Cart() {
        this.items = new ArrayList<>();
    }

    /**
     * Adds a given product to the shopping cart.
     * @param product The product to add.
     */
    public void addProduct(Product product) {
        this.items.add(product);
    }

    /**
     * Retrieves all the products in the Cart in the order they were added.
     * @return A list of all products in the cart
     * @ensures The returned list is a shallow copy.
     */
    public List<Product> getContents() {
        return new ArrayList<>(this.items);
    }

    /**
     * Empty the shopping cart.
     */
    public void setEmpty() {
        this.items.clear();
    }

    /**
     * Returns if the cart is empty.
     * @return True if and only if the cart is empty.
     */
    public boolean isEmpty() {
        return this.items.isEmpty();
    }
}
