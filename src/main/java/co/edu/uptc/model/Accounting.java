package co.edu.uptc.model;

import java.time.LocalDateTime;

public class Accounting {

    public enum MovementType {
        INCOME,
        EXPENSE
    }

    private int id;
    private String description;
    private MovementType type;
    private double amount;
    private LocalDateTime dateTime;

    public Accounting(int id, String description, MovementType type,
            double amount, LocalDateTime dateTime) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public MovementType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Accounting{id=" + id + ", description=" + description
                + ", type=" + type + ", amount=" + amount
                + ", dateTime=" + dateTime + "}";
    }
}