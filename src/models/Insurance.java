package models;

import enums.InsuranceType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;

public class Insurance {
    private String insuranceId;
    private String customerId;
    private InsuranceType insuranceType;
    private double premiumAmount;
    private double coverageAmount;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private boolean isActive;
    private String policyNumber;

    private static Random random = new Random();

    public Insurance(String customerId, InsuranceType insuranceType, double premiumAmount,
                     double coverageAmount, LocalDate startDate, LocalDate expiryDate) {
        this.insuranceId = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.insuranceType = insuranceType;
        this.premiumAmount = premiumAmount;
        this.coverageAmount = coverageAmount;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.isActive = true;
        this.policyNumber = generatePolicyNumber();
    }

    private static String generatePolicyNumber() {
        StringBuilder digits = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            digits.append(random.nextInt(10));
        }
        return "POL-" + digits;
    }

    public String getInsuranceId() { return insuranceId; }
    public String getCustomerId() { return customerId; }
    public InsuranceType getInsuranceType() { return insuranceType; }
    public double getPremiumAmount() { return premiumAmount; }
    public double getCoverageAmount() { return coverageAmount; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public boolean isActive() { return isActive; }
    public String getPolicyNumber() { return policyNumber; }
    public void setActive(boolean active) { this.isActive = active; }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    public double getAnnualPremium() {
        return premiumAmount * 12;
    }

    public long getRemainingCoverageDays() {
        if (isExpired()) return 0;
        return ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
    }

    public double getTotalPremiumPaid() {
        long monthsPaid = ChronoUnit.MONTHS.between(startDate, LocalDate.now());
        return premiumAmount * monthsPaid;
    }
}

