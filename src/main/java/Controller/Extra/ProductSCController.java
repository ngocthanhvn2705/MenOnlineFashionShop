package Controller.Extra;

import Models.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class ProductSCController implements Initializable {

    @FXML
    private CheckBox checkBox;

    @FXML
    private ImageView imageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Spinner<Integer> quantitySpinner;

    Product product;

    public void setProductSC(Product product1){
        imageView.setImage(product1.getImage());
        imageView.setFitHeight(170);
        imageView.setFitWidth(170);
        imageView.setPreserveRatio(false);
        nameLabel.setText(product1.getName());
        priceLabel.setText((new DecimalFormat("#,###").format(product1.getPrice())));
        this.product = product1;
    }

    public void setQuantitySpinner(Integer max){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, max);
        quantitySpinner.setValueFactory(valueFactory);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
