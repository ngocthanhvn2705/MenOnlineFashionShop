package Controller.Extra;

import Controller.ManagerUIController;
import Database.JDBCConnection;
import Models.Customer;
import Models.Employee;
import Models.Order_Items;
import Models.Orders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateOrdersController implements Initializable {

    @FXML
    private AnchorPane confirmForm;

    @FXML
    private Label orderIDLabel;
    @FXML
    private Label statusLabel;

    @FXML
    private TableView<Order_Items> orderItemsTable;

    @FXML
    private TableColumn<Order_Items, Integer> priceCol;

    @FXML
    private TableColumn<Order_Items, String> productIDCol;

    @FXML
    private TableColumn<Order_Items, Integer> quantityCol;

    @FXML
    private TableColumn<Order_Items, String> sizeCol;

    @FXML
    private ComboBox<String> statusCombobox;

    @FXML
    private Button saveBtn;


    Order_Items order_items = null;
    ObservableList<Order_Items> Order_ItemsList = FXCollections.observableArrayList();
    String query = null;
    String orderid;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public void setOrderID(String id){
        orderIDLabel.setText(id);
        this.orderid=id;
        refreshOrder_Items();
    }
    public void setStatus(String status){
        statusCombobox.setValue(status);

    }

    public void setStatusLabel(String status){
        statusCombobox.setVisible(false);
        statusLabel.setText(status);
        statusLabel.setVisible(true);
        saveBtn.setVisible(false);
    }
    public void cancel() {
        confirmForm.setVisible(false);
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

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void confirm(ActionEvent event) throws SQLException, IOException {
        update();
        cancel();

        displaySuccessful("The order was successfully updated status!");
    }

    @FXML
    void save(ActionEvent event) {
        connection = JDBCConnection.getJDBCConnection();
        confirmForm.setVisible(true);
    }


    private void update() throws SQLException {

        query = "UPDATE orders SET ORDERS_STATUS= ? WHERE ORDERS_ID= ?";

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, statusCombobox.getValue());
        preparedStatement.setString(2, orderid);
        preparedStatement.execute();

    }
    private void loadDateOrder_Items() {

        connection = JDBCConnection.getJDBCConnection();
        refreshOrder_Items();

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
    }

    public void refreshOrder_Items() {
        try {
            Order_ItemsList.clear();

            query = "SELECT * FROM `order_items` WHERE OI_ORDERS_ID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, orderid);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order_ItemsList.add(new Order_Items(
                        resultSet.getString("OI_ORDERS_ID"),
                        resultSet.getString("OI_PRODUCT_ID"),
                        resultSet.getInt("OI_QUANTITY"),
                        resultSet.getInt("OI_PRICE"),
                        resultSet.getString("OI_SIZE")));
                orderItemsTable.setItems(Order_ItemsList);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        confirmForm.setVisible(false);
        loadDateOrder_Items();


        statusCombobox.setItems(FXCollections.observableArrayList(
                "PREPARING", "DELIVERING", "DONE", "CANCELED"));


    }
}
