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

    public User userLogin(String mobileNumber, int pin)throws IllegalArgumentException, IllegalStateException{
        isLockedOut();
        User user = userDatabase.get(mobileNumber);

        if(user == null) {
            userAttempts++;
            throw new IllegalArgumentException("Mobile number is incorrect");
        } else if(user.getPinNumber() != pin){
            userAttempts++;
            throw new IllegalArgumentException("PIN is incorrect");
        }else {
            userAttempts = 0;
            this.loggedInUser = user;
            return user;
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

    public double transfer(String receiverMobile, double amount)throws IllegalStateException, IllegalArgumentException{
        if(loggedInUser == null){
            throw new IllegalStateException("Session expired. Please re-login.");
        }

        if(!AmountValidation.isPositive(amount)){
            throw new IllegalArgumentException("Please enter amount greater than zero.");
        }

        if (loggedInUser.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance.");
        }

        User receiver = userDatabase.get(receiverMobile);

        if(receiver == null){
            throw new IllegalStateException("Target receiver mobile number not found.");
        }

        if(receiver.getMobileNumber().equals(loggedInUser.getMobileNumber())){
           throw new IllegalArgumentException("You can't transfer money to yourself.");
        }

        double calculatedBalance = loggedInUser.setBalance(loggedInUser.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        Transaction senderTx = new Transaction("Transfer out", amount, "Sent to " + receiver.getFullName());
        Transaction receiverTx = new Transaction("Transfer in", amount, "Received from " + loggedInUser.getFullName());

        loggedInUser.addTransaction(senderTx);
        receiver.addTransaction(receiverTx);

        return calculatedBalance;
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

    public boolean logout(){
        return loggedInUser == null;
    }

    public boolean isLoggedIn()throws IllegalStateException{
        if(loggedInUser == null) {
            throw new IllegalStateException("Session expired. Please re-login.");
        }
        return loggedInUser != null;
    }

    public boolean isLockedOut()throws IllegalStateException {
        if(userAttempts >= MAX_ATTEMPTS) {
            throw new IllegalStateException("This account is temporarily locked.");
        }
        return false;
    }
}
