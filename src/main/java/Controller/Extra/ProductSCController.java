package Controller.Extra;

import Controller.ThumbController;
import Database.JDBCConnection;
import Models.Product;
import Models.Shopping_Cart;
import Models.getData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private Label sizeLabel;
    Connection connection = null;
    PreparedStatement preparedStatement= null;
    String query = null;

    @FXML
    private Spinner<Integer> quantitySpinner;

    Product product;
    Shopping_Cart shoppingCart;

    public void setProductSC(Product product1, Shopping_Cart sc){
        this.shoppingCart = sc;
        this.product = product1;

        imageView.setImage(product1.getImage());
        imageView.setFitHeight(170);
        imageView.setFitWidth(170);
        imageView.setPreserveRatio(false);
        nameLabel.setText(product1.getName());
        priceLabel.setText((new DecimalFormat("#,###").format(product1.getPrice())));
        sizeLabel.setText(sc.getSizeproduct());

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, quantitySize());
        quantitySpinner.setValueFactory(valueFactory);
        quantitySpinner.getValueFactory().setValue(sc.getAmount());
        quantitySpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                connection = JDBCConnection.getJDBCConnection();
                query = "UPDATE SHOPPING_CART SET SC_AMOUNT= ? WHERE SC_CUSTOMER_ID = ? AND SC_PRODUCT_ID=? AND SC_SIZE_PRODUCT = ?";

                try {
                    preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setInt(1, newValue);
                    preparedStatement.setString(2, getData.customer.getId());
                    preparedStatement.setString(3, product1.getId());
                    preparedStatement.setString(4, sc.getSizeproduct());
                    preparedStatement.execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    @FXML
    public void viewProductSC(MouseEvent event) throws MalformedURLException {
        URL url = new File("src/main/java/Views/Extra/ShowinformationProduct.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ThumbController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ShowinformationProduct showinformationProduct = loader.getController();
        showinformationProduct.setProductOnSC(product);

        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    @FXML
    public void chooseProduct(){
        if (checkBox.isSelected()){
            getData.ProductChosenList.add(shoppingCart.getProductid());
            getData.SizeProductChosenList.add(shoppingCart.getSizeproduct());
        } else if (!checkBox.isSelected()) {
            for(int i = 0; i < getData.ProductChosenList.size(); i++){
                if (product.getId().equals(getData.ProductChosenList.get(i))){
                    getData.ProductChosenList.remove(i);
                    getData.SizeProductChosenList.remove(i);
                }
            }
        }
    }



    Integer quantitySize()  {
        Integer quantity = 0;
        connection = JDBCConnection.getJDBCConnection();

        query = "SELECT* FROM SIZE WHERE SIZE_PRODUCT_ID= ? AND SIZE_NAME =?";
        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,shoppingCart.getProductid());
            preparedStatement.setString(2,shoppingCart.getSizeproduct());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                quantity = resultSet.getInt("SIZE_QUANTITY");

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quantity;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
