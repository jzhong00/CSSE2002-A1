
package farm.inventory;

import farm.core.FailedTransactionException;
import farm.core.InvalidStockRequestException;
import farm.inventory.product.*;
import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FancyInventory implements Inventory {
    List<Product> products;

    public FancyInventory() {
        this.products = new ArrayList<>();
    }

    @Override
    public void addProduct(Barcode barcode, Quality quality) {
        Product product = getProductByBarcode(barcode, quality);
        this.products.add(product);
    }

    @Override
    public void addProduct(Barcode barcode, Quality quality, int quantity) throws InvalidStockRequestException {
        Product product = getProductByBarcode(barcode, quality);
        for (int i = 0; i < quantity; i++) {
            this.products.add(product);
        }
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
        List<Product> organisedProducts = new ArrayList<>();

        // Iterate over all barcode values in order
        for (Barcode barcode : Barcode.values()) {
            for (Product product : this.products) {
                if (barcode.equals(product.getBarcode())) {
                    organisedProducts.add(product);
                }
            }
        }
        return organisedProducts;
    }

    @Override
    public List<Product> removeProduct(Barcode barcode) {
        List<Product> productsToRemove = new ArrayList<>();

        this.products.sort(Comparator.comparing(Product::getQuality).reversed());

        for (Product product : this.products) {
            if (barcode.equals(product.getBarcode())) {
                productsToRemove.add(product);
                this.products.remove(product);
                break;
            }
        }
        return productsToRemove;
    }

    @Override
    public List<Product> removeProduct(Barcode barcode, int quantity) throws FailedTransactionException {
        List<Product> removedProduct = new ArrayList<>();
        List<Product> availableProducts = new ArrayList<>();
        for (Product product : this.products) {
            if (barcode.equals(product.getBarcode())) {
                availableProducts.add(product);
            }
        }
        System.out.println(availableProducts);

        if (availableProducts.size() <= quantity) {
            removedProduct.addAll(availableProducts);
        } else {
            // Remove items with the highest quality
            availableProducts.sort(Comparator.comparing(Product::getQuality).reversed());
            for (int i = 0; i < quantity; i++) {
                Product product = availableProducts.get(i);
                removedProduct.add(product);
            }
        }
        this.products.removeAll(removedProduct);
        return removedProduct;
    }

    public int getStockedQuantity(Barcode barcode) {
        int quantity = 0;
        for (Product product : this.products) {
            if (barcode.equals(product.getBarcode())) {
                quantity++;
            }
        }
        return quantity;
    }

    private Product getProductByBarcode(Barcode barcode, Quality quality) {
        return switch (barcode) {
            case EGG -> new Egg(quality);
            case JAM -> new Jam(quality);
            case MILK -> new Milk(quality);
            case WOOL -> new Wool(quality);
        };
    }
}

