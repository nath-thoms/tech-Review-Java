package sample.datamodel;

import java.time.LocalDate;
import java.util.ArrayList;

public class Product {

    private String name;
    private String price;
    private String description;
    private LocalDate release;
    private ArrayList newReviews;
    private int likes;

    public Product(String name, String price, String description, LocalDate release) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.release = release;
        this.newReviews = new ArrayList();
        this.likes = 0;
    }

    public boolean addReview(String title, String body, int stars, String author) {

        System.out.println("new review added");
        return this.newReviews.add(new Review(title, body, stars, author));

    }

    /**
     * Getters and setters
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getRelease() {
        return release;
    }

    public void setRelease(LocalDate release) {
        this.release = release;
    }

    public ArrayList getNewReviews() {
        return newReviews;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return name;
    }
}

