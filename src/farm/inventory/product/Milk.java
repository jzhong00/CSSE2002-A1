package farm.inventory.product;

import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;

public class Milk extends Product {

    public Milk() {
        super(Barcode.MILK, Quality.REGULAR, Barcode.MILK.getBasePrice());
    }

    public Milk(Quality quality) {
        super(Barcode.MILK, quality, Barcode.MILK.getBasePrice());
    }
}
