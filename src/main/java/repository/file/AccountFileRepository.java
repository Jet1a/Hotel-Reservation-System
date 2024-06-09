package repository.file;

import entities.Account;
import entities.Customer;
import repository.AccountRepository;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AccountFileRepository implements AccountRepository, Serializable {
    private final String filename = "accounts.dat";
    private Map<String, Account> accounts;
    private long nextId = 1;

    public AccountFileRepository() {
        File file = new File(filename);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis);
                 ObjectInputStream ois = new ObjectInputStream(bis)) {
                accounts = (Map<String, Account>) ois.readObject();
                nextId = ois.readLong();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            accounts = new HashMap<>();
            nextId = 1;
        }
    }

    private void saveToFile() {
        try (FileOutputStream fos = new FileOutputStream(filename);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(accounts);
            oos.writeLong(nextId);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        saveToFile();
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
        accounts.remove(account.getAccountId(), account);
        saveToFile();
        return true;
    }

    @Override
    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }
}
