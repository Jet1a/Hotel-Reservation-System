package repository;

import entities.Account;
import entities.Customer;

import java.util.Collection;

public interface AccountRepository {
    Account getAccount(String id);

    Account create(Customer customer);

    boolean update(Account account);

    boolean delete(Account account);

    Collection<Account> getAllAccounts();
}
