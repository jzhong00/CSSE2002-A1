package farm.customer;

import farm.core.CustomerNotFoundException;
import farm.core.DuplicateCustomerException;

import java.util.ArrayList;
import java.util.List;


/**
 * The address book where the farmer stores their customers' details.
 * Keeps track of all the customers that come and visit the Farm.
 */
public class AddressBook {
    private ArrayList<Customer> customers;

    /**
     * The constructor for the AddressBook.
     */
    public AddressBook() {
        this.customers = new ArrayList<>();
    }


    /**
     * Adds a new customer to the address book.
     * @param customer The customer to add to the address book.
     * @throws DuplicateCustomerException If the customer already exists.
     * @ensures The address book contains no duplicate customers.
     */
    public void addCustomer(Customer customer) throws DuplicateCustomerException {
        if (this.containsCustomer(customer)) {
            // Include a message with the string representation of the duplicate customer.
            throw new DuplicateCustomerException(customer.toString());
        }
        this.customers.add(customer);

    }

    /**
     * Retrieve all customer records stored in the address book.
     * @return A list of all customers in the address book.
     * @ensures The returned list is a shallow copy.
     */
    public List<Customer> getAllRecords() {
        // Return a shallow copy of the customers list
        return new ArrayList<>(customers);
    }

    /**
     * Checks to see if a customer is already in the address book.
     * @param customer The customer to check
     * @return True if and only if the customer already exists.
     */
    public boolean containsCustomer(Customer customer) {
        for (Customer customers : this.customers) {
            if (customers.equals(customer)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Lookup a customer in address book, if they exist using their details.
     * @param name The name of the customer to lookup.
     * @param phoneNumber The phone number of the customer.
     * @return The customer if and only if they exist in the address book.
     * @throws CustomerNotFoundException If there is no customer matching the information in the address book.
     * @requires That the name is non-empty and has been stripped of its trailing whitespace
     *           and that the phone number is a positive number.
     */
    public Customer getCustomer(String name, int phoneNumber) throws CustomerNotFoundException {
        for (Customer customer : this.customers) {
            // A customer is uniquely identified by their name and phone number.
            if (customer.getName().equals(name) && customer.getPhoneNumber() == phoneNumber) {
                return customer;
            }
        }
        throw new CustomerNotFoundException();
    }
}
