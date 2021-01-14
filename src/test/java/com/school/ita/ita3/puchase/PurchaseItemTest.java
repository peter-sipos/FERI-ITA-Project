package com.school.ita.ita3.puchase;

import com.school.ita.ita3.product.Product;
import com.school.ita.ita3.purchase.PurchaseItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class PurchaseItemTest {

    @Test
    public void calculateItemPriceTest(){
        Product product = new Product();
        product.setPrice(20);

        PurchaseItem purchaseItem = new PurchaseItem(product, 3);
        purchaseItem.calculateItemPrice();

        assertThat(purchaseItem.getItemPrice()).isEqualTo(60);
    }
}
