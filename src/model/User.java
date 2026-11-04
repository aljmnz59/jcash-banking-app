package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private long id;
    private String mobileNumber;
    private int pinNumber;
    private String fullName;
    private double balance;
    private List<Transaction> transactionList;

    public User(long id, String mobile, int pin, String fullName, double balance){
       this.id = id;
       mobileNumber = mobile;
       pinNumber = pin;
       this.fullName = fullName;
       this.balance = balance;
       this.transactionList = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void addTransaction(Transaction transaction) {
        this.transactionList.add(transaction);
    }
    public String currentBalance() {
        return "Current balance: " + balance;
    }

    public double getBalance() {
        return balance;
    }

    public double setBalance(double balance) {
        return this.balance = balance;
    }
}

