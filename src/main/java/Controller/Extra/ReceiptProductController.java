package Controller.Extra;

import Database.JDBCConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReceiptProductController implements Initializable {

    @FXML
    private ComboBox<String> categoryCombobox;

    @FXML
    private AnchorPane confirmForm;

    @FXML
    private TextField priceFld;

    @FXML
    private ComboBox<String> productidCombobox;

    @FXML
    private TextField quantityFld;
    ObservableList<String> itemsCategory = FXCollections.observableArrayList("T-Shirt", "Jeans", "Jacket", "Sneaker", "Jewellery");
    ObservableList<String> itemsProductid = FXCollections.observableArrayList();

    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    String query;
    public void displayError(String message) throws IOException {
        URL url = new File("src/main/java/Views/Extra/Error.fxml").toURI().toURL();
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
    }

    public void displaySuccessful(String message) throws IOException {
        URL url = new File("src/main/java/Views/Extra/Successful.fxml").toURI().toURL();
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
    }
    public void cancel(){
        confirmForm.setVisible(false);
    }

    @FXML
    public void clear() {
        categoryCombobox.setValue(null);
        productidCombobox.setValue(null);
        quantityFld.setText(null);
        priceFld.setText(null);
    }

    public void setProductidCombobx(){
        categoryCombobox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            productidCombobox.getItems().clear();
            itemsProductid.clear();
            connection = JDBCConnection.getJDBCConnection();
            if (newValue != null) {
                switch (newValue) {
                    case "T-Shirt":
                        query = "SELECT * FROM product WHERE product_id LIKE 'T%%' ";
                        break;
                    case "Jeans":
                        query = "SELECT * FROM `product` WHERE product_id LIKE 'JNS%' ";
                        break;
                    case "Jacket":
                        query = "SELECT * FROM `product` WHERE product_id LIKE 'J%' AND product_id NOT LIKE 'JNS%' AND product_id NOT LIKE 'JW%'";
                        break;
                    case "Sneaker":
                        query = "SELECT * FROM product WHERE product_id LIKE 'S%%'";
                        break;
                    case "Jewellery":
                        query = "SELECT * FROM `product` WHERE product_id LIKE 'JW%' ";
                        break;
                }
                try {
                    preparedStatement = connection.prepareStatement(query);
                    resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        itemsProductid.add(resultSet.getString("PRODUCT_ID"));
                    }
                    productidCombobox.setItems(itemsProductid);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }



    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void confirm() throws SQLException, IOException {
        connection = JDBCConnection.getJDBCConnection();
        query = "INSERT INTO PRODUCT_RECEIPT (RECEIPT_DATE, PRODUCT_ID, RECEIPT_QUANTITY, RECEIPT_PRICE)" +
                "VALUES(CURDATE(), ?, ?, ?)";

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, productidCombobox.getValue());
        preparedStatement.setInt(2, Integer.valueOf(quantityFld.getText()));
        preparedStatement.setInt(3, Integer.valueOf(priceFld.getText()));
        preparedStatement.execute();

        clear();
        cancel();
        displaySuccessful("The data was successfully saved!");
    }

    @FXML
    void save(ActionEvent event) throws IOException {
        connection = JDBCConnection.getJDBCConnection();
        String category = categoryCombobox.getValue();
        String productid = productidCombobox.getValue();
        String quantity = quantityFld.getText();
        String price = priceFld.getText();

        if(category.isEmpty() || productid.isEmpty() || quantity.isEmpty() || price.isEmpty()) {
            displayError("Please Fill All Data");

        } else if (!isNumberInteger(price)) {
            displayError("Price only number integer");

        } else if (!isNumberInteger(quantity)) {
            displayError("Price only number integer");

        } else
            confirmForm.setVisible(true);
    }

    public boolean isNumberInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryCombobox.setItems(itemsCategory);
        setProductidCombobx();

    }
}
