package farm.inventory.product;

import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;

public class Jam extends Product {

    public Jam() {
        super(Barcode.JAM, Quality.REGULAR, Barcode.JAM.getBasePrice());
    }

    public Jam(Quality quality) {
        super(Barcode.JAM, quality, Barcode.JAM.getBasePrice());
    }
}
