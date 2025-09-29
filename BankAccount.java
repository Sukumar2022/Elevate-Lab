import java.util.ArrayList;
import java.util.List;

class Transaction {
    private String type;
    private double amount;
    private double balanceAfter;

    public Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    @Override
    public String toString() {
        return type + ": (rs.)" + amount + " | Balance after: (rs.)" + balanceAfter;
    }
}

class Account {
    private String accountNumber;
    private String owner;
    private double balance;
    private List<Transaction> history;

    public Account(String accountNumber, String owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = 0.0;
        this.history = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        balance += amount;
        history.add(new Transaction("Deposit", amount, balance));
        System.out.println("Deposited (rs.)" + amount + " successfully.");
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return;
        }
        balance -= amount;
        history.add(new Transaction("Withdraw", amount, balance));
        System.out.println("Withdrew (rs.)" + amount + " successfully.");
    }

    public double getBalance() {
        return balance;
    }

    public void printTransactionHistory() {
        System.out.println("\nTransaction History for " + owner + " (Account: " + accountNumber + "):");
        for (Transaction t : history) {
            System.out.println(t);
        }
    }
}

public class BankAccount {
    public static void main(String[] args) {
        Account acc1 = new Account("4052020", "Sukumar");

        acc1.deposit(500);
        acc1.withdraw(200);
        acc1.deposit(150);
        acc1.withdraw(1000);

        System.out.println("\nFinal Balance: $" + acc1.getBalance());
        acc1.printTransactionHistory();
    }
}
