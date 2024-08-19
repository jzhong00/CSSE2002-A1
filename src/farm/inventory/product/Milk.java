package farm.inventory.product;

import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;

public class Milk extends Product {
    private final String name;

    public Milk() {
        this.barcode = Barcode.MILK;
        this.price = this.barcode.getBasePrice();
        this.name = this.barcode.getDisplayName();
        this.quality = Quality.REGULAR;
    }

    public Milk(Quality quality) {
        this.barcode = Barcode.MILK;
        this.price = this.barcode.getBasePrice();
        this.name = this.barcode.getDisplayName();
        this.quality = quality;
    }
}