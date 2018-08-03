package sample.datamodel;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class Review {

    private String title;
    private String body;
    private int stars;
    private String author;

    public Review(String title, String body, int stars, String author) {
        this.title = title;
        this.body = body;
        this.stars = stars;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getStars() {
        return stars;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "\'" + title + "\'" + " - a review by " + author + ". \n" + body;

    }
}
