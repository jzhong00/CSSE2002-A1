package farm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import farm.core.DuplicateCustomerException;
import farm.core.Farm;
import farm.core.ShopFront;
import farm.customer.AddressBook;
import farm.customer.Customer;
import farm.inventory.BasicInventory;
import farm.inventory.Inventory;
import farm.inventory.product.Egg;
import farm.inventory.product.Jam;
import farm.inventory.product.Milk;
import farm.inventory.product.data.Barcode;
import farm.sales.transaction.SpecialSaleTransaction;
import farm.sales.transaction.Transaction;
import farm.core.FarmManager;

/**
 * Execute the Farm MVP program.
 * This file is for you to execute your program,
 * it will not be marked.
 */
public class Main {

    /**
     * Start the farm program.
     * @param args Parameters to the program, currently not supported.
     */
    public static void main(String[] args) throws DuplicateCustomerException {
        Customer jack = new Customer("Jack", 01234567, "1st Street");

        jack.getCart().addProduct(new Egg());
        jack.getCart().addProduct(new Milk());
        jack.getCart().addProduct(new Jam());
        jack.getCart().addProduct(new Egg());
        jack.getCart().addProduct(new Milk());
        jack.getCart().addProduct(new Egg());

        Map<Barcode, Integer> discounts = new HashMap<>();
        discounts.put(Barcode.MILK, 50);
        discounts.put(Barcode.JAM, 0);

        Transaction transaction = new SpecialSaleTransaction(jack, discounts);
        transaction.finalise();

        System.out.println(transaction.getReceipt());
    }
}