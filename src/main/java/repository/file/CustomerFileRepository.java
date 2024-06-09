package repository.file;

import entities.Customer;
import repository.CustomerRepository;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerFileRepository implements CustomerRepository, Serializable {
    private final String filename = "customers.dat";
    private Map<String, Customer> customers;
    private long nextId;

    public CustomerFileRepository() {
        File file = new File(filename);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis);
                 ObjectInputStream ois = new ObjectInputStream(bis)) {
                customers = (Map<String, Customer>) ois.readObject();
                nextId = ois.readLong();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            customers = new HashMap<>();
            nextId = 1;
        }
    }

    private void saveToFile() {
        try (FileOutputStream fos = new FileOutputStream(filename);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(customers);
            oos.writeLong(nextId);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer create(String name) {
        var id = String.format("C%d", nextId);
        if (customers.containsKey(id)) return null;
        var customer = new Customer(id, name);
        customers.put(id, customer);
        ++nextId;
        saveToFile();
        return customer;
    }

    @Override
    public boolean update(Customer customer) {
        if (customer == null) return false;
        customers.put(customer.getId(), customer);
        return true;
    }

    @Override
    public boolean delete(String id) {
        if (id == null || customers.get(id) == null) return false;
        customers.remove(id);
        ++nextId;
        saveToFile();
        return true;
    }

    @Override
    public Customer getCustomer(String id) {
        if (id == null || customers.get(id) == null) return null;
        return customers.get(id);
    }

    @Override
    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

    @Override
    public Collection<Customer> getCustomerByName(String customerName) {
        return customers.values().stream().filter(c -> c.getName().equals(customerName)).toList();
    }
}
