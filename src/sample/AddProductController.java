package sample;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.datamodel.Product;


import java.time.LocalDate;

public class AddProductController  {

    @FXML
    private TextField prodName;

    @FXML
    private TextField prodPrice;

    @FXML
    private DatePicker prodDate;

    @FXML
    private TextArea prodDesc;

    public Product processResults() {
        String productName = prodName.getText().trim();
        String productPrice = prodPrice.getText().trim();
        LocalDate releaseDate = prodDate.getValue();
        String productDescription = prodDesc.getText().trim();

        if((productName.length() > 3) && (productPrice != null) && (releaseDate != null) && (productDescription.split(" ").length > 3)) {

            return new Product(productName, productPrice, productDescription, releaseDate);

        } else {
            System.out.println("Handle this error later");
            return null;
        }
    }
}
