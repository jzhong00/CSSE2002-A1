package farm.customer;
import farm.sales.Cart;

import java.util.Objects;

public class Customer {
    private String name;
    private int phoneNumber;
    private String address;
    private Cart cart;

    public Customer(String name, int phoneNumber, String address) {
        if (name == null || name.isEmpty() || address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Name and address cannot be empty");
        }
        else if (phoneNumber < 0) {
            throw new IllegalArgumentException("Phone number cannot be negative");
        }
        this.name = name.trim();
        this.phoneNumber = phoneNumber;
        this.address = address.trim();
        this.cart = new Cart();
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = newName.trim();
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int newPhone) {
        if (newPhone <= 0) {
            throw new IllegalArgumentException("Phone number must be positive");
        }
        this.phoneNumber = newPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String newAddress) {
        if (newAddress == null || newAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        this.address = newAddress.trim();
    }

    public Cart getCart() {
        return this.cart;
    }

    @Override public String toString() {
        return "Name: " + name + " | Phone: " + phoneNumber + " | Address: " + address;
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        Customer other = (Customer) obj;
        return phoneNumber == other.phoneNumber && Objects.equals(name, other.name);
    }

    @Override public int hashCode() {
        return Objects.hash(name, phoneNumber);
    }

}
