package repository;

import entities.Customer;

import java.util.Collection;

public interface CustomerRepository {
    Customer create(String name);

    boolean update(Customer customer);

    boolean delete(String id);

    Customer getCustomer(String id);

    Collection<Customer> getAllCustomers();

    Collection<Customer> getCustomerByName(String customerName);
}
