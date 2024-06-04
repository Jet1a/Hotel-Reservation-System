package repository.memory;

import entities.Customer;
import repository.CustomerRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerMemoryRepository implements CustomerRepository {
    private final Map<String, Customer> customers;
    private long nextId = 1;

    public CustomerMemoryRepository() {
        customers = new HashMap<>();
    }

    @Override
    public Customer create(String name) {
        var id = String.format("C%d", nextId);
        if (customers.containsKey(id)) return null;
        var customer = new Customer(id, name);
        customers.put(id, customer);
        ++nextId;
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
