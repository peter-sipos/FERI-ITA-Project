package com.school.ita.ita3.product;

import javax.persistence.Entity;

@Entity
public class Movie extends Product {
    private String director;
    private String producer;
    private String studio;
    private String genre;

    public Movie() {
    }

    public Movie(String director, String producer, String studio, String genre) {
        this.director = director;
        this.producer = producer;
        this.studio = studio;
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
