package services;

import models.Customer;

import java.util.HashMap;
import java.util.TreeSet;

public class CustomerService {
    private TreeSet<Customer> customers;
    private HashMap<String, Customer> customersById;
    private static CustomerService instance = null;

    private CustomerService() {
        this.customers = new TreeSet<>();
        this.customersById = new HashMap<>();
    }

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        customersById.put(customer.getUserId(), customer);
    }
    public Customer getCustomerById(String userId) {
        return customersById.get(userId);
    }

    public Customer getCustomerByCnp(String cnp) {
        for (Customer customer : customers) {
            if (customer.getCnp().equals(cnp)) {
                return customer;
            }
        }
        return null;
    }

    public TreeSet<Customer> getAllCustomers() {
        return customers;
    }
    public void updateCustomerEmail(String userId, String email) {
        Customer customer = getCustomerById(userId);
        if (customer != null) {
            customer.setEmail(email);
        }
    }

    public void updateCustomerPhone(String userId, String phone) {
        Customer customer = getCustomerById(userId);
        if (customer != null) {
            customer.setPhone(phone);
        }
    }
    public void removeCustomer(String userId) {
        Customer customer = getCustomerById(userId);
        if (customer != null) {
            customers.remove(customer);
            customersById.remove(userId);
        }
    }

    public int getCustomerCount() {
        return customers.size();
    }
}
