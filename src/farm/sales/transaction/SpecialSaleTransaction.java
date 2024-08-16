package farm.sales.transaction;

import farm.customer.Customer;
import farm.inventory.product.Product;
import farm.inventory.product.data.Barcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecialSaleTransaction extends CategorisedTransaction {
    Map<Barcode, Integer> discounts = new HashMap<>();

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
        return getPurchaseSubtotal(type) * (1 - getDiscountAmount(type));
    }

    public int getDiscountAmount(Barcode type) {
        return discounts.get(type);
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
        return "Transaction"


    }

}
