package repository.memory;

import entities.Account;
import entities.Customer;
import repository.AccountRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AccountMemoryRepository implements AccountRepository {
    private final Map<String, Account> accounts;
    private long nextId = 1;

    public AccountMemoryRepository() {
        accounts = new HashMap<>();
    }

    @Override
    public Account getAccount(String id) {
        return accounts.get(id);
    }

    @Override
    public Account create(Customer customer) {
        if (customer == null) return null;
        var number = String.format("A%d", nextId);
        if (accounts.containsKey(number)) return null;
        var account = new Account(number, customer);
        accounts.put(number, account);
        ++nextId;
        return account;
    }

    @Override
    public boolean update(Account account) {
        if (account == null) return false;
        accounts.replace(account.getAccountId(), account);
        return true;
    }

    @Override
    public boolean delete(Account account) {
        if (account == null) return false;
        return accounts.remove(account.getAccountId(), account);
    }

    @Override
    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }
}
