package farm.sales;

import farm.inventory.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addProduct(Product product) {
        this.items.add(product);
        System.out.println(this.items);
    }

    public List<Product> getContents() {
        return new ArrayList<>(this.items);
    }

    public void setEmpty() {
        this.items.clear();
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }
}
