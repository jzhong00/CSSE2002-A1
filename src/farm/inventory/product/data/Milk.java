package farm.inventory.product.data;

import farm.inventory.product.Product;

public class Milk extends Product {
    private final String name;
    private final Barcode barcode;
    private final Quality quality;
    private final int price;
    
    public Milk() {
        this.barcode = Barcode.MILK;
        this.price = Barcode.MILK.getBasePrice();
        this.name = Barcode.MILK.getDisplayName();
        this.quality = Quality.REGULAR;
    }
    public Milk(Quality quality) {
        this.barcode = Barcode.MILK;
        this.price = Barcode.MILK.getBasePrice();
        this.name = Barcode.MILK.getDisplayName();
        this.quality = quality;
    }
}
