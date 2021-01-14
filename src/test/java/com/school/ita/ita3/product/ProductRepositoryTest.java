package com.school.ita.ita3.product;

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
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void saveTest(){
        Product product = new Product();
        product.setName("Product");

        productRepository.save(product);

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Example<Product> example = Example.of(product, matcher);
        Product otherProduct = productRepository.findOne(example).orElse(null);

        assertThat(otherProduct).isEqualTo(product);
    }

    @Test
    public void findAllTest(){
        Product product1 = new Product();
        Product product2 = new Product();

        productRepository.save(product1);
        productRepository.save(product2);

        List<Product> products = productRepository.findAll();

        assertThat(products.size()).isEqualTo(2);
    }
}
