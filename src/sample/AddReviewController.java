package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.datamodel.Product;
import sample.datamodel.Review;



public class AddReviewController {

    @FXML
    private TextField revTitle;

    @FXML
    private TextField revAuthor;

    @FXML
    private TextArea revBody;


    public Review processReview() {
        String reviewTitle = revTitle.getText().trim();
        String reviewAuthor = revAuthor.getText().trim();
        String reviewBody = revBody.getText().trim();

        if(reviewTitle != null && reviewAuthor != null && reviewBody != null) {
            System.out.println("Review processed");
            return new Review(reviewTitle, reviewBody, 0, reviewAuthor);

        } else {
            System.out.println("Error");
            return null;
        }
    }

}
