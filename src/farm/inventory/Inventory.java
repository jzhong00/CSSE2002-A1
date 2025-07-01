package farm.inventory;

import farm.core.FailedTransactionException;
import farm.core.InvalidStockRequestException;
import farm.inventory.product.Product;
import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;

import java.util.List;

/**
 * An interface representing the base requirements for an Inventory.
 * Handles adding and removing items from storage.
 */
public interface Inventory {

    /**
     * Adds a new product with corresponding barcode to the inventory.
     * @param barcode The barcode of the product to add.
     * @param quality The quality of added product.
     */
    void addProduct(Barcode barcode, Quality quality);

    /**
     * Adds the specified quantity of the product with corresponding barcode to the inventory,
     * provided that the implementing inventory supports adding multiple products at once.
     * @param barcode The barcode of the product to add.
     * @param quality The quality of added product.
     * @param quantity The amount of the product to add.
     * @throws InvalidStockRequestException If the inventory is not fancy.
     */
    void addProduct(Barcode barcode, Quality quality, int quantity)
            throws InvalidStockRequestException;

    /**
     * Determines if a product exists in the inventory with the given barcode.
     * @param barcode The barcode of the product to check.
     * @return True if and only if the product exists.
     */
    boolean existsProduct(Barcode barcode);

    /**
     * Retrieves the full stock currently held in the inventory.
     * @return A list containing all products currently stored in the inventory.
     */
    List<Product> getAllProducts();

    /**
     * Removes the first product with corresponding barcode from the inventory.
     * @param barcode The barcode of the product to be removed.
     * @return A list containing the removed product if it exists, else an empty list.
     */
    List<Product> removeProduct(Barcode barcode);

    /**
     * Removes the given number of products with corresponding barcode from the inventory,
     * provided that the implementing inventory supports removing multiple products at once.
     * The method removes as many items as possible based on the inventory.
     * @param barcode The barcode of the product to be removed.
     * @param quantity The total amount of the product to remove from the inventory.
     * @return A list containing the removed product if it exists, else an empty list.
     * @throws FailedTransactionException If the inventory is not fancy.
     */
    List<Product> removeProduct(Barcode barcode, int quantity) throws FailedTransactionException;

}