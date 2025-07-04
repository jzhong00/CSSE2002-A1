package farm.inventory.product;

import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;

/**
 * A class representing an instance of wool.
 */
public class Wool extends Product {

    /**
     * Create a wool instance with no additional details.
     * Item quality is not specified, so will default to be REGULAR.
     */
    public Wool() {
        super(Barcode.WOOL, Quality.REGULAR, Barcode.WOOL.getBasePrice());
    }

    /**
     * Create a wool instance with a specific quality value.
     * @param quality The quality level to assign to this wool.
     */
    public Wool(Quality quality) {
        super(Barcode.WOOL, quality, Barcode.WOOL.getBasePrice());
    }
}
