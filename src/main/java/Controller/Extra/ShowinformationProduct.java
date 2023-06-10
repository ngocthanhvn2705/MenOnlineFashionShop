package Controller.Extra;

import Database.JDBCConnection;
import Models.Product;
import Models.getData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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

public class ShowinformationProduct {

    @FXML
    private AnchorPane confirmForm;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label nameLabel;

    @FXML
    private Label originLabel;

    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label availableLabel;

    @FXML
    private Label sizeLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private ComboBox sizecomboBox;

    @FXML
    private ImageView product_imageView;
    @FXML
    private Spinner<Integer> amountSpinner;
    Product product;
    @FXML
    private ComboBox <String> comboBox;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    int quantity;

    ObservableList<String> options;
    public void displayError(String message) {
        URL url = null;
        try {
            url = new File("src/main/java/Views/Extra/Error.fxml").toURI().toURL();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            loader.load();

            ErrorController errorController = loader.getController();
            errorController.setLabel(message);

            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void displaySuccessful(String message)  {
        URL url = null;
        try {
            url = new File("src/main/java/Views/Extra/Successful.fxml").toURI().toURL();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            loader.load();

            SuccessfulController successfulController = loader.getController();
            successfulController.setLabel(message);

            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void setProduct(Product product1){
        nameLabel.setText(product1.getName());
        priceLabel.setText((new DecimalFormat("#,###").format(product1.getPrice())));
        originLabel.setText(product1.getOrigin());
        product_imageView.setImage(product1.getImage());
        quantityLabel.setText(product1.getQuantity().toString());
        this.product = product1;
        if(product1.getId().substring(0,2).equals("JW")){
            sizecomboBox.setPromptText("None");
        }else if(product1.getId().substring(0,1).equals("S")){
            options = FXCollections.observableArrayList("40", "41", "42", "43", "44");
        }else{
            options = FXCollections.observableArrayList("S", "M", "L");
        }
        sizecomboBox.setItems(options);

        SpinnerValueFactory<Integer> valueSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, product.getQuantity(), 1);
        amountSpinner.setValueFactory(valueSpinner);
    }

    public void setProductOnSC(Product product1){
        nameLabel.setText(product1.getName());
        priceLabel.setText((new DecimalFormat("#,###").format(product1.getPrice())));
        originLabel.setText(product1.getOrigin());
        product_imageView.setImage(product1.getImage());
        quantityLabel.setText(product1.getQuantity().toString());
        this.product = product1;
        sizeLabel.setVisible(false);
        amountLabel.setVisible(false);
        sizecomboBox.setVisible(false);
        amountSpinner.setVisible(false);
    }

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }



    @FXML
    void cancel(ActionEvent event) {

    }
    @FXML
    void addProductScBtn() throws SQLException {
        if (getData.customer == null){
            displayError("Please Sign in for Add Shopping Cart and Order");
        }else if(product.getQuantity() <=0 ){
            displayError("This product is out of stock");
        } else if (sizecomboBox.getValue() == null && !product.getId().substring(0,2).equals("JW")) {
            displayError("Please choose size of product");
        } else if (!checkquantitySize(sizecomboBox.getValue().toString())) {
            displayError("The size of Product is out of stock");
        } else if (quantity < amountSpinner.getValue()) {
            displayError("This size of the product is only "+quantity);
        } else if (checkquantitySize(sizecomboBox.getValue().toString())){
            connection =JDBCConnection.getJDBCConnection();
            String query1 = "SELECT* FROM SHOPPING_CART WHERE SC_CUSTOMER_ID = ? AND SC_PRODUCT_ID = ? AND SC_SIZE_PRODUCT=?";

            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setString(1, getData.customer.getId());
            preparedStatement.setString(2, product.getId());
            preparedStatement.setString(3,sizecomboBox.getValue().toString());
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Connection con = JDBCConnection.getJDBCConnection();

                String query2 = "UPDATE SHOPPING_CART SET SC_AMOUNT = SC_AMOUNT+ ? " +
                        "WHERE SC_CUSTOMER_ID = ? AND SC_SIZE_PRODUCT= ? AND SC_PRODUCT_ID = ?";

                PreparedStatement pre = con.prepareStatement(query2);
                pre.setInt(1,amountSpinner.getValue());
                pre.setString(2, getData.customer.getId());
                pre.setString(3, sizecomboBox.getValue().toString());
                pre.setString(4, product.getId());
                pre.execute();
            }else {

                query = "INSERT INTO SHOPPING_CART(SC_CUSTOMER_ID, SC_PRODUCT_ID, SC_AMOUNT, SC_PRICE, SC_SIZE_PRODUCT) " +
                        "VALUES (?,?,?,?,?)";
                Connection connect = JDBCConnection.getJDBCConnection();
                PreparedStatement prepared = connect.prepareStatement(query);
                prepared.setString(1, getData.customer.getId());
                prepared.setString(2, product.getId());
                prepared.setInt(3, amountSpinner.getValue());
                prepared.setInt(4, 0);
                prepared.setString(5, sizecomboBox.getValue().toString());
                prepared.execute();
            }

            displaySuccessful("Product has been added successfully");
        }
    }

    boolean checkquantitySize(String size) throws SQLException {
        connection = JDBCConnection.getJDBCConnection();

        query = "SELECT* FROM SIZE WHERE SIZE_PRODUCT_ID= ? AND SIZE_NAME =?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,product.getId());
        preparedStatement.setString(2,size);

        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            quantity = resultSet.getInt("SIZE_QUANTITY");
            if(quantity > 0 ){
                return true;
            }
        }
        return false;
    }

    @FXML
    void confirm(ActionEvent event) {

    }

}
