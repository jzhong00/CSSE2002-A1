package farm.inventory.product;

import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;

public class Wool extends Product {
    private final String name;

    public Wool() {
        this.barcode = Barcode.WOOL;
        this.price = Barcode.WOOL.getBasePrice();
        this.name = Barcode.WOOL.getDisplayName();
        this.quality = Quality.REGULAR;
    }
    public Wool(Quality quality) {
        this.barcode = Barcode.WOOL;
        this.price = Barcode.WOOL.getBasePrice();
        this.name = Barcode.WOOL.getDisplayName();
        this.quality = quality;
    }
}
