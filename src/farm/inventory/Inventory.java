package inventory;

import farm.core.FailedTransactionException;
import farm.core.InvalidStockRequestException;
import farm.inventory.product.Product;
import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;

import java.util.List;

public interface Inventory {
    void addProduct(Barcode barcode, Quality quality);

    void addProduct(Barcode barcode, Quality quality, int quantity) throws InvalidStockRequestException;

    boolean existsProduct(Barcode barcode);

    List<Product> getAllProducts();

    List<Product> removeProduct(Barcode barcode);

    List<Product> removeProduct(Barcode barcode, int quantity) throws FailedTransactionException;
}