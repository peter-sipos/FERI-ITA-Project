package com.school.ita.ita3.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void save(Customer customer){
        customerRepository.save(customer);
    }

    public void deleteById(long id){
        customerRepository.deleteById(id);
    }

    public Customer authorize(String email, String password){
        Customer customer = customerRepository.findByEmail(email);
        if (customer != null && customer.getPassword().equals(password)){
            return customer;
        } else {
            return null;
        }
    }

    public Customer findByEmail(String email){
        return customerRepository.findByEmail(email);
    }
}
