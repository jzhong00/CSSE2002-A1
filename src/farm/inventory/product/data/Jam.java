package farm.inventory.product.data;

import farm.inventory.product.Product;

public class Jam extends Product {
    private final String name;
    private final Barcode barcode;
    private final Quality quality;
    private final int price;
    
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
