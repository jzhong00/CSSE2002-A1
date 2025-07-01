package farm.customer;

import farm.sales.Cart;

import java.util.Objects;


/**
 * A customer who interacts with the farmer's business.
 */
public class Customer {
    private String name;
    private int phoneNumber;
    private String address;
    private Cart cart;


    /**
     * A constructor for the customer.
     * @param name The customer's name.
     * @param phoneNumber The phone number of the customer.
     * @param address The customer's address.
     * @requires The name and address are non-empty
     * @requires That the phone number is a positive number.
     * @requires That the name and address are stripped of trailing whitespaces.
     */
    public Customer(String name, int phoneNumber, String address) {
        if (name == null || name.isEmpty() || address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Name and address cannot be empty");
        } else if (phoneNumber < 0) {
            throw new IllegalArgumentException("Phone number cannot be negative");
        }

        // Strip the name and address of any trailing whitespace.
        this.name = name.trim();
        this.address = address.trim();
        this.phoneNumber = phoneNumber;
        this.cart = new Cart();
    }

    /**
     * Retrieve the name of the customer.
     * @return The customer's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Update the current name of the customer with a new one.
     * @param newName The new name to override the current name.
     * @ensures That the name is non-empty and that it's stripped of trailing whitespaces.
     */
    public void setName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = newName.trim();
    }

    /**
     * Retrieve the phone number of the customer.
     * @return The customer's phone number.
     */
    public int getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Set the current phone number of the customer to be newPhone.
     * @param newPhone The phone number to override the current phone number.
     * @requires The phone number is a positive number.
     */
    public void setPhoneNumber(int newPhone) {
        if (newPhone <= 0) {
            throw new IllegalArgumentException("Phone number must be positive");
        }
        this.phoneNumber = newPhone;
    }

    /**
     * Retrieve the address of the customer.
     * @return The customer's address.
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Set the current address of the customer to be newAddress.
     * @param newAddress The address to override the current address.
     * @requires That the address is non-empty and that its stripped of trailing whitespaces.
     */
    public void setAddress(String newAddress) {
        if (newAddress == null || newAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        this.address = newAddress.trim();
    }

    /**
     * Retrieves the customers cart.
     * @return The customer's cart.
     */
    public Cart getCart() {
        return this.cart;
    }

    /**
     * Returns a string representation of this customer class.
     * @return The formatted string representation of the customer.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name)
          .append(" | Phone Number: ").append(phoneNumber)
          .append(" | Address: ").append(address);
        return sb.toString();
    }

    /**
     * Determines whether the provided object is equal to this customer instance.
     * For customers, equality is defined by having the same phone number and name.
     * @param obj The object with which to compare
     * @return True if the other object is a customer with the same
     *         phone number and name as the current customer.
     */
    @Override
    public boolean equals(Object obj) {
        // Check if the given object is the same as the current object.
        if (this == obj) {
            return true;
        }
        // Check if the object is null or belonging to a different class.
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Since we know the object is a Customer, we can cast the provided object.
        Customer other = (Customer) obj;

        // Compare the name and phone fields.
        return Objects.equals(this.phoneNumber, other.phoneNumber)
                && Objects.equals(this.name, other.name);
    }

    /**
     * A hashcode method that respects the equals(Object) method.
     * @return An appropriate hashcode value for this instance.
     */
    @Override
    public int hashCode() {
        // Generate a hashcode based on the customer's name and phone number
        // to ensure consistency with the equals(Object) method.
        return Objects.hash(this.name, this.phoneNumber);
    }

}
