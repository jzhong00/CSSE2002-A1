package farm.sales;

import farm.core.FailedTransactionException;
import farm.sales.transaction.Transaction;

import farm.inventory.product.Product;

/**
 * The controlling class for all transactions.
 */
public class TransactionManager {
    private Transaction currentTransaction;

    /**
     * Constructor for TransactionManager.
     */
    public TransactionManager() {
        this.currentTransaction = null;
    }

    /**
     * Determine whether a transaction is currently in progress.
     * @return True if and only if a transaction is in progress.
     */
    public boolean hasOngoingTransaction() {
        return currentTransaction != null;
    }

    /**
     * Begins managing the specified transaction, provided one is not already ongoing.
     * @param transaction The transaction to set as the manager's ongoing transaction.
     * @throws FailedTransactionException If and only if a transaction is already in progress.
     */
    public void setOngoingTransaction(Transaction transaction) throws FailedTransactionException {
        if (hasOngoingTransaction()) {
            throw new FailedTransactionException();
        }
        this.currentTransaction = transaction;
    }

    /**
     * Adds the given product to the cart of the customer associated with the current transaction.
     * The product can only be added if there is currently an ongoing transaction
     * and that transaction has not already been finalised.
     *
     * @param product The product to add to customer's cart.
     * @throws FailedTransactionException If and only if there is no ongoing transaction
     *                                    or the transaction has already been finalised.
     * @requires The provided product is known to be valid for purchase,
     *           i.e. has been successfully retrieved from the farm's inventory
     */
    public void registerPendingPurchase(Product product)
                             throws FailedTransactionException {
        if (!hasOngoingTransaction() || this.currentTransaction.isFinalised()) {
            throw new FailedTransactionException();
        }
        currentTransaction.getAssociatedCustomer().getCart().addProduct(product);
    }

    /**
     * Finalises the currently ongoing transaction
     * and makes readies the TransactionManager to accept a new ongoing transaction.
     * @return The finalised transaction.
     * @throws FailedTransactionException If and only if there is no
     *                                    currently ongoing transaction to close.
     */
    public Transaction closeCurrentTransaction()
                                    throws FailedTransactionException {
        if (!hasOngoingTransaction()) {
            throw new FailedTransactionException("No ongoing transaction");
        }
        // Empty the customer's cart and finalise the transaction.
        this.currentTransaction.finalise();

        Transaction closedTransaction = currentTransaction;

        this.currentTransaction = null;
        return closedTransaction;

    }



}
