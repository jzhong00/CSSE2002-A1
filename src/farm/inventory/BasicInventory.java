package farm.inventory;

import farm.core.FailedTransactionException;
import farm.core.InvalidStockRequestException;
import farm.inventory.product.*;
import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;

import java.util.ArrayList;
import java.util.List;


/**
 * A very basic inventory that both stores and handles products individually.
 * Only supports operation on single Products at a time.
 */
public class BasicInventory implements Inventory {
    private List<Product> products;

    /**
     * A constructor for the BasicInventory.
     */
    public BasicInventory() {
        this.products = new ArrayList<>();
    }

    @Override
    public void addProduct(Barcode barcode, Quality quality) {
        Product product = getProductByBarcode(barcode, quality);
        this.products.add(product);
    }

    @Override
    public void addProduct(Barcode barcode, Quality quality, int quantity)
            throws InvalidStockRequestException {

        throw new InvalidStockRequestException(
                "Current inventory is not fancy enough. Please supply products one at a time."
        );
    }

    @Override
    public boolean existsProduct(Barcode barcode) {
        for (Product product : this.products) {
            if (barcode.equals(product.getBarcode())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Product> getAllProducts() {
        return this.products;
    }

    @Override
    public List<Product> removeProduct(Barcode barcode) {
        List<Product> productsToRemove = new ArrayList<>();
        for (Product product : this.products) {
            if (barcode.equals(product.getBarcode())) {
                productsToRemove.add(product);
                break;
            }
        }
        this.products.removeAll(productsToRemove);
        return productsToRemove;
    }

    @Override
    public List<Product> removeProduct(Barcode barcode, int quantity)
            throws FailedTransactionException {

        throw new FailedTransactionException(
                "Current inventory is not fancy enough. Please purchase products one at a time."
        );
    }

    /**
     * Helper function that returns the product based on its barcode and quality.
     * @param barcode The product's barcode.
     * @param quality The product's quality.
     * @return The product instance.
     */
    private Product getProductByBarcode(Barcode barcode, Quality quality) {
        return switch (barcode) {
            case EGG -> new Egg(quality);
            case JAM -> new Jam(quality);
            case MILK -> new Milk(quality);
            case WOOL -> new Wool(quality);
        };
    }
}


