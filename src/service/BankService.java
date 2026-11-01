package service;

import model.Transaction;
import model.User;
import util.AmountValidation;

import java.util.HashMap;
import java.util.Map;

public class BankService {
    private Map<String, User> userDatabase = new HashMap<>();
    private int userAttempts = 0;
    private final int MAX_ATTEMPTS = 3;
    private User loggedInUser = null;

    public BankService(){
        User user1 = new User(1, "09123456789", 1234, "John Doe", 1000.0);
        User user2 = new User(2, "09987654321", 4321, "Maria Clara", 1000.00);

        userDatabase.put(user1.getMobileNumber(), user1);
        userDatabase.put(user2.getMobileNumber(), user2);
    }

    public User userLogin(String mobileNumber, int pin){
        if(userAttempts >= MAX_ATTEMPTS) {
            return null;
        }

        User user = userDatabase.get(mobileNumber);

        if(user == null || user.getPinNumber() != pin) {
            userAttempts++;
            System.out.println("Mobile number or PIN is incorrect");
            return null;
        } else {
            userAttempts = 0;
            this.loggedInUser = user;
            return user;
        }
    }

    public void displayBalance(){
        if(loggedInUser != null){
            System.out.printf("Your current balance is: ₱%.2f", loggedInUser.getBalance());
        }
    }

    public double cashIn(double amount)throws IllegalStateException, IllegalArgumentException{
        if(loggedInUser == null){
            throw new IllegalStateException("Session expired. Please re-login.");
        }
        if(!AmountValidation.isPositive(amount)){
            throw new IllegalArgumentException("Please enter a valid amount greater than zero.");
        }
        double calculatedBalance = loggedInUser.getBalance() + amount;
        loggedInUser.setBalance(calculatedBalance);

        Transaction tx = new Transaction("Cash-in", amount, "Self cash-in");
        loggedInUser.addTransaction(tx);

        return calculatedBalance;
    }

    public void transfer(String receiverMobile, double amount){
        if(loggedInUser == null) return;

        if(!AmountValidation.isPositive(amount)){
            System.out.println("\nPlease enter valid amount");
            return;
        }

        if(loggedInUser.getBalance() < amount){
            System.out.println("\nInsufficient balance");
            return;
        }

        User receiver = userDatabase.get(receiverMobile);

        if(receiver == null){
            System.out.println("\nTarget receiver mobile number not found.");
            return;
        }

        if(receiver.getMobileNumber().equals(loggedInUser.getMobileNumber())){
            System.out.println("\nYou can't transfer money to yourself.");
            return;
        }

        loggedInUser.setBalance(loggedInUser.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        Transaction senderTx = new Transaction("Transfer out", amount, "Sent to " + receiver.getFullName());
        Transaction receiverTx = new Transaction("Transfer in", amount, "Received from " + loggedInUser.getFullName());

        loggedInUser.addTransaction(senderTx);
        receiver.addTransaction(receiverTx);

        System.out.printf("\nSuccessfully transfered ₱%.2f to %s.\n", amount, receiver.getFullName());
        displayBalance();
    }

    public void showTransaction(){
        if(loggedInUser == null) return;
        System.out.println("\n--- Transaction History ---");
        if(loggedInUser.getTransactionList().isEmpty()){
            System.out.println("No transactions found.");
        } else {
            for(Transaction tx : loggedInUser.getTransactionList()){
                System.out.println(tx);
            }
        }
    }

    public void logout(){
        System.out.println("\nLogging out. Goodbye " + (loggedInUser != null ? loggedInUser.getFullName() : "") + "!");
    }

    public boolean isLoggedIn(){
        return loggedInUser != null;
    }

    public boolean isLockedOut() {
        return userAttempts >= MAX_ATTEMPTS;
    }
}
