package com.school.ita.ita3.customer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CustomerService.class})
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void findByEmailTest(){
        Customer customer = createFakeCustomer();
        Mockito.when(customerRepository.findByEmail("alex@great.com")).thenReturn(createFakeCustomer());

        Customer fromDb = customerService.findByEmail(customer.getEmail());

        assertThat(customer.getEmail()).isEqualTo(fromDb.getEmail());
    }

    @Test
    public void deleteByIdTest(){
        Customer customer = createFakeCustomer();
        customerService.deleteById(customer.getId());
        Mockito.verify(customerRepository, Mockito.times(1)).deleteById(customer.getId());
    }

    @Test
    public void saveTest(){
        Customer customer = createFakeCustomer();
        customerService.save(customer);
        Mockito.verify(customerRepository, Mockito.times(1)).save(customer);
    }

    @Test
    public void authorizeCorrectMatchTest(){
        Mockito.when(customerRepository.findByEmail("alex@great.com")).thenReturn(createFakeCustomer());
        String email = "alex@great.com";
        String password = "1111";
        Customer customer = customerService.authorize(email, password);
        assertThat(customer).isNotNull();
    }

    @Test
    public void authorizeCorrectEmailWrongPasswordTest(){
        Mockito.when(customerRepository.findByEmail("alex@great.com")).thenReturn(createFakeCustomer());
        String email = "alex@great.com";
        String password = "1112";
        Customer customer = customerService.authorize(email, password);
        assertThat(customer).isNull();
    }

    @Test
    public void authorizeWrongEmailRightPasswordTest(){
        Mockito.when(customerRepository.findByEmail("alex@great.com")).thenReturn(createFakeCustomer());
        String email = "alex@notgreat.com";
        String password = "1111";
        Customer customer = customerService.authorize(email, password);
        assertThat(customer).isNull();
    }

    @Test
    public void authorizeWrongEmailWrongPasswordTest(){
        Mockito.when(customerRepository.findByEmail("alex@great.com")).thenReturn(createFakeCustomer());
        String email = "alex@notgreat.com";
        String password = "1112";
        Customer customer = customerService.authorize(email, password);
        assertThat(customer).isNull();
    }

    private Customer createFakeCustomer(){
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Alex");
        customer.setSurname("Great");
        customer.setEmail("alex@great.com");
        customer.setPassword("1111");
        customer.setAddress(new Address("Street", 1, 2000, "Maribor", "Sloveia"));
        return customer;
    }

}
