package Controller;

import Controller.Extra.*;
import Database.JDBCConnection;
import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import Models.Employee;
import Models.getData;
import javafx.stage.StageStyle;

import static Models.getData.username;

public class ManagerUIController implements Initializable {
    @FXML
    private Label managerNameLabel;
    @FXML
    private Button dashboardBtn;
    @FXML
    private Button manageCustomerbtn;
    @FXML
    private Button manageEmployeeBtn;
    @FXML
    private Button manageOrderBtn;

    @FXML
    private Button manageProductBtn;
    @FXML
    private Button manageVoucherBtn;

    @FXML
    private Button recieptProductBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, String> employeeAddressCol;

    @FXML
    private TableColumn<Employee, String> employeeBirthCol;

    @FXML
    private TableColumn<Employee, String> employeeEmailCol;

    @FXML
    private TableColumn<Employee, String> employeeGenderCol;

    @FXML
    private TableColumn<Employee, String> employeeIDCol;

    @FXML
    private TableColumn<Employee, String> employeeNameCol;

    @FXML
    private TableColumn<Employee, String> employeePhoneCol;

    @FXML
    private TableColumn<Employee, String> employeeUsernameCol;

    @FXML
    private AnchorPane dashBoard_form;
    @FXML
    private AnchorPane recieptProduct_Form;
    @FXML
    private AnchorPane main_form;
    @FXML
    private AnchorPane confirmForm;
    @FXML
    private AnchorPane manageProduct_form;
    @FXML
    private AnchorPane manageCustomer_form;
    @FXML
    private AnchorPane manageEmployee_form;
    @FXML
    private AnchorPane manageVoucher_form;
    @FXML
    private AnchorPane manageOrder_form;
    @FXML
    private GridPane productGrid;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField searchEmployeeFld;

    //----------------CUSTOMER--------------

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, String> customerBirthCol;

    @FXML
    private TableColumn<Customer, String> customerEmailCol;

    @FXML
    private TableColumn<Customer, String> customerGenderCol;

    @FXML
    private TableColumn<Customer, String> customerIDCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private TableColumn<Customer, String> customerUsernameCol;
    @FXML
    private TextField searchCustomerFld;

    //---------------VOUCHER-------------
    @FXML
    private TableView<Voucher> voucherTable;
    @FXML
    private TextField searchVoucherFld;

    @FXML
    private TableColumn<Voucher, Integer> voucherAmountCol;

    @FXML
    private TableColumn<Voucher, String> voucherIDCol;


    @FXML
    private TableColumn<Voucher, String> voucherTypeCol;

    @FXML
    private TableColumn<Voucher, Integer> voucherUsedCol;

    @FXML
    private TableColumn<Voucher, Integer> voucherValueCol;
    @FXML
    private TableColumn<Voucher, String> voucherStatusCol;

    //----------------PRODUCT-----------
    @FXML
    private TextField searchProductFld;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> productIDCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, String> productOrginCol;

    @FXML
    private TableColumn<Product, Integer> productPriceCol;

    @FXML
    private TableColumn<Product, Integer> productQuantityCol;

    @FXML
    private TableColumn<Product, Integer> productSoldCol;

    @FXML
    private TableColumn<Product, String> productStatusCol;
    @FXML
    private TableColumn<Product, String> productDescriptionCol;

    //--------------ORDER---------------
    @FXML
    private TableView<Orders> orderTable;
    @FXML
    private TextField searchOrderFld;
    @FXML
    private TableColumn<Orders, String> orderCustomerIDCol;

    @FXML
    private TableColumn<Orders, String> orderDateCol;

    @FXML
    private TableColumn<Orders, String> orderIDCol;

    @FXML
    private TableColumn<Orders, Integer> orderPriceCol;

    @FXML
    private TableColumn<Orders, String> orderStatusCol;

    @FXML
    private TableColumn<Orders, String> orderVoucherIDCol;

    //----------------RECEIPT PRODUCT---------
    @FXML
    private TableView<Product_receipt> receiptTable;

    @FXML
    private TableColumn<Product_receipt, String> idCol;

    @FXML
    private TableColumn<Product_receipt, String> dateCol;

    @FXML
    private TableColumn<Product_receipt, String> productidCol;

    @FXML
    private TableColumn<Product_receipt, Integer> quantityCol;

    @FXML
    private TableColumn<Product_receipt, Integer> priceCol;

    private double x = 0;
    private double y = 0;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Employee employee = null;
    Customer customer = null;
    Voucher voucher = null;
    Orders orders = null;
    Product product = null;
    Product_receipt receipt = null;
    ObservableList<Employee> EmployeeList = FXCollections.observableArrayList();
    ObservableList<Customer> CustomerList = FXCollections.observableArrayList();
    ObservableList<Voucher> VoucherList = FXCollections.observableArrayList();
    ObservableList<Orders> OrdersList = FXCollections.observableArrayList();
    ObservableList<Product> ProductList = FXCollections.observableArrayList();
    ObservableList<Product_receipt> ReceiptList = FXCollections.observableArrayList();
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
    public void cancel() {
        confirmForm.setVisible(false);
    }

    public void confirm() {
        if (manageEmployee_form.isVisible()){
            deleteEmployee();
        }
        if (manageOrder_form.isVisible()){
            cancelOrder();
        }
        confirmForm.setVisible(false);
    }

    private void loadDateCustomer() {

        connection = JDBCConnection.getJDBCConnection();
        refreshCustomer();

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerUsernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerBirthCol.setCellValueFactory(new PropertyValueFactory<>("birth"));
        customerGenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    public void refreshCustomer() {
        try {
            CustomerList.clear();

            query = "SELECT * FROM `customer`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CustomerList.add(new Customer(
                        resultSet.getString("CUSTOMER_ID"),
                        resultSet.getString("CUSTOMER_USERNAME"),
                        resultSet.getString("CUSTOMER_NAME"),
                        resultSet.getDate("CUSTOMER_BIRTH"),
                        resultSet.getString("CUSTOMER_GENDER"),
                        resultSet.getString("CUSTOMER_ADDRESS"),
                        resultSet.getString("CUSTOMER_PHONE"),
                        resultSet.getString("CUSTOMER_EMAIL")));
                customerTable.setItems(CustomerList);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void viewOrderCustomer(MouseEvent event) throws MalformedURLException {
        if (event.getClickCount() == 2) {
            customer = customerTable.getSelectionModel().getSelectedItem();

            URL url = new File("src/main/java/Views/Extra/ViewOrdersCustomer.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);

            try {
                loader.load();
            } catch (IOException ex) {
                Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
            }

            ViewOrdersCustomer viewOrdersCustomer = loader.getController();
            viewOrdersCustomer.setCustomerIDname(customer.getId(), customer.getName());

            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(parent));
            stage.show();
        }
    }

    public void searchCustomer() {
        Label emptyLabel = new Label("DATA NOT FOUND");
        emptyLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: red;");
        customerTable.setPlaceholder(emptyLabel);

        FilteredList<Customer> filteredCustomer = new FilteredList<>(CustomerList, b -> true);

        searchCustomerFld.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredCustomer.setPredicate(customer -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchCustomerKey = newValue.toLowerCase();

                if (customer.getName().toLowerCase().indexOf(searchCustomerKey) > -1) {
                    return true;
                } else if (customer.getAddress().toLowerCase().indexOf(searchCustomerKey) > -1) {
                    return true;
                } else if (customer.getEmail().toLowerCase().indexOf(searchCustomerKey) > -1) {
                    return true;
                } else if (customer.getUsername().toLowerCase().indexOf(searchCustomerKey) > -1) {
                    return true;
                } else if (customer.getPhone().toLowerCase().indexOf(searchCustomerKey) > -1) {
                    return true;
                } else if (customer.getGender().toLowerCase().indexOf(searchCustomerKey) > -1) {
                    return true;
                } else if (customer.getBirth().toString().toLowerCase().indexOf(searchCustomerKey) > -1) {
                    return true;
                } else if(customer.getId().toLowerCase().indexOf(searchCustomerKey) > -1){
                    return true;
                }else
                    return false;
            });
        });

        SortedList<Customer> sortedCustomer = new SortedList<>(filteredCustomer);

        sortedCustomer.comparatorProperty().bind(customerTable.comparatorProperty());

        customerTable.setItems(sortedCustomer);
    }



    private void loadDateOrders() {

        connection = JDBCConnection.getJDBCConnection();
        refreshOrder();

        orderIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        orderStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        orderVoucherIDCol.setCellValueFactory(new PropertyValueFactory<>("voucher_id"));
        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
    public void refreshOrder() {
        try {
            OrdersList.clear();

            query = "SELECT * FROM `orders`";
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
                orderTable.setItems(OrdersList);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateOrder(MouseEvent event) throws IOException {
        orders = orderTable.getSelectionModel().getSelectedItem();

        URL url = new File("src/main/java/Views/Extra/UpdateOrders.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);

        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

        UpdateOrdersController updateOrdersController = loader.getController();
        updateOrdersController.setOrderID(orders.getId());
        updateOrdersController.setStatus(orders.getStatus());

        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(parent));
        stage.show();
    }
    public void cancelOrderBtn(){
        confirmForm.setVisible(true);
    }

    public void cancelOrder() {
        try {
            orders = orderTable.getSelectionModel().getSelectedItem();
            query = "UPDATE `orders` SET ORDERS_STATUS = 'CANCELED' WHERE ORDERS_ID  = '" + orders.getId()+"'";
            connection = JDBCConnection.getJDBCConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            displaySuccessful("The order was successfully canceled!");
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void viewOrder(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2){
            updateOrder(event);
        }
    }

    public void searchOrder() {
        Label emptyLabel = new Label("DATA NOT FOUND");
        emptyLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: red;");
        orderTable.setPlaceholder(emptyLabel);

        FilteredList<Orders> filteredOrders = new FilteredList<>(OrdersList, b -> true);

        searchOrderFld.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredOrders.setPredicate(orders -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchOrdersKey = newValue.toLowerCase();

                if (orders.getCustomer_id().toLowerCase().indexOf(searchOrdersKey) > -1) {
                    return true;
                } else if (orders.getStatus().toLowerCase().indexOf(searchOrdersKey) > -1) {
                    return true;
                } else if (orders.getVoucher_id().toLowerCase().indexOf(searchOrdersKey) > -1) {
                    return true;
                } else if (orders.getDate().toString().toLowerCase().indexOf(searchOrdersKey) > -1) {
                    return true;
                } else if (orders.getPrice().toString().toLowerCase().indexOf(searchOrdersKey) > -1) {
                    return true;
                } else if(orders.getId().toLowerCase().indexOf(searchOrdersKey) > -1){
                    return true;
                }else
                    return false;
            });
        });

        SortedList<Orders> sortedOrders = new SortedList<>(filteredOrders);

        sortedOrders.comparatorProperty().bind(orderTable.comparatorProperty());

        orderTable.setItems(sortedOrders);
    }

    private void loadDateProduct(){
        connection = JDBCConnection.getJDBCConnection();
        refreshProduct();

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productSoldCol.setCellValueFactory(new PropertyValueFactory<>("sold"));
        productStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        productOrginCol.setCellValueFactory(new PropertyValueFactory<>("origin"));
    }

    public void refreshProduct() {
        try {
            ProductList.clear();

            query = "SELECT * FROM `product`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Image image = null;
                Blob file = resultSet.getBlob("PRODUCT_IMAGE");
                if(file != null) {
                    byte[] b = file.getBytes(1, (int) file.length());
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(b);
                    image = new Image(inputStream);
                }

                ProductList.add(new Product(
                        resultSet.getString("PRODUCT_ID"),
                        resultSet.getString("PRODUCT_NAME"),
                        resultSet.getString("PRODUCT_DESCRIPTION"),
                        image,
                        resultSet.getInt("PRODUCT_PRICE"),
                        resultSet.getInt("PRODUCT_QUANTITY"),
                        resultSet.getInt("PRODUCT_SOLD"),
                        resultSet.getString("PRODUCT_STATUS"),
                        resultSet.getString("PRODUCT_ORIGIN")));
                productTable.setItems(ProductList);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateProduct(MouseEvent event) throws MalformedURLException {
        product = productTable.getSelectionModel().getSelectedItem();
        URL url = new File("src/main/java/Views/Extra/AddProduct.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

        AddProductController addProductController = loader.getController();
        addProductController.setUpdate(true);
        addProductController.setTextField(product.getId(), product.getName(), product.getDescription(), product.getImage(),
                                            product.getPrice(), product.getQuantity(),product.getStatus(), product.getOrigin() );
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        refreshProduct();
    }

    public void insertProduct() {
        try {
            URL url = new File("src/main/java/Views/Extra/AddProduct.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });

            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
            refreshProduct();
        } catch (IOException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void viewProduct(MouseEvent event) throws MalformedURLException {
        if (event.getClickCount() == 2 ){
            updateProduct(event);
        }
    }

    public void searchProduct() {
        Label emptyLabel = new Label("DATA NOT FOUND");
        emptyLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: red;");
        productTable.setPlaceholder(emptyLabel);

        FilteredList<Product> filteredProduct = new FilteredList<>(ProductList, b -> true);

        searchProductFld.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredProduct.setPredicate(product -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchProductKey = newValue.toLowerCase();

                if (product.getName().toLowerCase().indexOf(searchProductKey) > -1) {
                    return true;
                } else if (product.getDescription().toLowerCase().indexOf(searchProductKey) > -1) {
                    return true;
                } else if (product.getPrice().toString().toLowerCase().indexOf(searchProductKey) > -1) {
                    return true;
                } else if (product.getQuantity().toString().toLowerCase().indexOf(searchProductKey) > -1) {
                    return true;
                } else if (product.getSold().toString().toLowerCase().indexOf(searchProductKey) > -1) {
                    return true;
                } else if (product.getStatus().toLowerCase().indexOf(searchProductKey) > -1) {
                    return true;
                } else if (product.getOrigin().toString().toLowerCase().indexOf(searchProductKey) > -1) {
                    return true;
                } else if(product.getId().toLowerCase().indexOf(searchProductKey) > -1){
                    return true;
                }else
                    return false;
            });
        });

        SortedList<Product> sortedProduct = new SortedList<>(filteredProduct);

        sortedProduct.comparatorProperty().bind(productTable.comparatorProperty());

        productTable.setItems(sortedProduct);
    }

    private void loadDateVoucher() {

        connection = JDBCConnection.getJDBCConnection();
        refreshVoucher();

        voucherIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        voucherTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        voucherValueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        voucherAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        voucherUsedCol.setCellValueFactory(new PropertyValueFactory<>("used"));
        voucherStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    public void refreshVoucher() {
        try {
            VoucherList.clear();

            query = "SELECT * FROM `voucher`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                VoucherList.add(new Voucher(
                        resultSet.getString("VOUCHER_ID"),
                        resultSet.getString("VOUCHER_TYPE"),
                        resultSet.getDouble("VOUCHER_VALUE"),
                        resultSet.getInt("VOUCHER_AMOUNT"),
                        resultSet.getInt("VOUCHER_USED"),
                        resultSet.getString("VOUCHER_STATUS")));
                voucherTable.setItems(VoucherList);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertVoucher() {
        try {
            URL url = new File("src/main/java/Views/Extra/AddVoucher.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });

            stage.initStyle(StageStyle.TRANSPARENT);
//            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(scene);
            stage.show();
            refreshEmployee();
        } catch (IOException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateVoucher(MouseEvent event) throws MalformedURLException {
        voucher = voucherTable.getSelectionModel().getSelectedItem();
        URL url = new File("src/main/java/Views/Extra/AddVoucher.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

        AddVoucherController addVoucherController = loader.getController();
        addVoucherController.setUpdate(true);
        addVoucherController.setTextField(voucher.getId(), voucher.getType(), voucher.getValue(), voucher.getAmount(),
                voucher.getStatus());
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        refreshVoucher();
    }


    public void viewVoucher(MouseEvent event) throws MalformedURLException {
        if (event.getClickCount() == 2) {
            updateVoucher(event);
        }
    }

    public void searchVoucher() {
        Label emptyLabel = new Label("DATA NOT FOUND");
        emptyLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: red;");
        voucherTable.setPlaceholder(emptyLabel);

        FilteredList<Voucher> filteredVoucher = new FilteredList<>(VoucherList, b -> true);

        searchVoucherFld.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredVoucher.setPredicate(voucher -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchVoucherKey = newValue.toLowerCase();

                if (voucher.getId().toLowerCase().indexOf(searchVoucherKey) > -1) {
                    return true;
                } else if (voucher.getStatus().toLowerCase().indexOf(searchVoucherKey) > -1) {
                    return true;
                } else if (voucher.getAmount().toString().toLowerCase().indexOf(searchVoucherKey) > -1) {
                    return true;
                } else if (voucher.getType().toLowerCase().indexOf(searchVoucherKey) > -1) {
                    return true;
                } else if (voucher.getUsed().toString().toLowerCase().indexOf(searchVoucherKey) > -1) {
                    return true;
                } else if (voucher.getValue().toString().toLowerCase().indexOf(searchVoucherKey) > -1) {
                    return true;
                }  else
                    return false;
            });
        });

        SortedList<Voucher> sortedVoucher = new SortedList<>(filteredVoucher);

        sortedVoucher.comparatorProperty().bind(voucherTable.comparatorProperty());

        voucherTable.setItems(sortedVoucher);
    }

    public void deleteEmployeeBtn() {
        confirmForm.setVisible(true);
    }

    public void deleteEmployee() {
        try {
            employee = employeeTable.getSelectionModel().getSelectedItem();
            query = "DELETE FROM `employee` WHERE EMPLOYEE_ID  = '" + employee.getId()+"'";
            connection = JDBCConnection.getJDBCConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            displaySuccessful("The data was successfully deleted!");
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertEmployee() {
        try {
            URL url = new File("src/main/java/Views/Extra/AddEmployee.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });

            stage.initStyle(StageStyle.TRANSPARENT);
//            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        refreshEmployee();
        employeeTable.refresh();
    }



    public void refreshEmployee() {
        try {
            EmployeeList.clear();

            query = "SELECT * FROM `employee`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                EmployeeList.add(new Employee(
                        resultSet.getString("EMPLOYEE_ID"),
                        resultSet.getString("EMPLOYEE_USERNAME"),
                        resultSet.getString("EMPLOYEE_NAME"),
                        resultSet.getDate("EMPLOYEE_BIRTH"),
                        resultSet.getString("EMPLOYEE_GENDER"),
                        resultSet.getString("EMPLOYEE_ADDRESS"),
                        resultSet.getString("EMPLOYEE_PHONE"),
                        resultSet.getString("EMPLOYEE_EMAIL")));
                employeeTable.setItems(EmployeeList);

            }


        } catch (SQLException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void updateEmployee(MouseEvent event) throws MalformedURLException, SQLException {
        employee = employeeTable.getSelectionModel().getSelectedItem();

        query = "SELECT ACC_PASSWORD FROM `acc` WHERE ACC_USERNAME = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, employee.getUsername());
        resultSet = preparedStatement.executeQuery();

        String password = null;
        if (resultSet.next()) {
            password = resultSet.getString("ACC_PASSWORD");
        }

        URL url = new File("src/main/java/Views/Extra/AddEmployee.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

        AddEmployeeController addEmployeeController = loader.getController();
        addEmployeeController.setUpdate(true);
        addEmployeeController.setTextField(employee.getId(), employee.getUsername(), password, employee.getName(),
                employee.getBirth().toLocalDate(), employee.getGender(), employee.getAddress(),
                employee.getPhone(), employee.getEmail());
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

    }

    private void loadDateEmployee() {

        connection = JDBCConnection.getJDBCConnection();
        refreshEmployee();

        employeeIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeUsernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        employeeNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeBirthCol.setCellValueFactory(new PropertyValueFactory<>("birth"));
        employeeGenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        employeeAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        employeePhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        employeeEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    private void searchEmployee() {
        Label emptyLabel = new Label("DATA NOT FOUND");
        emptyLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: red;");
        employeeTable.setPlaceholder(emptyLabel);

        FilteredList<Employee> filteredEmployee = new FilteredList<>(EmployeeList, b -> true);

        searchEmployeeFld.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredEmployee.setPredicate(employee -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchEmployeeKey = newValue.toLowerCase();

                if (employee.getName().toLowerCase().indexOf(searchEmployeeKey) > -1) {
                    return true;
                } else if (employee.getAddress().toLowerCase().indexOf(searchEmployeeKey) > -1) {
                    return true;
                } else if (employee.getEmail().toLowerCase().indexOf(searchEmployeeKey) > -1) {
                    return true;
                } else if (employee.getUsername().toLowerCase().indexOf(searchEmployeeKey) > -1) {
                    return true;
                } else if (employee.getPhone().toLowerCase().indexOf(searchEmployeeKey) > -1) {
                    return true;
                } else if (employee.getGender().toLowerCase().indexOf(searchEmployeeKey) > -1) {
                    return true;
                } else if (employee.getBirth().toString().toLowerCase().indexOf(searchEmployeeKey) > -1) {
                    return true;
                } else
                    return false;
            });
        });

        SortedList<Employee> sortedEmployee = new SortedList<>(filteredEmployee);

        sortedEmployee.comparatorProperty().bind(employeeTable.comparatorProperty());

        employeeTable.setItems(sortedEmployee);
    }

    @FXML
    private void viewEmployee(MouseEvent event) throws MalformedURLException, SQLException {
        if (event.getClickCount() == 2) {
            updateEmployee(event);
        }
    }


    private void loadDateReceipt() {

        connection = JDBCConnection.getJDBCConnection();
        refreshReceipt();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        productidCol.setCellValueFactory(new PropertyValueFactory<>("productid"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    public void refreshReceipt() {
        try {
            ReceiptList.clear();

            query = "SELECT * FROM `product_receipt`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ReceiptList.add(new Product_receipt(
                        resultSet.getString("RECEIPT_ID"),
                        resultSet.getDate("RECEIPT_DATE"),
                        resultSet.getString("PRODUCT_ID"),
                        resultSet.getInt("RECEIPT_QUANTITY"),
                        resultSet.getInt("RECEIPT_PRICE")));
                receiptTable.setItems(ReceiptList);

            }


        } catch (SQLException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiptProduct(){
            try {
                URL url = new File("src/main/java/Views/Extra/ReceiptProduct.fxml").toURI().toURL();
                Parent root = FXMLLoader.load(url);
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                });

                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) this.main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void logOut() throws IOException {
        logOutBtn.getScene().getWindow().hide();
        URL url = new File("src/main/java/Views/UnsignedUI.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        root.setOnMousePressed((MouseEvent event) ->{
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    void switchForm(ActionEvent event) {
        if (event.getSource() == dashboardBtn) {
            dashBoard_form.setVisible(true);
            manageEmployee_form.setVisible(false);
            manageCustomer_form.setVisible(false);
            manageOrder_form.setVisible(false);
            manageProduct_form.setVisible(false);
            manageVoucher_form.setVisible(false);
            recieptProduct_Form.setVisible(false);
            confirmForm.setVisible(false);

            dashboardBtn.setStyle("-fx-background-color: #BAD1C2");
            manageEmployeeBtn.setStyle("-fx-background-color: transparent");
            manageCustomerbtn.setStyle("-fx-background-color: transparent");
            manageOrderBtn.setStyle("-fx-background-color: transparent");
            manageProductBtn.setStyle("-fx-background-color: transparent");
            manageVoucherBtn.setStyle("-fx-background-color: transparent");
            recieptProductBtn.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == manageEmployeeBtn) {
            dashBoard_form.setVisible(false);
            manageEmployee_form.setVisible(true);
            manageCustomer_form.setVisible(false);
            manageOrder_form.setVisible(false);
            manageProduct_form.setVisible(false);
            manageVoucher_form.setVisible(false);
            recieptProduct_Form.setVisible(false);
            confirmForm.setVisible(false);

            dashboardBtn.setStyle("-fx-background-color: transparent");
            manageEmployeeBtn.setStyle("-fx-background-color: #BAD1C2");
            manageCustomerbtn.setStyle("-fx-background-color: transparent");
            manageOrderBtn.setStyle("-fx-background-color: transparent");
            manageProductBtn.setStyle("-fx-background-color: transparent");
            manageVoucherBtn.setStyle("-fx-background-color: transparent");
            recieptProductBtn.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == manageCustomerbtn) {
            dashBoard_form.setVisible(false);
            manageEmployee_form.setVisible(false);
            manageCustomer_form.setVisible(true);
            manageOrder_form.setVisible(false);
            manageProduct_form.setVisible(false);
            manageVoucher_form.setVisible(false);
            recieptProduct_Form.setVisible(false);
            confirmForm.setVisible(false);

            dashboardBtn.setStyle("-fx-background-color: transparent");
            manageEmployeeBtn.setStyle("-fx-background-color: transparent");
            manageCustomerbtn.setStyle("-fx-background-color: #BAD1C2");
            manageOrderBtn.setStyle("-fx-background-color: transparent");
            manageProductBtn.setStyle("-fx-background-color: transparent");
            manageVoucherBtn.setStyle("-fx-background-color: transparent");
            recieptProductBtn.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == manageOrderBtn) {
            dashBoard_form.setVisible(false);
            manageEmployee_form.setVisible(false);
            manageCustomer_form.setVisible(false);
            manageOrder_form.setVisible(true);
            manageProduct_form.setVisible(false);
            manageVoucher_form.setVisible(false);
            recieptProduct_Form.setVisible(false);
            confirmForm.setVisible(false);

            dashboardBtn.setStyle("-fx-background-color: transparent");
            manageEmployeeBtn.setStyle("-fx-background-color: transparent");
            manageCustomerbtn.setStyle("-fx-background-color: transparent");
            manageOrderBtn.setStyle("-fx-background-color: #BAD1C2");
            manageProductBtn.setStyle("-fx-background-color: transparent");
            manageVoucherBtn.setStyle("-fx-background-color: transparent");
            recieptProductBtn.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == manageProductBtn) {
            dashBoard_form.setVisible(false);
            manageEmployee_form.setVisible(false);
            manageCustomer_form.setVisible(false);
            manageOrder_form.setVisible(false);
            manageProduct_form.setVisible(true);
            manageVoucher_form.setVisible(false);
            recieptProduct_Form.setVisible(false);
            confirmForm.setVisible(false);

            dashboardBtn.setStyle("-fx-background-color: transparent");
            manageEmployeeBtn.setStyle("-fx-background-color: transparent");
            manageCustomerbtn.setStyle("-fx-background-color: transparent");
            manageOrderBtn.setStyle("-fx-background-color: transparent");
            manageProductBtn.setStyle("-fx-background-color: #BAD1C2");
            manageVoucherBtn.setStyle("-fx-background-color: transparent");
            recieptProductBtn.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == manageVoucherBtn) {
            dashBoard_form.setVisible(false);
            manageEmployee_form.setVisible(false);
            manageCustomer_form.setVisible(false);
            manageOrder_form.setVisible(false);
            manageProduct_form.setVisible(false);
            manageVoucher_form.setVisible(true);
            recieptProduct_Form.setVisible(false);
            confirmForm.setVisible(false);

            dashboardBtn.setStyle("-fx-background-color: transparent");
            manageEmployeeBtn.setStyle("-fx-background-color: transparent");
            manageCustomerbtn.setStyle("-fx-background-color: transparent");
            manageOrderBtn.setStyle("-fx-background-color: transparent");
            manageProductBtn.setStyle("-fx-background-color: transparent");
            manageVoucherBtn.setStyle("-fx-background-color: #BAD1C2");
            recieptProductBtn.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == recieptProductBtn) {
            dashBoard_form.setVisible(false);
            manageEmployee_form.setVisible(false);
            manageCustomer_form.setVisible(false);
            manageOrder_form.setVisible(false);
            manageProduct_form.setVisible(false);
            manageVoucher_form.setVisible(false);
            recieptProduct_Form.setVisible(true);
            confirmForm.setVisible(false);

            dashboardBtn.setStyle("-fx-background-color: transparent");
            manageEmployeeBtn.setStyle("-fx-background-color: transparent");
            manageCustomerbtn.setStyle("-fx-background-color: transparent");
            manageOrderBtn.setStyle("-fx-background-color: transparent");
            manageProductBtn.setStyle("-fx-background-color: transparent");
            manageVoucherBtn.setStyle("-fx-background-color: transparent");
            recieptProductBtn.setStyle("-fx-background-color: #BAD1C2");

        }
    }

    void startForm(){
        dashBoard_form.setVisible(true);
        manageEmployee_form.setVisible(false);
        manageCustomer_form.setVisible(false);
        manageOrder_form.setVisible(false);
        manageProduct_form.setVisible(false);
        manageVoucher_form.setVisible(false);
        recieptProduct_Form.setVisible(false);
        confirmForm.setVisible(false);

        dashboardBtn.setStyle("-fx-background-color: #BAD1C2");
        manageEmployeeBtn.setStyle("-fx-background-color: transparent");
        manageCustomerbtn.setStyle("-fx-background-color: transparent");
        manageOrderBtn.setStyle("-fx-background-color: transparent");
        manageProductBtn.setStyle("-fx-background-color: transparent");
        manageVoucherBtn.setStyle("-fx-background-color: transparent");
        recieptProductBtn.setStyle("-fx-background-color: transparent");
    }

    public void displayUsername() {
        String user = managerlogin.getUsername();
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        this.managerNameLabel.setText(user);
    }
    Manager managerlogin ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerlogin = getData.manager;
        displayUsername();
        startForm();

        loadDateEmployee();
        searchEmployee();

        loadDateCustomer();
        searchCustomer();

        loadDateVoucher();
        searchVoucher();

        loadDateOrders();
        searchOrder();

        loadDateProduct();
        searchProduct();

        loadDateReceipt();
    }
}
