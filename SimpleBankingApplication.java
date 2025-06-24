// All classes are combined into a single file for simplicity and demonstration.
// In a real-world application, these would be in separate files within a package structure.

import java.util.UUID;


public class SimpleBankingApplication {

    // --- Custom Exception Classes (Bonus) ---
    // These are defined as static nested classes to be self-contained.

    /**
     * Custom exception for when a withdrawal is attempted with insufficient funds.
     */
    public static class InsufficientFundsException extends Exception {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }

    /**
     * Custom exception for when the monthly withdrawal limit for a savings account is exceeded.
     */
    public static class WithdrawalLimitExceededException extends Exception {
        public WithdrawalLimitExceededException(String message) {
            super(message);
        }
    }

    // --- Abstract Base Class: BankAccount ---

    /**
     * An abstract representation of a bank account.
     * This class provides the basic structure and common functionality for all account types.
     * It uses abstraction to define a contract for subclasses.
     */
    public abstract static class BankAccount {

        // Fields are private/protected to ensure encapsulation.
        private final String accountNumber;
        protected double balance; // protected to allow direct access by subclasses for simplicity

        /**
         * Constructor for BankAccount.
         * Initializes the account with a zero balance and a unique account number.
         */
        public BankAccount() {
            // Generate a unique account number for each new account.
            this.accountNumber = UUID.randomUUID().toString();
            this.balance = 0.0;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
                System.out.printf("Deposited: $%.2f. New balance: $%.2f%n", amount, balance);
            } else {
                System.out.println("Deposit amount must be positive.");
            }
        }

        public abstract void withdraw(double amount) throws InsufficientFundsException, WithdrawalLimitExceededException;

        public abstract void printMonthlyStatement();
    }

    // --- Concrete Subclasses ---

    /**
     * Represents a Savings Account, a specific type of BankAccount.
     * It has a limit on the number of withdrawals per month.
     */
    public static class SavingsAccount extends BankAccount {

        private int withdrawalCount;
        private static final int WITHDRAWAL_LIMIT = 3;

        public SavingsAccount() {
            super();
            this.withdrawalCount = 0;
        }

        @Override
        public void withdraw(double amount) throws WithdrawalLimitExceededException, InsufficientFundsException {
            if (withdrawalCount >= WITHDRAWAL_LIMIT) {
                throw new WithdrawalLimitExceededException(
                    "Withdrawal limit of " + WITHDRAWAL_LIMIT + " per month has been exceeded."
                );
            }

            if (amount > balance) {
                throw new InsufficientFundsException("Insufficient funds for withdrawal. Current balance: $" + balance);
            }

            balance -= amount;
            withdrawalCount++;
            System.out.printf("Successfully withdrew $%.2f from Savings Account. New balance: $%.2f. (%d/%d withdrawals this month)%n",
                    amount, balance, withdrawalCount, WITHDRAWAL_LIMIT);
        }

        @Override
        public void printMonthlyStatement() {
            System.out.println("\n--- Savings Account Monthly Statement ---");
            System.out.println("Account Number: " + getAccountNumber());
            System.out.printf("Ending Balance: $%.2f%n", getBalance());
            System.out.println("Withdrawals this month: " + withdrawalCount + "/" + WITHDRAWAL_LIMIT);
            System.out.println("---------------------------------------");

            this.withdrawalCount = 0;
            System.out.println("-> Monthly withdrawal count has been reset for the next period.");
        }
    }

    /**
     * Represents a Current Account, a specific type of BankAccount.
     * It has no limit on the number of withdrawals.
     */
    public static class CurrentAccount extends BankAccount {

        public CurrentAccount() {
            super();
        }

        @Override
        public void withdraw(double amount) throws InsufficientFundsException {
            if (amount > balance) {
                throw new InsufficientFundsException("Insufficient funds for withdrawal. Current balance: $" + balance);
            }

            balance -= amount;
            System.out.printf("Successfully withdrew $%.2f from Current Account. New balance: $%.2f%n", amount, balance);
        }

        @Override
        public void printMonthlyStatement() {
            System.out.println("\n--- Current Account Monthly Statement ---");
            System.out.println("Account Number: " + getAccountNumber());
            System.out.printf("Ending Balance: $%.2f%n", getBalance());
            System.out.println("---------------------------------------");
        }
    }

    // --- Main Application Method ---

    public static void main(String[] args) {
        BankAccount savings = new SavingsAccount();
        BankAccount current = new CurrentAccount();

        System.out.println("--- Initializing Accounts ---");
        System.out.println("Savings Account created with number: " + savings.getAccountNumber());
        System.out.println("Current Account created with number: " + current.getAccountNumber());
        System.out.println();

        System.out.println("--- Testing Savings Account ---");
        savings.deposit(1000.00);

        try {
            savings.withdraw(100.00);
            savings.withdraw(50.00);
            savings.withdraw(200.00);
        } catch (WithdrawalLimitExceededException e) {
            System.err.println("Withdrawal limit exceeded: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.err.println("Insufficient funds: " + e.getMessage());
        }

        try {
            System.out.println("\nAttempting to exceed withdrawal limit...");
            savings.withdraw(50.00);
        } catch (WithdrawalLimitExceededException e) {
            System.err.println("Caught expected exception: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.err.println("Caught unexpected exception: " + e.getMessage());
        }

        savings.printMonthlyStatement();

        try {
            System.out.println("\nAttempting to withdraw with insufficient funds...");
            savings.withdraw(2000.00);
        } catch (InsufficientFundsException e) {
            System.err.println("Caught expected exception: " + e.getMessage());
        } catch (WithdrawalLimitExceededException e) {
            System.err.println("Caught unexpected exception: " + e.getMessage());
        }

        System.out.println();

        System.out.println("--- Testing Current Account ---");
        current.deposit(2000.00);

        try {
            current.withdraw(500.00);
            current.withdraw(300.00);
            current.withdraw(200.00);
            current.withdraw(100.00);
        } catch (Exception e) {
            System.err.println("Error during withdrawal: " + e.getMessage());
        }

        try {
            System.out.println("\nAttempting to withdraw with insufficient funds...");
            current.withdraw(5000.00);
        } catch (InsufficientFundsException e) {
            System.err.println("Caught expected exception: " + e.getMessage());
        } catch (WithdrawalLimitExceededException e) {
            System.err.println("Caught unexpected exception: " + e.getMessage());
        }

        current.printMonthlyStatement();
    }
}
