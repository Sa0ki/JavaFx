package org.example.fxapplication.Models;

import org.example.fxapplication.Security.PasswordEncoder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String cin;
    private String email;
    private String password;

    public Client(Long id, String firstName, String lastName, LocalDate dob, String cin, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.cin = cin;
        this.email = email;
        this.password = password;
    }
    public Long getId(){ return id;}

    public void setId(Long id) { this.id = id; }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PasswordEncoder.encode(password);
    }

    @Override
    public String toString() {
        return id + ", " + firstName + ", " + lastName + ", " + dob + ", " + cin + ", " + email + ", " + password;
    }

    private static List<Client> clientsList = new ArrayList<>(Arrays.asList(
        new Client(null, "Saad", "Kinan", LocalDate.of(2000, 8, 2), "BK874509", "e.saad.kinan@gmail.com", "1234"),
        new Client(null, "Sarah", "Akiou", LocalDate.of(2005, 12, 24), "EH774450", "sarouta109@gmail.com", "5678"),
        new Client(null, "Ali", "Mohamed", LocalDate.of(1999, 5, 13), "AC456789", "ali12@gmail.com", "9876"),
        new Client(null, "Ghita", "Dari", LocalDate.of(2001, 3, 1), "PO897634", "ghitadari@gmail.com", "4564"),
        new Client(null, "Ahmed", "Wali", LocalDate.of(1997, 11, 30), "FR456789", "ahmed1997@gmail.com", "1234")
    ));

    public static List<Client> getClientsList(){
        return clientsList;
    }
    public static void setClientsList(List<Client> list){
        clientsList = list;
    }
}
