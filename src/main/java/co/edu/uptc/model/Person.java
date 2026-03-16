package co.edu.uptc.model;

import java.time.LocalDate;

public class Person {

    private int id;
    private String name;
    private String lastName;
    private char gender;
    private LocalDate birthDate;

    public Person(int id, String name, String lastName, char gender, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public char getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Person{id=" + id + ", name=" + name + ", lastName=" + lastName
                + ", gender=" + gender + ", birthDate=" + birthDate + "}";
    }
}