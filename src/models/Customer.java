package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Customer implements Comparable<Customer>{
    private String userId;
    private String cnp;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
    private CreditHistory creditHistory;
    private List<Insurance> insurances;
    public Customer(String cnp, String firstName, String lastName, LocalDate dateOfBirth, String email, String phone){
        this.userId = java.util.UUID.randomUUID().toString();
        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = LocalDateTime.now();
        this.email = email;
        this.phone = phone;
        this.insurances = new ArrayList<>();
        this.creditHistory = new CreditHistory();
    }

    public String getUserId() {
        return userId;
    }

    public String getCnp() {
        return cnp;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getFullName(){
        return lastName + " " +firstName;
    }
    public String getEmail(){ return email;}
    public String getPhone(){ return phone;}
    public LocalDate getDateOfBirth(){ return dateOfBirth;}
    public LocalDateTime getCreatedAt(){ return createdAt;}
    public CreditHistory getCreditHistory(){return creditHistory;}
    public List<Insurance> getInsurances(){
        return insurances;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void addInsurance(Insurance insurance) {
        insurances.add(insurance);
    }
    @Override
    public int compareTo(Customer other){
        return Comparator.comparing(Customer::getLastName)
                .thenComparing(Customer::getFirstName)
                .compare(this, other);
    }
}
