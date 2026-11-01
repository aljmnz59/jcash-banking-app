package model;

import java.time.*;

public class Transaction {
    private String type;
    private double amount;
    private String details;
    private LocalDateTime dateTime;

    public Transaction(String type, double amount, String details) {
        this.type = type;
        this.amount = amount;
        this.details = details;
        this.dateTime = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDetails() {
        return details;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: ₱%.2f (%s)", dateTime, type, amount, details);
    }
}
