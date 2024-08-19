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
        int subtotal = 0;
        for (Product product : getPurchases()) {
            if (product.getBarcode() == type) {
                subtotal += product.getBasePrice();
            }
        }
        return subtotal * (1 - getDiscountAmount(type));
    }

    public int getDiscountAmount(Barcode type) {
        int discount = 0;
        if (discounts.containsKey(type)) {
            discount = discounts.get(type);
        }
        return discount;
    }

    public int getTotalSaved() {
        int total = 0;

        // Get purchased products
        Map<Barcode, List<Product>> purchases = getPurchasesByType();

        // Iterate through each discount
        for (Map.Entry<Barcode, Integer> discount : discounts.entrySet()) {
            Barcode barcode = discount.getKey();
            int discountAmount = discount.getValue();

            if (purchases.containsKey(barcode)) {
                List<Product> products = purchases.get(barcode);
                int quantity = getPurchaseQuantity(barcode);

                total += discountAmount * quantity;
            }
        }
        return total;
    }

    @Override
    public String toString() {
        List<Product> products = getPurchases();
        Customer customer = getAssociatedCustomer();
        String status = isFinalised() ? "Finalised" : "Active";
        Map<Barcode, Integer> usedDiscounts = new HashMap<>();
        for (Product product : products) {
            if (discounts.containsKey(product.getBarcode())) {
                usedDiscounts.put(product.getBarcode(), discounts.get(product.getBarcode()));
            }
        }

        // Format string
        return "Transaction {Customer: " + customer + ", Status: "
                + status + ", Associated Products: " + products
                + ", Discounts: " + usedDiscounts + "}";
    }

    @Override
    public String getReceipt() {
        if (!isFinalised()) {
            return ReceiptPrinter.createActiveReceipt();
        }

        List<String> headings = List.of("Item", "Qty", "Price (ea.)", "Subtotal");
        String customer = getAssociatedCustomer().getName();

        List<List<String>> recieptEntries = new ArrayList<>();
        String totalCost = convertPrice(getTotal());
        String totalSaved = convertPrice(getTotalSaved());

        for (Barcode barcode : Barcode.values()) {
            int quantity = getPurchaseQuantity(barcode);

            if (quantity > 0) {
                int basePrice = barcode.getBasePrice();
                int subtotal = getPurchaseSubtotal(barcode);
                int discount = getDiscountAmount(barcode);
                int savings = (quantity * basePrice) - subtotal;

                List<String> entry = new ArrayList<>();
                entry.add(barcode.getDisplayName());
                entry.add(String.valueOf(quantity));
                entry.add(convertPrice(basePrice));
                entry.add(convertPrice(subtotal));

                recieptEntries.add(entry);
            }

            if (discounts.containsKey(barcode)) {
                List<String> entry = new ArrayList<>();
                String message = "Discount applied! " + discounts.get(barcode) + "% off " + barcode.getDisplayName();
                entry.add(message);
                recieptEntries.add(entry);
            }
        }
        return ReceiptPrinter.createReceipt(headings, recieptEntries, totalCost, customer, totalSaved);

    }
}


