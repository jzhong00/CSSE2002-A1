package farm.sales.transaction;

import farm.customer.Customer;
import farm.inventory.product.Product;
import farm.inventory.product.data.Barcode;
import farm.sales.ReceiptPrinter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A transaction type that builds on the functionality of a categorised transaction,
 * allowing store-wide discounts to be applied to all products of a nominated type.
 */
public class SpecialSaleTransaction extends CategorisedTransaction {
    private Map<Barcode, Integer> discounts;

    /**
     * Construct a new special sale transaction for an associated customer,
     * with an empty set of discounts
     *
     * @param customer The customer who is starting the transaction.
     */
    public SpecialSaleTransaction(Customer customer) {
        super(customer);
        this.discounts = new HashMap<>();
    }

    /**
     * onstruct a new special sale transaction for an associated customer,
     * with a set of discounts to be applied to nominated product types on purchasing.
     *
     * @param customer The customer who is starting the transaction.
     * @param discounts A mapping from product barcodes to the associated discount
     *                 applied on purchasing, where discount amounts are specified
     *                 as an integer percentage
     */
    public SpecialSaleTransaction(Customer customer, Map<Barcode, Integer> discounts) {
        super(customer);
        this.discounts = new HashMap<>(discounts);
    }


    /**
     * Determines the total price for the provided product type within this transaction,
     * with any specified discount applied as an integer percentage taken from the usual subtotal.
     *
     * @param type The product type.
     * @return The total (discounted) price for all instances of that
     *         product type within this transaction.
     */
    @Override
    public int getPurchaseSubtotal(Barcode type) {
        int subtotal = super.getPurchaseSubtotal(type);

        // Retrieve discount amount as a percentage
        int discountPercentage = getDiscountAmount(type);

        // Calculate the discounted subtotal
        double result = subtotal - ((discountPercentage * subtotal) / 100.0);
        return (int) (Math.round(result));
    }

    /**
     * Retrieves the discount percentage that will be applied for a particular product type,
     * as an integer
     *
     * @param type The product type.
     * @return The amount the product is discount by, as an integer percentage.
     */
    public int getDiscountAmount(Barcode type) {
        int discount = 0;
        if (discounts.containsKey(type)) {
            discount = discounts.get(type);
        }
        return discount;
    }

    /**
     * Calculates how much the customer has saved from discounts.
     * @return The numerical savings from discounts.
     */
    public int getTotalSaved() {
        double total = 0.0;

        // Get purchased products
        Map<Barcode, List<Product>> purchases = getPurchasesByType();

        // Iterate through each discount
        for (Map.Entry<Barcode, Integer> discount : discounts.entrySet()) {
            Barcode barcode = discount.getKey();
            int discountAmount = discount.getValue();

            // Check if a purchase with that barcode was made.
            if (purchases.containsKey(barcode)) {
                List<Product> products = purchases.get(barcode);
                int productTotal = 0;
                for (Product product : products) {
                    productTotal += product.getBasePrice();
                }

                // Calculate the amount saved by applying the discount
                double amountSaved = (productTotal * discountAmount) / 100.0;
                total += amountSaved;
            }
        }
        return (int) (total);
    }


    /**
     * Calculates the total price (with discounts) of all the current products in the transaction.
     * @return the total (discounted) price calculated.
     */
    @Override
    public int getTotal() {
        int total = 0;
        for (Barcode barcode : this.getPurchasedTypes()) {
            total += this.getPurchaseSubtotal(barcode);
        }
        return total;
    }

    /**
     * Returns a string representation of this transaction and its current state.
     * <p>
     * The representation contains information about the customer, the transaction's status,
     * the associated products, and the discounts to be applied.
     * @return The formatted string representation of the product.
     */
    @Override
    public String toString() {
        List<Product> products = getPurchases();
        Customer customer = getAssociatedCustomer();
        String status = isFinalised() ? "Finalised" : "Active";

        // Format string
        return "Transaction {Customer: " + customer.toString().replace("Name: ", "") + ", Status: "
                + status + ", Associated Products: " + products
                + ", Discounts: " + this.discounts + "}";
    }


    @Override
    public String getReceipt() {
        if (!isFinalised()) {
            return ReceiptPrinter.createActiveReceipt();
        }

        List<String> headings = List.of("Item", "Qty", "Price (ea.)", "Subtotal");
        String customer = getAssociatedCustomer().getName();

        List<List<String>> receiptEntries = new ArrayList<>();
        String totalCost = convertPrice(getTotal());
        int totalSavedNum = getTotalSaved();
        String totalSaved = convertPrice(getTotalSaved());

        for (Barcode barcode : Barcode.values()) {
            int quantity = getPurchaseQuantity(barcode);

            if (quantity > 0) {
                List<String> entry = new ArrayList<>();

                int basePrice = barcode.getBasePrice();
                int subtotal = getPurchaseSubtotal(barcode);

                entry.add(barcode.getDisplayName());
                entry.add(String.valueOf(quantity));
                entry.add(convertPrice(basePrice));
                entry.add(convertPrice(subtotal));
                if (discounts.containsKey(barcode) && discounts.get(barcode) > 0) {
                    String message = "Discount applied! " + discounts.get(barcode)
                            + "% off " + barcode.getDisplayName();
                    entry.add(message);
                }
                receiptEntries.add(entry);
            }
        }
        if (totalSavedNum > 0) {
            return ReceiptPrinter.createReceipt(
                    headings, receiptEntries, totalCost, customer, totalSaved
            );
        } else {
            return ReceiptPrinter.createReceipt(headings, receiptEntries, totalCost, customer);
        }

    }
}


