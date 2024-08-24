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
import farm.inventory.product.Wool;
import farm.inventory.product.data.Barcode;
import farm.inventory.product.data.Quality;
import farm.sales.TransactionHistory;
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
        Inventory inventory = new BasicInventory();  // Create a new Inventory instance
        AddressBook addressBook = new AddressBook();  // Create a new AddressBook instance
        Farm farm = new Farm(inventory, addressBook);  // Pass Inventory and AddressBook to the Farm constructor
        ShopFront shopFront = new ShopFront();  // Create a new ShopFront instance

        FarmManager manager = new FarmManager(farm, shopFront);  // Pass Farm and ShopFront to the FarmManager constructor
        manager.run();  // Run the FarmManager
    }
}