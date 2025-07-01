package farm.inventory.product;

import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;

import java.util.Objects;

/**
 * An abstract class representing an instance of a product.
 * Each product is a single instance of a specific item.
 */
public abstract class Product {
    private Barcode barcode;
    private Quality quality;
    private int price;

    /**
     * Constructor for products to be used by the subclasses.
     * @param barcode The product's barcode.
     * @param quality The product's quality.
     * @param price The product's price.
     */
    protected Product(Barcode barcode, Quality quality, int price) {
        this.barcode = barcode;
        this.quality = quality;
        this.price = price;
    }

    /**
     * Accessor method for the product's identifier.
     * @return The product's barcode.
     */
    public Barcode getBarcode() {
        return this.barcode;
    }

    /**
     * Retrieve the product's display name, for visual/textual representation.
     * @return The product's display name.
     */
    public String getDisplayName() {
        return this.barcode.getDisplayName();
    }

    /**
     * Retrieve the product's quality.
     * @return The quality level for this product.
     */
    public Quality getQuality() {
        return this.quality;
    }

    /**
     * Retrieve the products base sale price.
     * @return The price of the product in cents.
     */
    public int getBasePrice() {
        return this.price;
    }

    /**
     * Returns a string representation of this product class.
     * @return The formatted string representation of the product.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // The string contains display name of the product, followed by its base price and quality.
        // It is in the form <name>:<price>c *<quality>*
        sb.append(this.barcode.getDisplayName())
                .append(": ")
                .append(this.price)
                .append("c *")
                .append(this.quality)
                .append("*");
        return sb.toString();
    }

    /**
     * If two instances of product are equal to each other.
     * Equality is defined by having the same barcode, and quality.
     * @param obj The object with which to compare
     * @return True if and not only the other object is a product with the same barcode,
     * and quality as the current product.
     */
    @Override
    public boolean equals(Object obj) {
        // Check if the current object is being compared to itself
        if (this == obj) {
            return true;
        }

        // Check if the provided object is null or if it belongs to a different class
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // Since we know the object belongs to the product class.
        Product other = (Product) obj;

        // Compare the barcode and quality fields
        return this.quality == other.quality && this.barcode.equals(other.barcode);
    }

    /**
     * A hashcode method that respects the equals(Object) method.
     * @return An appropriate hashcode value for this instance.
     */
    @Override
    public int hashCode() {
        // An object is uniquely identified by its barcode and quality.
        return Objects.hash(barcode, quality);
    }
}
