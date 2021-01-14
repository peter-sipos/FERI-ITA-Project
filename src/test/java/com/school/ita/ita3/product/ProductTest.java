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
public class ProductTest {

    @Autowired
    private ProductRepository productRepository;


    @Test
    public void returnBookWhenQueriingProductTest(){
        Book book = new Book();
        book.setName("GOT");

        productRepository.save(book);

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Example<Product> example = Example.of(book, matcher);
        Product product = productRepository.findOne(example).orElse(null);

        assertThat(book).isEqualTo(product);
    }

    @Test
    public void returnMovieWhenQueriingProductTest(){
        Movie movie = new Movie();
        movie.setName("Avatar");

        productRepository.save(movie);

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Example<Product> example = Example.of(movie, matcher);
        Product product = productRepository.findOne(example).orElse(null);

        assertThat(movie).isEqualTo(product);
    }

    @Test
    public void returnSubclassesWhenQueriingProductsTest(){
        Book book = new Book();
        Movie movie = new Movie();
        productRepository.save(book);
        productRepository.save(movie);
        List<Product> products = productRepository.findAll();
        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    public void bookInheritanceTest(){
        Product product = new Book();
        assertThat(product.getClass()).isEqualTo(Book.class);
    }

    @Test
    public void movieInheritanceTest(){
        Product product = new Movie();
        assertThat(product.getClass()).isEqualTo(Movie.class);
    }

    @Test
    public void updateStockQuantityTest(){
        Product product = new Product();
        product.setQuantityInStock(10);
        product.updateStockQuantity(3);

        assertThat(product.getQuantityInStock()).isEqualTo(7);
    }


}
