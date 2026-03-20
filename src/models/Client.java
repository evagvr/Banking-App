package models;

public class Client {
    private String cnp;
    private String firstName;
    private String lastName;
    private int age;
    private double monthlyIncome;
    private int riskScore;
    public Client(String cnp, String firstName, String lastName, int age, double monthlyIncome){
        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.monthlyIncome = monthlyIncome;
        this.riskScore = 10;
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

    public int getAge() {
        return age;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    @Override
    public String toString() {
        return "Client{" +
                "cnp='" + cnp + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", monthlyIncome=" + monthlyIncome +
                ", riskScore=" + riskScore +
                '}';
    }
}
