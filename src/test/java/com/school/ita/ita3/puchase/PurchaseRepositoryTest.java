package com.school.ita.ita3.puchase;

import com.school.ita.ita3.customer.Address;
import com.school.ita.ita3.customer.Customer;
import com.school.ita.ita3.customer.CustomerRepository;
import com.school.ita.ita3.purchase.Purchase;
import com.school.ita.ita3.purchase.PurchaseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PurchaseRepositoryTest {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void saveTest(){
        Purchase purchase = new Purchase();

        purchaseRepository.save(purchase);

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Example<Purchase> example = Example.of(purchase, matcher);
        Purchase purchaseFromDB = purchaseRepository.findOne(example).orElse(null);

        assertThat(purchase).isEqualTo(purchaseFromDB);
    }

    @Test
    public void findByPurchaseCustomerIdTest(){
        Customer customer = new Customer();
        customer.setAddress(new Address());
        customerRepository.save(customer);


        Customer customer2 = new Customer();
        customer2.setAddress(new Address());
        customerRepository.save(customer2);

        Purchase purchase1 = new Purchase(customer);
        Purchase purchase2 = new Purchase(customer);
        Purchase purchase3 = new Purchase(customer2);

        purchaseRepository.save(purchase1);
        purchaseRepository.save(purchase2);
        purchaseRepository.save(purchase3);

        List<Purchase> purchasesFromDb = purchaseRepository.findByPurchaseCustomerId(customer.getId());

        assertThat(purchasesFromDb.size()).isEqualTo(2);
        assertThat(purchasesFromDb).contains(purchase1, purchase2);
        assertThat(purchasesFromDb).doesNotContain(purchase3);
    }
}

