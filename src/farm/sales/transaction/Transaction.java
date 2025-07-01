package farm.sales.transaction;

import farm.customer.*;
import farm.inventory.product.Product;
import farm.sales.ReceiptPrinter;

import java.util.ArrayList;
import java.util.List;


/**
 * Transactions keeps track of what items are to be (or have been) purchased and by whom.
 */
public class Transaction {
    private Customer customer;
    private List<Product> purchases;
    private boolean finalised;

    /**
     * Construct a new transaction for an associated customer.
     * @param customer The customer who is starting the transaction.
     */
    public Transaction(Customer customer) {
        this.customer = customer;
        this.purchases = new ArrayList<>();

        // Transactions should always be active at the time of creation.
        this.finalised = false;
    }

    /**
     * Retrieves the customer associated with this transaction.
     * @return The customer of the transaction.
     */
    public Customer getAssociatedCustomer() {
        return this.customer;
    }

    /**
     * Retrieves all products associated with the transaction.
     * <p>
     * If the transaction has been finalised,
     * this is all products that were 'locked in' as final purchases at that time
     * <p>
     * If the transaction is instead still active,
     * it is all products currently in the associated customer's cart.
     * @return the list of purchases comprising the transaction.
     * @ensures The returned list is a shallow copy and cannot modify the original transaction
     */
    public List<Product> getPurchases() {
        if (!this.finalised) {
            return getAssociatedCustomer().getCart().getContents();
        }
        return new ArrayList<>(this.purchases);
    }

    /**
     * Calculates the total price of all the current products in the transaction.
     * @return The calculated total price.
     */
    public int getTotal() {
        List<Product> purchases = getPurchases();
        int total = 0;
        for (Product product : purchases) {
            total += product.getBasePrice();
        }
        return total;
    }

    /**
     * Determines if the transaction is finalised (i.e. sale completed) or not.
     * @return True if and only if the transaction is over, else false.
     */
    public boolean isFinalised() {
        return this.finalised;
    }

    /**
     * Mark a transaction as finalised and update the transaction's internal state accordingly.
     */
    public void finalise() {
        this.finalised = true;
        // Set all purchases as final and empty cart.
        this.purchases = new ArrayList<>(this.customer.getCart().getContents());
        this.customer.getCart().setEmpty();
    }

    /**
     * Returns a string representation of this transaction and its current state.
     * <p>
     * The representation contains information about the customer,
     * the transaction's status, and the associated products.
     * @return The formatted string representation of the product.
     */
    @Override
    public String toString() {
        // Dynamically determine the status string.
        String status = this.finalised ? "Finalised" : "Active";

        List<String> productDescriptions = new ArrayList<>();

        // Add the string representation of each product/
        for (Product product : this.getPurchases()) {
            productDescriptions.add(product.toString());
        }

        return new StringBuilder().append("Transaction {Customer: ")
                .append(customer.toString().replaceFirst("Name: ", ""))
                .append(", Status: ")
                .append(status)
                .append(", Associated Products: ")
                .append(productDescriptions)
                .append("}").toString();
    }

    /**
     * Converts the transaction into a formatted receipt for display.
     * @return The styled receipt representation of this transaction
     */
    public String getReceipt() {

        // Determine the headings of the receipt.
        List<String> headings = new ArrayList<>();
        headings.add("Item");
        headings.add("Price");

        // Create an active receipt if the transaction is not finalised.
        if (!finalised) {
            return ReceiptPrinter.createActiveReceipt();
        }

        String total = convertPrice(this.getTotal());

        List<List<String>> entries = new ArrayList<>();
        // Iterate through each purchase and add its information to a row.
        for (Product product : this.purchases) {
            List<String> entry = new ArrayList<>();
            entry.add(product.getDisplayName());
            entry.add(convertPrice(product.getBasePrice()));
            entries.add(entry);
        }
        return ReceiptPrinter.createReceipt(headings, entries, total, this.customer.getName());
    }

    /**
     * Helper method to convert a price in cents to a string representation in dollars.
     * @param price The integer price in cents.
     * @return A formatted string for the price.
     */
    protected String convertPrice(int price) {
        return "$" + String.format("%.2f", price / 100.0);
    }


}
