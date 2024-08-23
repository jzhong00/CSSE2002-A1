package farm.core;

import farm.customer.AddressBook;
import farm.customer.Customer;
import farm.inventory.BasicInventory;
import farm.inventory.FancyInventory;
import farm.inventory.Inventory;
import farm.inventory.product.Product;
import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;
import farm.sales.TransactionHistory;
import farm.sales.TransactionManager;
import farm.sales.transaction.Transaction;

import java.util.List;

/**
 * The top-level model class responsible for managing the internal state of the farm.
 */
public class Farm {
    private Inventory inventory;
    private AddressBook addressBook;
    private TransactionManager transactionManager;
    private TransactionHistory transactionHistory;

    /**
     * Constructor for the Farm that creates a new farm instance with an inventory and address book.
     * @param inventory The inventory through which access to the farm's stock is provisioned.
     * @param addressBook The address book storing the farm's customer records.
     */
    public Farm(Inventory inventory, AddressBook addressBook) {
        this.inventory = inventory;
        this.addressBook = addressBook;

        // Initialise a transaction manager and transaction history.
        this.transactionManager = new TransactionManager();
        this.transactionHistory = new TransactionHistory();

    }

    /**
     * Retrieves all customers in the farm's address book.
     * @return A list of all customers in the address book.
     * @ensures The returned list is a shallow copy and cannot modify the original address book.
     */
    public List<Customer> getAllCustomers() {
        return addressBook.getAllRecords();
    }

    /** Retrieves all products currently stored in the farm's inventory.
     * @return A list of all products in the inventory.
     * @ensures The returned list is a shallow copy and cannot modify the original inventory.
     */
    public List<Product> getAllStock() {
        return inventory.getAllProducts();
    }

    /**
     * Retrieves the farm's transaction manager.
     * @return The farm's transaction manager.
     */
    public TransactionManager getTransactionManager() {
        return this.transactionManager;
    }

    /**
     * Retrieves the farm's transaction history.
     * @return The farm's transaction history.
     */
    public TransactionHistory getTransactionHistory() {
        return this.transactionHistory;
    }

    /**
     * Saves the supplied customer in the farm's address book.
     * @param customer The customer to add into the address book.
     * @throws DuplicateCustomerException If the address book already contains this customer.
     */
    public void saveCustomer(Customer customer)
                  throws DuplicateCustomerException {
        this.addressBook.addCustomer(customer);
    }

    /**
     * Adds a single product of the specified type and quality to the farm's inventory.
     * @param barcode The barcode of the product to be added.
     * @param quality The quality of the product.
     */
    public void stockProduct(Barcode barcode,
                             Quality quality) {
        this.inventory.addProduct(barcode, quality);
    }

    /**
     * Adds a specified number of a product into the farm's inventory.
     * @param barcode The barcode of the product.
     * @param quality The product's quality.
     * @param quantity The number of products to add into the inventory.
     * @throws InvalidStockRequestException If the quantity is greater than one, a FancyInventory is not in use.
     * @throws IllegalArgumentException If the quantity is less than one.
     */
    public void stockProduct(Barcode barcode,
                             Quality quality,
                             int quantity) throws InvalidStockRequestException {

        // Check a valid quantity is supplied.
        if (quantity <= 0) {
            // Must add at least one item.
            throw new IllegalArgumentException("Quantity must be at least 1.");
        } else if (quantity > 1 && !(inventory instanceof FancyInventory)) {
            // Adding more than one item at a time cannot be done with a BasicInventory.
            throw new InvalidStockRequestException();
        }

        this.inventory.addProduct(barcode, quality, quantity);
    }

    /**
     * Sets the provided transaction as the current ongoing transaction.
     * @param transaction The traction to be set as ongoing.
     * @throws FailedTransactionException If the farm's transaction manager is unable
     *                                    to set this transaction as the ongoing transaction.
     */
    public void startTransaction(Transaction transaction)
                      throws FailedTransactionException {
        this.transactionManager.setOngoingTransaction(transaction);
    }

    /**
     * Attempts to add a single product of the given type to the customer's shopping cart.
     * @param barcode The product barcode to add.
     * @return The number of products successfully added.
     * @throws FailedTransactionException If no transaction is currently ongoing.
     */
    public int addToCart(Barcode barcode)
              throws FailedTransactionException {

        // Ensure there is a currently ongoing transaction.
        if (!transactionManager.hasOngoingTransaction()) {
            throw new FailedTransactionException("Cannot add to cart when no customer has started shopping.");
        }

        // Get all products currently in the farm's inventory.
        List<Product> products = this.inventory.getAllProducts();

        for (Product product : products) {
            if (product.getBarcode().equals(barcode)) {
                // If the product is in the inventory, register it as a pending purchase.
                this.transactionManager.registerPendingPurchase(product);
                // This method can only successfully register one product.
                return 1;
            }
        }
        // No products were successfully added.
        return 0;
    }

    /**
     * Attempts to add the specified number of products of the given type to the customer's shopping cart.
     * @param barcode The barcode of the product to be added.
     * @param quantity The number of products to add.
     * @return The number of products successfully added.
     * @throws FailedTransactionException If there is no currently ongoing transaction,
     *                                    or if the quantity is greater than one when not using a FancyInventory.
     * @throws IllegalArgumentException If the argument less than 1.
     */
    public int addToCart(Barcode barcode, int quantity)
              throws FailedTransactionException {

        if (quantity < 1) {
            // The quantity is negative or zero.
            throw new IllegalArgumentException("Quantity must be at least 1.");
        } else if (quantity > 1 && (inventory instanceof BasicInventory)) {
            // The inventory is basic and the customer is attempting to buy multiple products in one transaction.
            throw new FailedTransactionException("Current inventory is not fancy enough. Please purchase products one at a time.");
        } else if  (!transactionManager.hasOngoingTransaction()) {
            // There is no currently existing transaction.
            throw new FailedTransactionException("Cannot add to cart when no customer has started shopping.");
        }

        int numAdded = 0;
        List<Product> products = this.inventory.getAllProducts();
        // Add as many products as possible given the existing stock.
        for (Product product : products) {
            if (product.getBarcode().equals(barcode) && numAdded < quantity) {
                this.transactionManager.registerPendingPurchase(product);
                numAdded++;
            }
        }
        return numAdded;
    }

    /**
     * Closes the ongoing transaction.
     * If there have been items purchased, record the transaction in the farm's history.
     * @return If and only if the transaction contained products.
     * @throws FailedTransactionException If the transaction is unable to be closed.
     */
    public boolean checkout()
                 throws FailedTransactionException {
        // Attempt to close the current transaction.
        Transaction closedTransaction = this.transactionManager.closeCurrentTransaction();
        if (!closedTransaction.getPurchases().isEmpty()) {
            // If there were items purchased, record the transaction.
            this.transactionHistory.recordTransaction(closedTransaction);
            return true;
        }
        // An empty cart was checked out.
        return false;
    }

    /**
     * Retrieves the receipt associated with the most recent transaction.
     * @return The receipt.
     */
    public String getLastReceipt() {
        Transaction transaction = transactionHistory.getLastTransaction();
        return transaction.getReceipt();
    }

    /**
     * Retrieves a customer from the address book.
     * @param name The customer's name.
     * @param phoneNumber The customer's phone number.
     * @return The customer instance.
     * @throws CustomerNotFoundException If the customer is not in the address book.
     */
    public Customer getCustomer(String name, int phoneNumber)
            throws CustomerNotFoundException {
        return addressBook.getCustomer(name, phoneNumber);
    }
}
