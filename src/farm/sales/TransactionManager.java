package farm.sales;

import farm.core.FailedTransactionException;
import farm.sales.transaction.Transaction;

import farm.inventory.product.Product;

public class TransactionManager {
    private Transaction currentTransaction;

    public TransactionManager() {
        this.currentTransaction = null;
    }

    public boolean hasOngoingTransaction() {
        return currentTransaction != null;
    }

    public void setOngoingTransaction(Transaction transaction) throws FailedTransactionException {
        if (hasOngoingTransaction()) {
            throw new FailedTransactionException();
        }
        this.currentTransaction = transaction;
    }

    public void registerPendingPurchase(Product product)
                             throws FailedTransactionException {
        if (!hasOngoingTransaction() || this.currentTransaction.isFinalised()) {
            throw new FailedTransactionException();
        }
        currentTransaction.getAssociatedCustomer().getCart().addProduct(product);
    }

    public Transaction closeCurrentTransaction()
                                    throws FailedTransactionException {
        if (!hasOngoingTransaction()) {
            throw new FailedTransactionException("No ongoing transaction");
        }
        this.currentTransaction.finalise();
        Transaction closedTransaction = currentTransaction;
        this.currentTransaction = null;
        return closedTransaction;

    }



}
