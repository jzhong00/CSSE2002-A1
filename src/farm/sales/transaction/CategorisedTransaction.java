package farm.sales.transaction;

import farm.customer.Customer;
import farm.inventory.product.Product;
import farm.inventory.product.data.Barcode;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CategorisedTransaction extends Transaction {
    public CategorisedTransaction(Customer customer) {
        super(customer);
    }

    public Set<Barcode> getPurchasedTypes() {
        Set<Barcode> barcodes = new HashSet<>();
        for (Product purchase : purchases) {
            barcodes.add(purchase.getBarcode());
        }
        return barcodes;
    }

    public Map<Barcode, List<Product>> getPurchasesByType() {
        return;
    }
}
