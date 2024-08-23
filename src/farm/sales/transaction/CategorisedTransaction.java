package farm.sales.transaction;

import farm.customer.Customer;
import farm.inventory.product.Product;
import farm.inventory.product.data.Barcode;
import farm.sales.ReceiptPrinter;

import java.util.*;

public class CategorisedTransaction extends Transaction {
    public CategorisedTransaction(Customer customer) {
        super(customer);
    }

    public Set<Barcode> getPurchasedTypes() {
        Set<Barcode> barcodes = new HashSet<>();
        for (Product purchase : getPurchases()) {
            barcodes.add(purchase.getBarcode());
        }
        return barcodes;
    }

    public Map<Barcode, List<Product>> getPurchasesByType() {
        Map<Barcode, List<Product>> purchasesByType = new HashMap<>();
        List<Product> purchases = getPurchases();

        for (Product purchase : purchases) {
            Barcode barcode = purchase.getBarcode();
            if (!purchasesByType.containsKey(barcode)) {
                purchasesByType.put(barcode, new ArrayList<>());
            }
            purchasesByType.get(barcode).add(purchase);
        }

        return purchasesByType;
    }

    public int getPurchaseQuantity(Barcode type) {
        List<Product> purchases = this.getPurchases();
        System.out.println(purchases);
        int count = 0;
        for (Product purchase : purchases) {
            if (purchase.getBarcode().equals(type)) {
                count++;
            }
        }
        return count;
    }

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
        if (!finalised) {
            return ReceiptPrinter.createActiveReceipt();
        }

        // Set up headings and list of entries
        List<List<String>> entries = new ArrayList<>();
        String total = convertPrice(getTotal());

        // Get purchased products grouped by Barcode
        Map<Barcode, List<Product>> purchasesByType = getPurchasesByType();
        System.out.println(purchasesByType);

        // Iterate over sorted barcodes to ensure correct order
        for (Barcode barcode : Barcode.values()) {
            if (purchasesByType.containsKey(barcode)) {
                List<Product> products = purchasesByType.get(barcode);
                int pricePerItem = barcode.getBasePrice(); // Convert cents to dollars
                int qty = getPurchaseQuantity(barcode);
                int subtotal = getPurchaseSubtotal(barcode);

                // Add formatted entry
                entries.add(Arrays.asList(
                    barcode.getDisplayName(), // Product identifier
                    String.valueOf(qty), // Quantity
                    convertPrice(pricePerItem), // Price per item
                    convertPrice(subtotal) // Subtotal
                ));
            }
        }

        // Add the total to the entries
        List<String> headings = Arrays.asList("Item", "Qty", "Price (ea.)", "Subtotal");

        // Generate and return the receipt
        return ReceiptPrinter.createReceipt(headings, entries, total, customer.getName()).toString();
    }


}