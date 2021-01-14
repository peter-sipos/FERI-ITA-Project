package com.school.ita.ita3.product;

import javax.persistence.Entity;

@Entity
public class Book extends Product {
    private String author;
    private String publisher;
    private String genre;

    public Book() {
    }

    public Book(String author, String publisher, String genre) {
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
