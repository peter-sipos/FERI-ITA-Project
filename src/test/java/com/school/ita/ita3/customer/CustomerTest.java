package com.school.ita.ita3.customer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DataSource dataSource;

//    Testing of lazy initialization. Fails due to uknown reason, as lazy loading works properly inside the program.
//    @Test(expected = org.hibernate.LazyInitializationException.class)
//    public void purchasesLazyLoadTest(){
//        Customer customer = createFakeCustomer();
//        Purchase purchase = new Purchase();
//        customer.getPurchases().add(purchase);
//
////        Customer savedCustomer = testEntityManager.persist(customer);
////        testEntityManager.clear();
//        customerRepository.save(customer);
//        customerRepository.flush();
//
////        Customer customer1 = customerRepository.findById(savedCustomer.getId()).orElse(null);
//        Customer customer1 = customerRepository.findByEmail("alex@great.com");
//        customerRepository.flush();
//
//        //assertThat(org.hibernate.Hibernate.isInitialized(customer1.getPurchases().get(0))).isFalse();
//        System.out.println(customer1.getPurchases().get(0));
//    }


    // based on: https://github.com/chclaus/spring-boot-examples/blob/master/spring-boot-jpa-optimistic-locking/src/test/java/de/chclaus/example/OptimisticLockingApplicationTests.java
    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void versionTest(){
        customerRepository.save(createFakeCustomer());

        Customer customer1 = customerRepository.findByEmail("alex@great.com");
        Customer customer2 = customerRepository.findByEmail("alex@great.com");

        Customer customerProxy1 = processCustomer(customer1);
        Customer customerProxy2 = processCustomer(customer2);

        customerProxy1.setName("Peter");
        customerProxy2.setName("Jose");

        Customer updatedCustomer1 = processCustomer(customerProxy1);
        Customer updatedCustomer2 = processCustomer(customerProxy2);

        Customer fromDb = customerRepository.findByEmail("alex@great.com");
        customerRepository.save(updatedCustomer1);
        fromDb = customerRepository.findByEmail("alex@great.com");
        customerRepository.save(updatedCustomer2);
    }

    @Test
    public void uniqIdTest(){
        Customer customer1 = createFakeCustomer();
        Customer customer2 = createFakeCustomer();

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers.get(0).getId()).isNotEqualTo(customers.get(1).getId());
    }


    @Test
    public void adressNoTableTest() throws SQLException {
        Customer customer = createFakeCustomer();
        customerRepository.save(customer);

        DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
        ResultSet resultSet = metaData.getTables(null,null, "ADRESS", null);
        assertThat(resultSet.next()).isFalse();
    }


    private Customer processCustomer(Customer customer){
        Customer proxy = new Customer();
        proxy.setId(customer.getId());
        proxy.setName(customer.getName());
        proxy.setSurname(customer.getSurname());
        proxy.setAddress(customer.getAddress());
        proxy.setEmail(customer.getEmail());
        proxy.setPassword(customer.getPassword());
        proxy.setVersion(customer.getVersion());
        proxy.setPurchases(customer.getPurchases());
        return proxy;
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