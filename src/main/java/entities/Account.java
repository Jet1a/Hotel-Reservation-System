package entities;

public class Account {
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
        return "Account : [" + "accountId='" + accountId + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
