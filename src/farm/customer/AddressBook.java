package farm.customer;

import farm.core.CustomerNotFoundException;
import farm.core.DuplicateCustomerException;

import java.util.ArrayList;
import java.util.List;

public class AddressBook {
    private ArrayList<Customer> customers;

    public AddressBook() {
        this.customers = new ArrayList<>();
    }


    public void addCustomer(Customer customer) throws DuplicateCustomerException {
        if (containsCustomer(customer)) {
            throw new DuplicateCustomerException();
        }
        customers.add(customer);

    }

    public List<Customer> getAllRecords() {
        return new ArrayList<>(customers);
    }

    public boolean containsCustomer(Customer customer) {
        for (Customer customers : this.customers) {
            if (customers.equals(customer)) {
                return true;
            }
        }
        return false;
    }

    public Customer getCustomer(String name, int phoneNumber) throws CustomerNotFoundException {
        for (Customer customer : this.customers) {
            if (customer.getName() == name && customer.getPhoneNumber() == phoneNumber) {
                return customer;
            }
        }
        throw new CustomerNotFoundException();
    }
}
