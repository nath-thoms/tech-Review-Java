package sample;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import sample.datamodel.MainCategory;
import sample.datamodel.Product;
import sample.datamodel.Review;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
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

    @FXML
    private ContextMenu listContextMenu;

    @FXML
    private JFXButton addNewRev;

    @FXML
    private ImageView thumbImage;

    @FXML
    private ImageView thumbImage2;

    @FXML
    private AnchorPane likeBar;

    @FXML
    private ImageView fire;

    @FXML
    private ImageView cold;


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

        /**
         * Initialising new listContextMenu
         * Gets the selected listView item and calls deleteItem.
         */
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete product");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Product product = productListView.getSelectionModel().getSelectedItem();
                deleteItem(product);
            }
        });

        // adds deleteMenuItem to the listContextMenu
        listContextMenu.getItems().addAll(deleteMenuItem);

        /**
         * Callback to change cell factory. Sets the displayed data in a filled cell
         * Lambda function adds the listContextMenu listener to cells that are not empty.
         * Empty cells context menu and fill is set to null.
         */
        productListView.setCellFactory(new Callback<ListView<Product>, ListCell<Product>>() {
            @Override
            public ListCell<Product> call(ListView<Product> param) {
                ListCell<Product> cell = new ListCell<Product>() {

                    @Override
                    protected void updateItem(Product item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty) {
                            setText(null);
                        } else {
                            setText(item.getName() + "\n" + "£" + item.getPrice());
                        }
                    }
                };

                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if(isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        }
                );
                return cell;
            }
        });

        thumbImage.setMouseTransparent(true);
        thumbImage2.setMouseTransparent(true);
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
        addNewRev.setVisible(true);
        likeBar.setVisible(true);

        Image thumbNail = new Image("sample/assets/joypad.png");
        Product item = productListView.getSelectionModel().getSelectedItem();
        if(item == null) {
            System.out.println("Woops!");
        }
        productName.setText(item.getName());
        productDate.setText("Release date: " + item.getRelease().toString());
        productPrice.setText("£" + String.valueOf(item.getPrice()));
        productThumb.setImage(thumbNail);
        productLikes.setText(Integer.toString(item.getLikes()));
        if(item.getLikes() >= 10) {
            fire.setVisible(true);
        } else if(item.getLikes() < 0) {
            cold.setVisible(true);
        } else {
            fire.setVisible(false);
            cold.setVisible(false);
        }

        productDescription.setText(item.getDescription());
        productDescription.setWrapText(true);
        reviewList.getItems().setAll(item.getNewReviews());
    }

    /**
     * method creates a new Dialog pane and initialises a window for the dialog pane.
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

    /**
     * Method creates a new dialog pane and initialises a window.
     * Using FXML loader, loads the addReview.fxml markup file.
     * OK and Cancel dialog buttons are added and dialog is set to showAndWait to allow input.
     * If OK is pressed, an instance of AddReviewController is initialised which calls processReview
     * which takes the data from input fields and returns a new Review.
     * The current selected item is retrieved from the productListView.
     * The ArrayList of reviews on the current Product is retrieved.
     * The newly created Review is added to the ArrayList of reviews on that Product.
     * The ArrayList of reviews is then added to the ListView node and rendered in the app.
     */
    @FXML
    public void showAddReviewDialog() {
        Dialog <ButtonType> addReviewDialog = new Dialog<>();
        addReviewDialog.initOwner(mainDashboard.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addReview.fxml"));
        try {
            addReviewDialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("Couldn't load dialog window.");
            return;
        }
        addReviewDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        addReviewDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = addReviewDialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("OK Pressed");
            AddReviewController controller = fxmlLoader.getController();
            Review newReview = controller.processReview();
            System.out.println(newReview.toString());

            Product item = productListView.getSelectionModel().getSelectedItem();
            ArrayList itemReviews = item.getNewReviews();
            itemReviews.add(newReview);
            reviewList.getItems().setAll(itemReviews);

        } else {
            System.out.println("Cancel pressed");
        }
    }

    /**
     * Method to delete passed Product from the productListView.
     * Generates a new confirmation alert type. Setting info.
     * Alert is shown and waits for user input. If OK result
     * getsItems from ListView and removes passed product.
     * Sets the product view panes visible property to false.
     */
    public void deleteItem(Product product) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete product");
        alert.setHeaderText("Delete item: " + product.getName() + "?");
        alert.setContentText("Are you sure you wish to remove this product? Press OK to confirm or Cancel to go back");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && (result.get() == ButtonType.OK)) {
            productListView.getItems().remove(product);
            productOverview.setVisible(false);
            descriptionPane.setVisible(false);
            reviewPane.setVisible(false);
        }
    }

    /**
     * Method to sort the Products in the product list by price, low to high.
     * adds the items from the listview into a SortedList to access comparator functionality.
     * Overrides compare to method to sort the items by price.
     * Sets the contents of the ListView with the newly sorted list.
     */
    public void sortPriceLowToHigh() {
        SortedList<Product> productList = new SortedList<Product>(productListView.getItems(),
                new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        double value1 = Double.parseDouble(o1.getPrice());
                        double value2 = Double.parseDouble(o2.getPrice());
                        if(value1 < value2) {
                            return -1;
                        } else if(value1 > value2) {
                            return 1;
                        }
                        return 0;
                    }
                });
        productListView.getItems().setAll(productList);
    }

    /**
     * Method to sort the Products in the product list by price, high to low.
     * adds the items from the listview into a SortedList to access comparator functionality.
     * Overrides compare to method to sort the items by price.
     * Sets the contents of the ListView with the newly sorted list.
     */
    public void sortPriceHighToLow() {
        SortedList<Product> productList = new SortedList<Product>(productListView.getItems(),
                new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        double value1 = Double.parseDouble(o1.getPrice());
                        double value2 = Double.parseDouble(o2.getPrice());
                        if(value1 < value2) {
                            return 1;
                        } else if(value1 > value2) {
                            return -1;
                        }
                        return 0;
                    }
                });
        productListView.getItems().setAll(productList);
    }

    public void upVote() {
        Product item = productListView.getSelectionModel().getSelectedItem();
        Integer newLikes = item.getLikes() +1;
        item.setLikes(newLikes);
        productLikes.setText(Integer.toString(item.getLikes()));

        if(item.getLikes() >= 10) {
            fire.setVisible(true);
        }

        if(item.getLikes() >= 0) {
            cold.setVisible(false);
        }

    }

    public void downVote() {
        Product item = productListView.getSelectionModel().getSelectedItem();
        Integer newLikes = item.getLikes() -1;
        item.setLikes(newLikes);
        productLikes.setText(Integer.toString(item.getLikes()));

        if(item.getLikes() < 0) {
            cold.setVisible(true);
        }

        if(item.getLikes() < 10) {
            fire.setVisible(false);
        }

    }

}

