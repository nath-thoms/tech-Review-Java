package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class Controller {

    @FXML
    private AnchorPane rootPane;

    /**
     * Loads the second pane - Dashboard.fxml
     * @param event
     * @throws IOException
     */

    @FXML
    private void loadSecond(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        rootPane.getChildren().setAll(pane);
    }

}
