package co.edu.uptc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor

public class Product {
    private String id;
    private String description;
    private String unit;
    private double price;

}
