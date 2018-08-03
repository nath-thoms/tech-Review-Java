package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.datamodel.MainCategory;
import sample.datamodel.Product;
import sample.datamodel.Review;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Dashboard {

    private List<Product> productList;

    @FXML
    private ListView<Product> productListView;

    @FXML
    private Label productName;

    @FXML
    private Label productDate;

    @FXML
    private Label productPrice;

    @FXML
    private Label productDescription;

    @FXML
    private ListView<Review> reviewList;

    @FXML
    private Label productLikes;

    @FXML
    private ImageView productThumb;

    @FXML
    private AnchorPane productOverview;

    @FXML
    private AnchorPane mainDashboard;

    @FXML
    private AnchorPane descriptionPane;

    @FXML
    private AnchorPane reviewPane;


    /**
     * Initialize method runs when app initialises. Currently creating placeholder data and adding ths to productList
     * array list of products.
     */

    public void initialize() {
        System.out.println("initialize running");
        Product prod1 = new Product("Nintendo Switch", "274.99", "Nintendo Switch is a breakthrough home video game console. " +
                "It not only connects to a TV at home, but it also instantly transforms into an on- the-go handheld using its 6.2-inch screen.\n" +
                "\n" +
                "In addition to providing single-player and multiplayer thrills at home, the Nintendo Switch system also enables gamers to play the same title wherever, " +
                "whenever and with whomever they choose. " +
                "The mobility of a handheld is now added to the power of a home gaming system to enable unprecedented new video game play styles.",
                LocalDate.of(2017, Month.MARCH, 3));
        Product prod2 = new Product("Pokemon Go", "49.99", "Pokemon game for Nintendo Switch", LocalDate.of(2018, Month.NOVEMBER, 11));
        Product prod3 = new Product("FIFA 19", "54.99", "Football game for Playstation 4", LocalDate.of(2018, Month.SEPTEMBER, 4));

        productList = new ArrayList<Product>();
        productList.add(prod1);
        productList.add(prod2);
        productList.add(prod3);

        // adds the productList to the ListView for products.
        productListView.getItems().setAll(productList);

        // sets the ListView to only allow one item to be selected
        productListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // ****** REVIEW MODEL DATA ******
        prod1.addReview("Fantastic product", "Excellent product, good value for money. Would recommend.", 5, "Nathan Chadwick");


    }

    /**
     * Method handles clicks in the ListView of products. Sets the AnchorPane displaying the product overview to 'visible'.
     * Populates product nodes with the fields from the instance of each Product class.
     */

    @FXML
    public void handleClickListView() {
        productOverview.setVisible(true);
        descriptionPane.setVisible(true);
        reviewPane.setVisible(true);

        Image thumbNail = new Image("sample/assets/joypad.png");
        Product item = productListView.getSelectionModel().getSelectedItem();
        productName.setText(item.getName());
        productDate.setText("Release date: " + item.getRelease().toString());
        productPrice.setText("£" + String.valueOf(item.getPrice()));
        productThumb.setImage(thumbNail);

        productDescription.setText(item.getDescription());
        productDescription.setWrapText(true);
        reviewList.getItems().setAll(item.getNewReviews());
        //productLikes.setText("Likes: " + item.getLikes());
    }

    /**
     * method creates a new Dialog pain and initialises a window for the dialog pane.
     * new FXMLLoader to load correct FXML file.
     * adds an OK and CANCEL dialog button to the dialog pane.
     * Dialog pane set to showAndWait so that window stays open awaiting user input.
     * IF OK is pressed, an instance of AddProductController is initialised using .getController to allow access to
     * .processResults() method which takes the input from the dialog pane and creates a new Product class instance.
     * newProduct is added to ArrayList productList - who's contents are then re-set in the List View FXML container to
     * display newly added product to the list of products in the application.
     */

    @FXML
    public void showAddProductDialog() {
        Dialog <ButtonType> addProductDialog = new Dialog<>();
        addProductDialog.initOwner(mainDashboard.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addProduct.fxml"));
        try {
            addProductDialog.getDialogPane().setContent(fxmlLoader.load());


        } catch(IOException e) {
            System.out.println("Couldn't load dialog window.");
            e.printStackTrace();
            return;
        }


        addProductDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        addProductDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = addProductDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AddProductController controller = fxmlLoader.getController();
            Product newProduct = controller.processResults();
            System.out.println(newProduct.toString());
            productList.add(newProduct);
            productListView.getItems().setAll(productList);
            System.out.println("OK pressed");
        } else {
            System.out.println("Cancel pressed");
        }
    }

    public List<Product> getList() {
        return productList;
    }
}
