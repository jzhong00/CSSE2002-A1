package farm.sales;

import farm.inventory.product.Product;
import farm.inventory.product.data.Barcode;
import farm.sales.transaction.CategorisedTransaction;
import farm.sales.transaction.SpecialSaleTransaction;
import farm.sales.transaction.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class TransactionHistory {
    private List<Transaction> transactions;

    public TransactionHistory() {
        this.transactions = new ArrayList<>();
    }

    public void recordTransaction(Transaction transaction) {
        if (transaction.isFinalised()) {
            this.transactions.add(transaction);
        }
    }

    public Transaction getLastTransaction() {
        if (transactions.isEmpty()) {
            return null;
        }
        return transactions.get(transactions.size() - 1);
    }

    public int getGrossEarnings() {
        int total = 0;
        for (Transaction transaction : this.transactions) {
            System.out.println(transaction.getAssociatedCustomer().getName());
            total += transaction.getTotal();
        }
        return total;
    }

    public int getGrossEarnings(Barcode type) {
        int total = 0;
        for (Transaction transaction : this.transactions) {
            if (transaction instanceof SpecialSaleTransaction) {
                total += ((SpecialSaleTransaction) transaction).getPurchaseSubtotal(type);
            } else if (transaction instanceof CategorisedTransaction) {
                total += ((CategorisedTransaction) transaction).getPurchaseSubtotal(type);
            } else {
                for (Product product : transaction.getPurchases()) {
                    if (product.getBarcode().equals(type)) {
                        total += product.getBasePrice();
                    }
                }

            }
        }
        return total;
    }

    public int getTotalTransactionsMade() {
        return this.transactions.size();
    }

    public int getTotalProductsSold() {
        int total = 0;
        for (Transaction transaction : this.transactions) {
            total += transaction.getPurchases().size();
        }
        return total;
    }

    public int getTotalProductsSold(Barcode type) {
        int total = 0;
        for (Transaction transaction : this.transactions) {
            if (transaction instanceof SpecialSaleTransaction || transaction instanceof CategorisedTransaction) {
                total += ((CategorisedTransaction) transaction).getPurchaseQuantity(type);
            } else {
                for (Product product : transaction.getPurchases()) {
                    if (product.getBarcode().equals(type)) {
                        total++;
                    }
                }
            }
        }
        return total;
    }

    public Transaction getHighestGrossingTransaction() {
        Transaction highest = null;
        int highestEarnings = 0;

        for (Transaction transaction : this.transactions) {
            if (transaction.getTotal() > highestEarnings) {
                highest = transaction;
                highestEarnings = transaction.getTotal();
            }
        }
        return highest;
    }

    public Barcode getMostPopularProduct() {
        Map<Barcode, Integer> products = new HashMap<>();
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

        for (Barcode barcode : products.keySet()) {
            if (products.get(barcode) > highestTotal) {
                highestTotal = products.get(barcode);
                highest = barcode;
            }
        }
        return highest;
    }

    public double getAverageSpendPerVisit() {
        int totalSpent = 0;
        int numberOfTransactions = transactions.size();

        for (Transaction transaction : this.transactions) {
            totalSpent += transaction.getTotal();
        }
        if (numberOfTransactions == 0) {
            return 0.0d;
        }
        return (double) totalSpent / numberOfTransactions;
    }


    public double getAverageProductDiscount(Barcode type) {

        int totalDiscount = 0;
        int totalNum = 0;
        for (Transaction transaction : transactions) {
            if (transaction instanceof SpecialSaleTransaction) {
                totalNum++;
                totalDiscount += ((SpecialSaleTransaction) transaction).getDiscountAmount(type);
            }
        }
        // Return 0.0 if no products were sold
        if (totalNum == 0) {
            return 0.0d;
        }

        // Cast to double for accurate division
        return (double) totalDiscount / totalNum;
    }
}