package farm.inventory.product.data;

import farm.inventory.product.Product;

public class Wool extends Product {
    private final String name;
    private final Barcode barcode;
    private final Quality quality;
    private final int price;
    
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
