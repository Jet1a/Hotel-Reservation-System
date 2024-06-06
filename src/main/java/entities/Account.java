package entities;

import exception.ParameterException;

import java.io.Serializable;

public class Account implements Serializable {
    private final String accountId;
    private final String customerId;

    public Account(String accountId, Customer customer) {
        if (accountId == null || accountId.isEmpty()) throw new ParameterException("Account ID is null or empty");
        if (customer == null) throw new ParameterException("Customer is null");
        this.accountId = accountId;
        this.customerId = customer.getId();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "\n--------- Accounts ---------"
                + "\nAccount ID: " + accountId
                + "\nCustomer ID: " + customerId
                + "\n----------------------------";
    }
}
