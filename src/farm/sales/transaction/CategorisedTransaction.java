package farm.sales.transaction;

import farm.customer.Customer;
import farm.inventory.product.Product;
import farm.inventory.product.data.Barcode;
import farm.sales.ReceiptPrinter;

import java.util.*;

/**
 * A transaction type that allows products to be categorised by their types, not solely as isolated individual products.
 * The resulting receipt therefore displays purchased types with an associated quantity purchased and subtotal, rather than a single line for each product.
 */
public class CategorisedTransaction extends Transaction {

    /**
     * Construct a new categorised transaction for an associated customer.
     * @param customer The customer who is starting the transaction.
     */
    public CategorisedTransaction(Customer customer) {
        super(customer);
    }

    /**
     * Retrieves all unique product types of the purchases associated with the transaction.
     * @return A set of all product types in the transaction.
     */
    public Set<Barcode> getPurchasedTypes() {
        Set<Barcode> barcodes = new HashSet<>();
        for (Product purchase : getPurchases()) {
            barcodes.add(purchase.getBarcode());
        }
        return barcodes;
    }


    /**
     * Retrieves all products associated with the transaction, grouped by their type.
     * @return The products in the transaction, grouped by their type.
     */
    public Map<Barcode, List<Product>> getPurchasesByType() {
        Map<Barcode, List<Product>> purchasesByType = new HashMap<>();
        List<Product> purchases = getPurchases();

        for (Product purchase : purchases) {
            Barcode barcode = purchase.getBarcode();
            // Create a key for a new barcode.
            if (!purchasesByType.containsKey(barcode)) {
                purchasesByType.put(barcode, new ArrayList<>());
            }
            // Add the purchase to the corresponding barcode.
            purchasesByType.get(barcode).add(purchase);
        }
        return purchasesByType;
    }

    /**
     * Retrieves the number of products of a particular type associated with the transaction.
     * @param type The product type.
     * @return The number of products of the specified type associated with the transaction.
     */
    public int getPurchaseQuantity(Barcode type) {
        List<Product> purchases = this.getPurchases();
        int count = 0;
        for (Product purchase : purchases) {
            if (purchase.getBarcode().equals(type)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Determines the total price for the provided product type within this transaction.
     * @param type The product type.
     * @return The total price for all instances of that product type within the transaction,
     *         or 0 if no items of that type are associated with the transaction.
     */
    public int getPurchaseSubtotal(Barcode type) {
        List<Product> purchases = getPurchases();
        int total = 0;
        for (Product purchase : purchases) {
            if (purchase.getBarcode().equals(type)) {
                int price = purchase.getBasePrice();
                total = total + price;
            }
        }
        return total;
    }


    @Override
    public String getReceipt() {
        // Check if the transaction is finalized
        if (!isFinalised()) {
            return ReceiptPrinter.createActiveReceipt();
        }

        // Set up headings and total.
        List<List<String>> entries = new ArrayList<>();
        String total = convertPrice(getTotal());

        // Get purchased products grouped by Barcode
        Map<Barcode, List<Product>> purchasesByType = getPurchasesByType();

        // Iterate over sorted barcodes to ensure correct order
        for (Barcode barcode : Barcode.values()) {
            if (purchasesByType.containsKey(barcode)) {
                int pricePerItem = barcode.getBasePrice();
                int qty = getPurchaseQuantity(barcode);
                int subtotal = getPurchaseSubtotal(barcode);

                // Add formatted entry
                entries.add(Arrays.asList(
                    barcode.getDisplayName(),
                    String.valueOf(qty),
                    convertPrice(pricePerItem),
                    convertPrice(subtotal)
                ));
            }
        }

        // Add the total to the entries
        List<String> headings = Arrays.asList("Item", "Qty", "Price (ea.)", "Subtotal");

        // Generate and return the receipt
        return ReceiptPrinter.createReceipt(
                headings, entries, total, getAssociatedCustomer().getName());
    }


}