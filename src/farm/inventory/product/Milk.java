package farm.inventory.product;

import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;

/**
 * A class representing an instance of milk.
 */
public class Milk extends Product {

    /**
     * Create a milk instance with no additional details.
     * Item quality is not specified, so will default to be REGULAR.
     */
    public Milk() {
        super(Barcode.MILK, Quality.REGULAR, Barcode.MILK.getBasePrice());
    }

    /**
     * Create a milk instance with a quality value.
     * @param quality The quality level to assign to this milk.
     */
    public Milk(Quality quality) {
        super(Barcode.MILK, quality, Barcode.MILK.getBasePrice());
    }
}
