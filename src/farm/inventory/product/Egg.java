package farm.inventory.product;

import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;

public class Egg extends Product {

    public Egg() {
        super(Barcode.EGG, Quality.REGULAR, Barcode.EGG.getBasePrice());
    }

    public Egg(Quality quality) {
        super(Barcode.EGG, quality, Barcode.EGG.getBasePrice());
    }
}
