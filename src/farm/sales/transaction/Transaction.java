package farm.sales.transaction;

import farm.customer.*;
import farm.inventory.product.Product;
import farm.sales.ReceiptPrinter;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    protected Customer customer;
    protected List<Product> purchases;
    protected boolean finalised;

    public Transaction(Customer customer) {
        this.customer = customer;
        this.purchases = new ArrayList<>();
        this.finalised = false;
    }

    public Customer getAssociatedCustomer() {
        return this.customer;
    }

    public List<Product> getPurchases() {
        if (!this.finalised) {
            return getAssociatedCustomer().getCart().getContents();
        }
        return new ArrayList<>(this.purchases);
    }

    public int getTotal() {
        List<Product> purchases = getPurchases();
        int total = 0;
        for (Product product : purchases) {
            total += product.getBasePrice();
        }
        return total;
    }

    public boolean isFinalised() {
        return this.finalised;
    }

    public void finalise() {
        this.finalised = true;
        this.purchases = new ArrayList<>(this.customer.getCart().getContents());
        this.customer.getCart().setEmpty();
    }

    @Override
    public String toString() {
        String status = this.finalised ? "Finalised" : "Active";
        List<String> productDescriptions = new ArrayList<>();

        for (Product product : this.getPurchases()) {
            productDescriptions.add(product.toString());
        }

        return "Transaction {Customer: " + customer.toString().replaceFirst("Name: ", "")
                + ", Status: " + status + ", Associated Products: " + productDescriptions + "}";
    }

    public String getReceipt() {
        List<String> headings = new ArrayList<>();
        String total = convertPrice(this.getTotal());

        headings.add("Item");
        headings.add("Price");
        if (!finalised) {
            return ReceiptPrinter.createActiveReceipt();
        }

        List<List<String>> entries = new ArrayList<>();
        for (Product product : this.purchases) {
            List<String> entry = new ArrayList<>();
            entry.add(product.getDisplayName());
            entry.add(convertPrice(product.getBasePrice()));
            entries.add(entry);
        }
        return ReceiptPrinter.createReceipt(headings, entries, total, this.customer.getName());
    }

    protected String convertPrice(int price) {
        return "$" + String.format("%.2f", price / 100.0);
    }

}
