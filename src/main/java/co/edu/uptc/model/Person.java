package co.edu.uptc.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Person {
    private int id;
    private String name;
    private String lastName;
    private char gender;
    private Date date;
}
