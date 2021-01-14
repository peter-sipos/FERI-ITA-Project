package com.school.ita.ita3.customer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Before
    public void saveFakeCustomer(){
        customerRepository.save(createFakeCustomer());
    }

    @Test
    public void findByEmailTest() {
        Customer customer = createFakeCustomer();
        Customer fromDb = customerRepository.findByEmail(customer.getEmail());

        assertEquals(customer.getName(), fromDb.getName());
    }

    @Test
    public void deleteByIdTest(){
        Customer fromDb = customerRepository.findByEmail("alex@great.com");
        customerRepository.deleteById(fromDb.getId());
        fromDb = customerRepository.findByEmail("alex@great.com");
        assertNull(fromDb);
    }

    @Test
    public void saveTest(){
        Customer customer = new Customer();
        customer.setName("Jose");
        customer.setEmail("jose@jose.com");
        customer.setAddress(new Address());

        customerRepository.save(customer);

        Customer jose = customerRepository.findByEmail("jose@jose.com");

        assertEquals(jose, customer);
    }




    private Customer createFakeCustomer(){
        Customer customer = new Customer();
        customer.setName("Alex");
        customer.setSurname("Great");
        customer.setEmail("alex@great.com");
        customer.setPassword("1111");
        customer.setAddress(new Address("Street", 1, 2000, "Maribor", "Sloveia"));
        return customer;
    }
}