package farm.sales;

import farm.inventory.product.Product;
import farm.inventory.product.data.Barcode;
import farm.sales.transaction.CategorisedTransaction;
import farm.sales.transaction.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * A record of all past transactions.
 * Handles retrieval of statistics about past transactions, such as earnings and popular products.
 */
public class TransactionHistory {
    private List<Transaction> transactions;

    /**
     * A constructor for the TransactionHistory.
     */
    public TransactionHistory() {
        this.transactions = new ArrayList<>();
    }

    /**
     * Adds the given transaction to the record of all past transactions.
     * @param transaction The transaction to add to the record.
     * @requires The transaction to be recorded has to be finalised.
     */
    public void recordTransaction(Transaction transaction) {
        // Only add the transaction to history if it is finalised.
        if (transaction.isFinalised()) {
            this.transactions.add(transaction);
        }
    }

    /**
     * Retrieves the most recent transaction.
     * @return The most recent transaction added to the record.
     */
    public Transaction getLastTransaction() {
        return transactions.getLast();
    }

    /**
     * Calculates the gross earnings, i.e. total income, from all transactions.
     * @return The gross earnings from all transactions in history, in cents.
     */
    public int getGrossEarnings() {
        int total = 0;
        for (Transaction transaction : this.transactions) {
            total += transaction.getTotal();
        }
        return total;
    }

    /**
     * Calculates the gross earnings, i.e. total income,
     * from all sales of a particular product type.
     *
     * @param type The Barcode of the item of interest.
     * @return The gross earnings from all sales of the product type, in cents.
     */
    public int getGrossEarnings(Barcode type) {
        int total = 0;
        for (Transaction transaction : this.transactions) {
            if (transaction instanceof CategorisedTransaction) {
                total += ((CategorisedTransaction) transaction).getPurchaseSubtotal(type);
            } else {
                // Manually calculate the earnings of a specific type for basic transactions.
                for (Product product : transaction.getPurchases()) {
                    if (product.getBarcode().equals(type)) {
                        total += product.getBasePrice();
                    }
                }
            }
        }
        return total;
    }

    /**
     * Calculates the number of transactions made.
     * @return The number of transactions in total.
     */
    public int getTotalTransactionsMade() {
        return this.transactions.size();
    }

    /**
     * Calculates the number of products sold over all transactions.
     * @return The total number of products sold.
     */
    public int getTotalProductsSold() {
        int total = 0;
        for (Transaction transaction : this.transactions) {
            total += transaction.getPurchases().size();
        }
        return total;
    }

    /**
     * Calculates the number of sold of a particular product type, over all transactions.
     * @param type The Barcode for the product of interest
     * @return The total number of products sold, for that particular product.
     */
    public int getTotalProductsSold(Barcode type) {
        int total = 0;
        for (Transaction transaction : this.transactions) {
            for (Product product : transaction.getPurchases()) {
                if (product.getBarcode().equals(type)) {
                    total++;
                }
            }
        }
        return total;
    }

    /**
     * Retrieves the transaction with the highest gross earnings, i.e. reported total.
     * If there are multiple return the one that first was recorded.
     * @return The transaction with the highest gross earnings.
     */
    public Transaction getHighestGrossingTransaction() {
        Transaction highest = null;
        int highestEarnings = 0;

        for (Transaction transaction : this.transactions) {
            // If same earnings amount, keep the one that was first recorded.
            if (transaction.getTotal() > highestEarnings) {
                highest = transaction;
                highestEarnings = transaction.getTotal();
            }
        }
        return highest;
    }

    /**
     * Calculates which type of product has had the highest quantity sold overall.
     * <p></p>
     * If two products have sold the same quantity resulting in a tie,
     * return the one appearing first in the Barcode enum.
     * @return The identifier for the product type of most popular product.
     */
    public Barcode getMostPopularProduct() {
        Map<Barcode, Integer> products = new HashMap<>();

        // Record all products bought and the quantity bought.
        for (Transaction transaction : this.transactions) {
            List<Product> productList = transaction.getPurchases();
            for (Product product : productList) {
                if (products.containsKey(product.getBarcode())) {
                    products.put(product.getBarcode(), products.get(product.getBarcode()) + 1);
                } else {
                    products.put(product.getBarcode(), 1);
                }
            }
        }

        int highestTotal = 0;
        Barcode highest = null;

        // Find the product with the highest quantity sold.
        for (Barcode barcode : products.keySet()) {
            if (products.get(barcode) > highestTotal) {
                highestTotal = products.get(barcode);
                highest = barcode;
            } else if (products.get(barcode) == highestTotal) {
                // If two products have the same quantity sold,
                // record the product that appears first in the Barcode enum.
                for (Barcode orderedBarcode : Barcode.values()) {
                    if (orderedBarcode.equals(highest) || orderedBarcode.equals(barcode)) {
                        highest = orderedBarcode;
                    }
                }
            }
        }
        return highest;
    }

    /**
     * Calculates the average amount spent by customers across all transactions.
     * @return The average amount spent overall, in cents.
     */
    public double getAverageSpendPerVisit() {
        if (transactions.isEmpty()) {
            return 0.0d;
        }
        return (double) getGrossEarnings() / getTotalTransactionsMade();
    }

    /**
     * Calculates the average amount a product has been discounted by,
     * across all sales of that product.
     * @param type The identifier of the product of interest.
     * @return The average discount for the product, in cents
     */
    public double getAverageProductDiscount(Barcode type) {

        // Return 0.0 if no products were sold
        if (transactions.isEmpty()) {
            return 0.0d;
        }

        double nonDiscount = (double) getTotalProductsSold(type) * type.getBasePrice();

        return (nonDiscount - (double) getGrossEarnings(type)) / getTotalProductsSold(type);
    }
}