package co.edu.uptc.model;

public class Product {

    private int id;
    private String description;
    private String unit;
    private double price;

    public Product(int id, String description, String unit, double price) {
        this.id = id;
        this.description = description;
        this.unit = unit;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getUnit() {
        return unit;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{id=" + id + ", description=" + description
                + ", unit=" + unit + ", price=" + price + "}";
    }
}