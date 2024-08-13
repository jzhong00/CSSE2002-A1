package farm.inventory.product;

import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;

public class Egg extends Product {
    private final Barcode barcode;
    private final Quality quality;
    private final int price;
    private final String name;

    public Egg() {
        this.barcode = Barcode.EGG;
        this.price = Barcode.EGG.getBasePrice();
        this.name = Barcode.EGG.getDisplayName();
        this.quality = Quality.REGULAR;
    }

    public Egg(Quality quality) {
        this.barcode = Barcode.EGG;
        this.price = Barcode.EGG.getBasePrice();
        this.name = Barcode.EGG.getDisplayName();
        this.quality = quality;
    }
}
