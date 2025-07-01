package farm.inventory.product;

import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;

/**
 * A class representing an instance of jam.
 */
public class Jam extends Product {

    /**
     * Create a jam instance with no additional details.
     * Item quality is not specified, so will default to be REGULAR.
     */
    public Jam() {
        super(Barcode.JAM, Quality.REGULAR, Barcode.JAM.getBasePrice());
    }

    /**
     * Create a jam instance with a quality value.
     * @param quality The quality level to assign to this jam.
     */
    public Jam(Quality quality) {
        super(Barcode.JAM, quality, Barcode.JAM.getBasePrice());
    }
}
