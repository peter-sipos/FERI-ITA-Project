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
public class MovieTest {

    @Autowired
    private ProductRepository productRepository;

    // based on: https://github.com/chclaus/spring-boot-examples/blob/master/spring-boot-jpa-optimistic-locking/src/test/java/de/chclaus/example/OptimisticLockingApplicationTests.java
    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void versionTest() {
        Movie product = new Movie();
        product.setName("Product");
        product.setDescription("descritption");
        product.setPrice(20);
        product.setQuantityInStock(39);
        product.setDirector("Autho");
        product.setGenre("fantasy");
        product.setStudio("studio");

        productRepository.save(product);

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Example<Movie> example = Example.of(product, matcher);
        Movie movie1 = (Movie) productRepository.findOne(example).orElse(null);

        Movie movieProxy1 = processMovie(movie1);

        movieProxy1.setName("GoT");
        movieProxy1.setDescription("The movie");

        Movie updatedMovie1 = processMovie(movieProxy1);

        Movie fromDb = (Movie) productRepository.findOne(example).orElse(null);
        fromDb.setName("Different name");
        productRepository.flush();
        productRepository.save(updatedMovie1);
    }

    @Test
    public void uniqIdTest(){
        Movie movie1 = new Movie();
        Movie movie2 = new Movie();

        productRepository.save(movie1);
        productRepository.save(movie2);

        List<Product> movies = productRepository.findAll();

        assertThat(movies.get(0).getId()).isNotEqualTo(movies.get(1).getId());
    }

    private Movie processMovie(Movie movie) {
        Movie proxy = new Movie();
        proxy.setId(movie.getId());
        proxy.setVersion(movie.getVersion());
        proxy.setName(movie.getName());
        proxy.setPrice(movie.getPrice());
        proxy.setDescription(movie.getDescription());
        proxy.setQuantityInStock(movie.getQuantityInStock());
        return proxy;
    }
}
