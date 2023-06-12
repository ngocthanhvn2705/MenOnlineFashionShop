package Controller.Extra;

import Models.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThumbController {

    @FXML
    private ImageView imageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;
    Product product;

    public void setProduct(Product product1){
        imageView.setImage(product1.getImage());
        nameLabel.setText(product1.getName());
        priceLabel.setText((new DecimalFormat("#,###").format(product1.getPrice())));
        this.product = product1;
    }

    @FXML
    void showinformationProduct(MouseEvent event) throws MalformedURLException {
        URL url = new File("src/main/java/Views/Extra/ShowinformationProduct.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ThumbController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ShowinformationProduct showinformationProduct = loader.getController();
        showinformationProduct.setProduct(product);

        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

}
