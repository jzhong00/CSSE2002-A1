package farm.inventory.product;

import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;

public class Jam extends Product {
    private final String name;

    public Jam() {
        this.barcode = Barcode.JAM;
        this.price = Barcode.JAM.getBasePrice();
        this.name = Barcode.JAM.getDisplayName();
        this.quality = Quality.REGULAR;
    }
    public Jam(Quality quality) {
        this.barcode = Barcode.JAM;
        this.price = Barcode.JAM.getBasePrice();
        this.name = Barcode.JAM.getDisplayName();
        this.quality = quality;
    }
}
