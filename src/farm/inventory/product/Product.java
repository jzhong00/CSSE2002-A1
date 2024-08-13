package farm.inventory.product;

import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;

import java.util.Objects;

public abstract class Product {
    private Barcode barcode;
    private Quality quality;
    private int price;

    public Barcode getBarcode() {
        return this.barcode;
    }
    public String getDisplayName() {
        return this.barcode.getDisplayName();
    }

    public Quality getQuality() {
        return this.quality;
    }

    public int getBasePrice() {
        return this.price;
    }

    @Override public String toString() {
        return this.barcode.getDisplayName() + ": " + this.price + "c *" + this.quality + "*";
    }

    @Override public boolean equals(Object obj) {
        // Two products are equal if they have the same barcode and quality
        Product other = (Product) obj;
        return other.quality == this.quality && other.barcode == this.barcode;
    }
    @Override public int hashCode() {
        return Objects.hash(barcode, quality);
    }
}
