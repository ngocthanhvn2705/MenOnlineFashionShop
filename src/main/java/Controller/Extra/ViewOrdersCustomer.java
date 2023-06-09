package Controller.Extra;

import Controller.ManagerUIController;
import Database.JDBCConnection;
import Models.Order_Items;
import Models.Orders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewOrdersCustomer implements Initializable {

    @FXML
    private Label customerNameLabel;


    @FXML
    private Label customerIDLable;

    @FXML
    private TableColumn<Orders, String> dateCol;

    @FXML
    private TableColumn<Orders, String> orderIDCol;

    @FXML
    private TableView<Orders> ordersTable;

    @FXML
    private TableColumn<Orders, Integer> priceCol;

    @FXML
    private TableColumn<Orders, String> statusCol;

    @FXML
    private TableColumn<Orders, String> voucherIDCol;
    Orders orders = null;
    ObservableList<Orders> OrdersList = FXCollections.observableArrayList();
    String query = null;
    String customerid;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;


    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setCustomerIDname(String id, String name){
        customerIDLable.setText(id);
        customerNameLabel.setText(name);
        this.customerid=id;
        refreshOrder();
    }

    private void loadDateOrders() {

        connection = JDBCConnection.getJDBCConnection();
        refreshOrder();

        orderIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        voucherIDCol.setCellValueFactory(new PropertyValueFactory<>("voucher_id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
    public void refreshOrder() {
        try {
            OrdersList.clear();

            query = "SELECT * FROM `orders` WHERE ORDERS_CUSTOMER_ID = '" + customerid + "'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                OrdersList.add(new Orders(
                        resultSet.getString("ORDERS_ID"),
                        resultSet.getString("ORDERS_CUSTOMER_ID"),
                        resultSet.getString("ORDERS_STATUS"),
                        resultSet.getString("ORDERS_VOUCHER_ID"),
                        resultSet.getDate("ORDERS_DATE"),
                        resultSet.getInt("ORDERS_PRICE")));
                ordersTable.setItems(OrdersList);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewOrdersCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadDateOrders();
    }
}
