package entities;

import java.io.Serializable;

public class Account implements Serializable {
    private final String accountId;
    private final String customerId;

    public Account(String accountId, Customer customer) {
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
        return "----------- Accounts -----------"
                + "\nAccount ID: " + accountId
                + "\nCustomer ID: " + customerId
        + "\n------------------------------";
    }
}
