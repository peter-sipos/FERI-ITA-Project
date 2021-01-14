package com.school.ita.ita3.puchase;

import com.school.ita.ita3.purchase.Purchase;
import com.school.ita.ita3.purchase.PurchaseItem;
import com.school.ita.ita3.purchase.PurchaseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PurchaseTest {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Test
    public void uniqIdTest(){
        Purchase purchase1 = new Purchase();
        Purchase purchase2 = new Purchase();

        purchaseRepository.save(purchase1);
        purchaseRepository.save(purchase2);

        List<Purchase> purchases = purchaseRepository.findAll();

        assertThat(purchases.get(0).getId()).isNotEqualTo(purchases.get(1).getId());
    }

    // based on: https://github.com/chclaus/spring-boot-examples/blob/master/spring-boot-jpa-optimistic-locking/src/test/java/de/chclaus/example/OptimisticLockingApplicationTests.java
    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void versionTest() {
        Purchase purchase = new Purchase();
        purchase.setTotalPrice(100);

        purchaseRepository.save(purchase);

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Example<Purchase> example = Example.of(purchase, matcher);
        Purchase purchase1 = purchaseRepository.findOne(example).orElse(null);

        Purchase processProxy1 = processPurchase(purchase1);

        processProxy1.setTotalPrice(200);

        Purchase updatedBook1 = processPurchase(processProxy1);

        Purchase fromDb = purchaseRepository.findOne(example).orElse(null);
        fromDb.setTotalPrice(150);
        purchaseRepository.flush();
        purchaseRepository.save(updatedBook1);
    }

    @Test
    public void calculateTotalPriceTest(){
        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setItemPrice(20);
        PurchaseItem purchaseItem1 = new PurchaseItem();
        purchaseItem1.setItemPrice(30);

        Purchase purchase = new Purchase();
        purchase.getPurchasedItems().add(purchaseItem);
        purchase.getPurchasedItems().add(purchaseItem1);

        purchase.calculateTotalPrice();

        assertThat(purchase.getTotalPrice()).isEqualTo(50);
    }


    private Purchase processPurchase(Purchase purchase){
        Purchase proxy = new Purchase();
        proxy.setId(purchase.getId());
        proxy.setVersion(purchase.getVersion());
        proxy.setTotalPrice(purchase.getTotalPrice());
        proxy.setPurchasedItems(purchase.getPurchasedItems());
        return proxy;
    }

}
