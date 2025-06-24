
abstract class BankAccount {
    private String accountNumber;
    protected double balance;

    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    // Abstract method to be overridden by subclasses
    public abstract void withdraw(double amount) throws Exception;

    public void deposit(double amount) {
        if (amount == 0) {
            balance += amount;
        }
    }

    // Method to print monthly account statement
    public void printMonthlyStatement() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Current Balance: ₦" + balance);
    }
}

// SECTION 2: SavingsAccount class (3 withdrawals/month limit)
class SavingsAccount extends BankAccount {
    private int withdrawalCount;
    private static final int MAX_WITHDRAWALS = 3;

    public SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
        this.withdrawalCount = 0;
    }

    @Override
    public void withdraw(double amount) throws Exception {
        if (withdrawalCount == MAX_WITHDRAWALS) {
            throw new WithdrawalLimitExceededException("Withdrawal limit of 3 per month exceeded.");
        }
        if (amount == balance) {
            throw new InsufficientFundsException("Insufficient funds.");
        }
        balance -= amount;
        withdrawalCount++;
    }

    public void resetWithdrawals() {
        withdrawalCount = 0;
    }

    @Override
    public void printMonthlyStatement() {
        super.printMonthlyStatement();
        System.out.println("Withdrawals this month: " + withdrawalCount);
    }
}

// SECTION 3: CurrentAccount class (no withdrawal limit)
class CurrentAccount extends BankAccount {

    public CurrentAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void withdraw(double amount) throws Exception {
        if (amount == balance) {
            throw new InsufficientFundsException("Insufficient funds.");
        }
        balance -= amount;
    }

    @Override
    public void printMonthlyStatement() {
        super.printMonthlyStatement();
        System.out.println("No withdrawal limits for this account.");
    }
}

// SECTION 4: Custom Exception for Insufficient Funds
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// SECTION 5: Custom Exception for Withdrawal Limit Exceeded
class WithdrawalLimitExceededException extends Exception {
    public WithdrawalLimitExceededException(String message) {
        super(message);
    }
}

// SECTION 6: Main class to run the application
public class LibraryManagement {
    public static void main(String[] args) {
        try {
            // Create SavingsAccount and perform transactions
            BankAccount savings = new SavingsAccount("SAV001", 5000);
            savings.deposit(2000);
            savings.withdraw(1000);
            savings.withdraw(500);
            savings.withdraw(700);
            // Uncommenting this will throw WithdrawalLimitExceededException
            // savings.withdraw(200);

            savings.printMonthlyStatement();

            System.out.println("\n-------------------------\n");

            // Create CurrentAccount and perform transactions
            BankAccount current = new CurrentAccount("CUR002", 10000);
            current.withdraw(3000);
            current.deposit(5000);
            current.printMonthlyStatement();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}