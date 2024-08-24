package farm.sales.transaction;

import farm.customer.Customer;
import farm.inventory.product.Product;
import farm.inventory.product.data.Barcode;
import farm.sales.ReceiptPrinter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecialSaleTransaction extends CategorisedTransaction {
    Map<Barcode, Integer> discounts;

    public SpecialSaleTransaction(Customer customer) {
        super(customer);
        this.discounts = new HashMap<>();
    }

    public SpecialSaleTransaction(Customer customer, Map<Barcode, Integer> discounts) {
        super(customer);
        this.discounts = new HashMap<>(discounts);
    }

    @Override
    public int getPurchaseSubtotal(Barcode type) {
        double subtotal = 0.0;
        // Calculate subtotal for products matching the given barcode
        for (Product product : getPurchases()) {
            if (product.getBarcode().equals(type)) { // Use .equals() for object comparison
                subtotal += product.getBasePrice();
            }
        }
        System.out.println(subtotal);

        // Retrieve discount amount as a percentage
        int discountPercentage = getDiscountAmount(type);

        // Calculate the discounted subtotal
        double result = subtotal - ((discountPercentage * subtotal) / 100.0); // Convert percentage to a factor
        System.out.println(result);
        return (int) (Math.round(result)); // Apply discount and cast to int
    }

    public int getDiscountAmount(Barcode type) {
        int discount = 0;
        if (discounts.containsKey(type)) {
            discount = discounts.get(type);
        }
        return discount;
    }

    public int getTotalSaved() {
        double total = 0.0;

        // Get purchased products
        Map<Barcode, List<Product>> purchases = getPurchasesByType();

        // Iterate through each discount
        for (Map.Entry<Barcode, Integer> discount : discounts.entrySet()) {
            Barcode barcode = discount.getKey();
            int discountAmount = discount.getValue();

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

    @Override
    public int getTotal() {
        int total = super.getTotal();
        int saved = getTotalSaved();
        return total - saved;
    }

    @Override
    public String toString() {
        List<Product> products = getPurchases();
        Customer customer = getAssociatedCustomer();
        String status = isFinalised() ? "Finalised" : "Active";
        System.out.println(this.discounts);

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
                System.out.println(subtotal);

                entry.add(barcode.getDisplayName());
                entry.add(String.valueOf(quantity));
                entry.add(convertPrice(basePrice));
                entry.add(convertPrice(subtotal));
                if (discounts.containsKey(barcode) && discounts.get(barcode) > 0) {
                    String message = "Discount applied! " + discounts.get(barcode) + "% off " + barcode.getDisplayName();
                    entry.add(message);
                }
                receiptEntries.add(entry);
            }
        }
        if (totalSavedNum > 0) {
            return ReceiptPrinter.createReceipt(headings, receiptEntries, totalCost, customer, totalSaved);
        } else {
            return ReceiptPrinter.createReceipt(headings, receiptEntries, totalCost, customer);
        }

    }
}


