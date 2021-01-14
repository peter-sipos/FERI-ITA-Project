package com.school.ita.ita3.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ProductService.class})
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void saveTest(){
        Product product = new Product();
        productService.save(product);
        Mockito.verify(productRepository, Mockito.times(1)).save(product);
    }

    @Test
    public void findAllTest(){
        Product product1 = new Product();
        Product product2 = new Product();

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        Mockito.when(productRepository.findAll()).thenReturn(products);

        List<Product> productsFromDb = productService.findAll();

        assertThat(productsFromDb).isEqualTo(products);
    }
}
