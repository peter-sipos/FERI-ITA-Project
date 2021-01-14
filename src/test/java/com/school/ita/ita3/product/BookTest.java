package com.school.ita.ita3.product;

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
public class BookTest {

    @Autowired
    private ProductRepository productRepository;

    // based on: https://github.com/chclaus/spring-boot-examples/blob/master/spring-boot-jpa-optimistic-locking/src/test/java/de/chclaus/example/OptimisticLockingApplicationTests.java
    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void versionTest() {
        Book product = new Book();
        product.setName("Product");
        product.setDescription("descritption");
        product.setPrice(20);
        product.setQuantityInStock(39);
        product.setAuthor("Autho");
        product.setGenre("fantasy");
        product.setPublisher("publisher");

        productRepository.save(product);

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Example<Book> example = Example.of(product, matcher);
        Book book1 = (Book) productRepository.findOne(example).orElse(null);

        Book bookProxy1 = processBook(book1);

        bookProxy1.setName("GoT");
        bookProxy1.setDescription("The book");

        Book updatedBook1 = processBook(bookProxy1);

        Book fromDb = (Book) productRepository.findOne(example).orElse(null);
        fromDb.setName("Different name");
        productRepository.flush();
        productRepository.save(updatedBook1);
    }



    @Test
    public void uniqIdTest(){
        Book book1 = new Book();
        Book book2 = new Book();

        productRepository.save(book1);
        productRepository.save(book2);

        List<Product> books = productRepository.findAll();

        assertThat(books.get(0).getId()).isNotEqualTo(books.get(1).getId());
    }

    private Book processBook(Book book){
        Book proxy = new Book();
        proxy.setId(book.getId());
        proxy.setVersion(book.getVersion());
        proxy.setName(book.getName());
        proxy.setPrice(book.getPrice());
        proxy.setDescription(book.getDescription());
        proxy.setQuantityInStock(book.getQuantityInStock());
        return proxy;
    }
}
