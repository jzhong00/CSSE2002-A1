package farm.inventory.product;

import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;

public class Wool extends Product {

    public Wool() {
        super(Barcode.WOOL, Quality.REGULAR, Barcode.WOOL.getBasePrice());
    }

    public Wool(Quality quality) {
        super(Barcode.WOOL, quality, Barcode.WOOL.getBasePrice());
    }
}
