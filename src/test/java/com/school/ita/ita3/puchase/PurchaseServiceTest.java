package com.school.ita.ita3.puchase;

import com.school.ita.ita3.customer.Customer;
import com.school.ita.ita3.purchase.Purchase;
import com.school.ita.ita3.purchase.PurchaseRepository;
import com.school.ita.ita3.purchase.PurchaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PurchaseService.class})
public class PurchaseServiceTest {

    @Autowired
    private PurchaseService purchaseService;

    @MockBean
    private PurchaseRepository purchaseRepository;

    @Test
    public void saveTest(){
        Purchase purchase = new Purchase();
        purchaseService.save(purchase);
        Mockito.verify(purchaseRepository, Mockito.times(1)).save(purchase);
    }

    @Test
    public void findByPurchaseCustomerIdTest(){
        Customer customer = Mockito.mock(Customer.class);
        List<Purchase> purchasesFromDb = purchaseService.findByPurchaseCustomerId(customer.getId());
        Mockito.verify(purchaseRepository, Mockito.times(1)).findByPurchaseCustomerId(customer.getId());
    }
}
