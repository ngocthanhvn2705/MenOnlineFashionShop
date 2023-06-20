package Controller;

import Controller.Extra.*;
import Database.JDBCConnection;
import Main.MyListener;
import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerUIController implements Initializable {

    @FXML
    private Button jacketBtn;

    @FXML
    private Button jeansBtn;

    @FXML
    private Button jewelleryBtn;
    @FXML
    private Button sneakerBtn;

    @FXML
    private Button tshirtBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private Button accountBtn;

    @FXML
    private AnchorPane account_form;

    @FXML
    private Button contactBtn;

    @FXML
    private AnchorPane contact_form;
    @FXML
    private Button homeBtn;

    @FXML
    private AnchorPane home_form;

    @FXML
    private AnchorPane order_form;
    @FXML
    private AnchorPane confirmAccountForm;
    @FXML
    private AnchorPane confirmOrderForm;
    @FXML
    private Button orderBtn;

    @FXML
    private Button scBtn;

    @FXML
    private AnchorPane sc_form;

    @FXML
    private GridPane productGrid;
    @FXML
    private Label customerNameLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private Label saveLabel;
    @FXML
    private TextField voucherFld;
    @FXML
    private TextField searchProductFLd;
    @FXML
    private PasswordField newpasswordFld;
    @FXML
    private PasswordField retypenewpasswordFld;
    @FXML
    private TextField addressFld;

    @FXML
    private TextField birthFld;

    @FXML
    private TextField emailFld;

    @FXML
    private TextField genderFld;

    @FXML
    private TextField nameFld;

    @FXML
    private TextField phoneFld;
    @FXML
    private VBox scItems;
    @FXML
    private TableView<Orders> orderTable;
    @FXML
    private TextField searchOrderFld;

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

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane main_form;
    String voucher = "NONE";
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Product product = null;
    Orders orders  =null;
    ObservableList<Product> ProductList = FXCollections.observableArrayList();
    ObservableList<Product> ProductListSearch = FXCollections.observableArrayList();
    ObservableList<Product> ProductListSC = FXCollections.observableArrayList();
    ObservableList<Orders> OrdersList = FXCollections.observableArrayList();

    ObservableList<Shopping_Cart> SCList = FXCollections.observableArrayList();
    private MyListener myListener;
    private double x = 0;
    private double y = 0;


    Customer customerlogin;

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
        confirmAccountForm.setVisible(false);
    }

    public void setCustomerNameLabel(String name){
        String[] words = name.split(" ");
        customerNameLabel.setText(words[words.length - 1]);
    }

    public void setItemsSC() throws SQLException {
        getData.ProductChosenList.clear();
        getData.SizeProductChosenList.clear();
        totalLabel.setText(null);
        saveLabel.setText(null);

        myListener = new MyListener() {
            @Override
            public void onClickListener() {
                setTotalSaveLabel();
            }
        };

        ProductListSC.clear();
        SCList.clear();
        scItems.getChildren().clear();
        connection = JDBCConnection.getJDBCConnection();
        query = "SELECT * FROM SHOPPING_CART WHERE SC_CUSTOMER_ID = ?";

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, customerlogin.getId());

        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String productid = resultSet.getString("SC_PRODUCT_ID");

            SCList.add(new Shopping_Cart(
                    productid,
                    resultSet.getInt("SC_AMOUNT"),
                    resultSet.getInt("SC_PRICE"),
                    resultSet.getString("SC_SIZE_PRODUCT")));

            Connection con = JDBCConnection.getJDBCConnection();

            String sql = "SELECT * FROM PRODUCT WHERE PRODUCT_ID = ?";
            PreparedStatement prepared = con.prepareStatement(sql);
            prepared.setString(1, productid);

            ResultSet rs = prepared.executeQuery();

            while (rs.next()) {
                Image image = null;
                Blob file = rs.getBlob("PRODUCT_IMAGE");
                if(file != null) {
                    byte[] b = file.getBytes(1, (int) file.length());
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(b);
                    image = new Image(inputStream, 170,170, false,true);
                }

                ProductListSC.add(new Product(
                        rs.getString("PRODUCT_ID"),
                        rs.getString("PRODUCT_NAME"),
                        rs.getString("PRODUCT_DESCRIPTION"),
                        image,
                        rs.getInt("PRODUCT_PRICE"),
                        rs.getInt("PRODUCT_QUANTITY"),
                        rs.getInt("PRODUCT_SOLD"),
                        rs.getString("PRODUCT_STATUS"),
                        rs.getString("PRODUCT_ORIGIN")));
            }

        }

        Node[] nodes = new Node[ProductListSC.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {
                URL url = new File("src/main/java/Views/Extra/ProductSC.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(url);
                try {
                    loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(CustomerUIController.class.getName()).log(Level.SEVERE, null, ex);
                }

                ProductSCController productSCController = loader.getController();
                productSCController.setProductSC(ProductListSC.get(i), SCList.get(i), myListener);


                nodes[i] = loader.getRoot();

                scItems.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteProductSC() throws SQLException {

        for (int i=0; i<getData.ProductChosenList.size(); i++){
            connection = JDBCConnection.getJDBCConnection();
            if (!getData.SizeProductChosenList.get(i).equals("FREE SIZE")) {
                query = "DELETE FROM SHOPPING_CART WHERE SC_CUSTOMER_ID = ? AND SC_PRODUCT_ID=? AND SC_SIZE_PRODUCT = ?";
            }else {
                query = "DELETE FROM SHOPPING_CART WHERE SC_CUSTOMER_ID = ? AND SC_PRODUCT_ID=? ";
            }

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customerlogin.getId());
            preparedStatement.setString(2, getData.ProductChosenList.get(i));
            if (!getData.SizeProductChosenList.get(i).equals("FREE SIZE")) {
                preparedStatement.setString(3, getData.SizeProductChosenList.get(i));
            }
            preparedStatement.execute();

        }
        getData.SizeProductChosenList.clear();
        getData.ProductChosenList.clear();
        setItemsSC();
    }

    public void setTextField(){
        nameFld.setText(customerlogin.getName());
        genderFld.setText(customerlogin.getGender());
        birthFld.setText(customerlogin.getBirth().toString());
        phoneFld.setText(customerlogin.getPhone());
        addressFld.setText(customerlogin.getAddress());
        emailFld.setText(customerlogin.getEmail());
    }

    public void editCustomer() throws IOException {
        connection = JDBCConnection.getJDBCConnection();
        String name = nameFld.getText();
        String gender = genderFld.getText();
        String birth = birthFld.getText();
        String address = addressFld.getText();
        String phone = phoneFld.getText();
        String email = emailFld.getText();

        if (name.isEmpty() || birth.isEmpty() || address.isEmpty() || email.isEmpty() ||
                phone.isEmpty()|| gender.isEmpty() ) {

            displayError("Please Fill All Data (May be not need New Password)");
        } else if (!isNumber(phone)) {
            displayError("Phone Number only number");

        }else  if(phone.length() >11 ) {
            displayError("Phone Number cannot be more than 11 characters");

        }else if ((newpasswordFld.getText().isEmpty() && !retypenewpasswordFld.getText().isEmpty())
                || (!newpasswordFld.getText().isEmpty() && retypenewpasswordFld.getText().isEmpty())) {
            displayError("Please Fill New Password/Re-type new password");

        }else if ((!newpasswordFld.getText().isEmpty() && !retypenewpasswordFld.getText().isEmpty())
                        && !newpasswordFld.getText().equals(retypenewpasswordFld.getText())){
                displayError("The passwords do not match");

        }else if(!gender.equals("MALE") || !gender.equals("FEMALE") ){
            displayError("This gender only MALE or FEMALE");
        } else{
            confirmAccountForm.setVisible(true);
        }

    }

    public void saveInformationCustomer() throws SQLException, IOException {
        connection = JDBCConnection.getJDBCConnection();
        query = "UPDATE `customer` SET "
                + "`CUSTOMER_NAME`=?,"
                + "`CUSTOMER_BIRTH`=?,"
                + "`CUSTOMER_GENDER`= ?,"
                + "`CUSTOMER_ADDRESS`= ?,"
                + "`CUSTOMER_PHONE`= ?,"
                + "`CUSTOMER_EMAIL`= ? WHERE CUSTOMER_ID = '"+customerlogin.getId()+"'";

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,nameFld.getText());
        preparedStatement.setString(2, birthFld.getText());
        preparedStatement.setString(3,genderFld.getText().toUpperCase());
        preparedStatement.setString(4,addressFld.getText());
        preparedStatement.setString(5,phoneFld.getText());
        preparedStatement.setString(6, emailFld.getText());
        preparedStatement.execute();

        if (!newpasswordFld.getText().isEmpty() && !retypenewpasswordFld.getText().isEmpty()){
            query = "UPDATE `acc` SET `ACC_PASSWORD` = ? WHERE ACC_USERNAME = '"+customerlogin.getUsername()+"'";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,newpasswordFld.getText());
            preparedStatement.execute();
        }
        displaySuccessful("Your account information has been successfully changed !");
        newpasswordFld.setText(null);
        retypenewpasswordFld.setText(null);
        confirmAccountForm.setVisible(false);
    }

    @FXML
    public void searchProduct(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            productGrid.setDisable(true);
            productGrid.setDisable(false);
            productGrid.getChildren().clear();
            connection = JDBCConnection.getJDBCConnection();
            try {
                ProductList.clear();
                if(tshirtBtn.getStyle().contains("-fx-background-color: #fbece4; -fx-underline: true;")) {
                    query = "SELECT * FROM product WHERE product_id LIKE 'T%%' ";

                } else if (jeansBtn.getStyle().contains("-fx-background-color: #fbece4; -fx-underline: true;")) {
                    query = "SELECT * FROM `product` WHERE product_id LIKE 'JNS%' ";

                } else if (jacketBtn.getStyle().contains("-fx-background-color: #fbece4; -fx-underline: true;")) {
                    query = "SELECT * FROM `product` WHERE product_id LIKE 'J%' AND product_id NOT LIKE 'JNS%' AND product_id NOT LIKE 'JW%'";

                } else if (sneakerBtn.getStyle().contains("-fx-background-color: #fbece4; -fx-underline: true;")) {
                    query = "SELECT * FROM product WHERE product_id LIKE 'S%%'";

                } else if (jewelleryBtn.getStyle().contains("-fx-background-color: #fbece4; -fx-underline: true;")) {
                    query = "SELECT * FROM `product` WHERE product_id LIKE 'JW%' ";

                }

                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Image image = null;
                    Blob file = resultSet.getBlob("PRODUCT_IMAGE");
                    if (file != null) {
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

                }
            } catch (SQLException ex) {
                Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
            ProductListSearch.clear();
            for (Product product2 : ProductList) {
                if (product2.getName().toLowerCase().contains(searchProductFLd.getText().toLowerCase())) {
                    ProductListSearch.add(product2);
                }
            }
            if(ProductListSearch.isEmpty()){
                displayError("NOT FOUND PRODUCT");
            }

            int columns = 1;
            int row = 1;
            for (int i = 0; i < ProductListSearch.size(); i++) {
                Product product1 = ProductListSearch.get(i);

                URL url = new File("src/main/java/Views/thumb.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(url);

                try {
                    VBox box = loader.load();
                    ThumbController thumbController = loader.getController();
                    thumbController.setProduct(product1);

                    if (columns == 4) {
                        columns = 1;
                        ++row;
                    }
                    productGrid.add(box, columns++, row);
                    GridPane.setMargin(box, new Insets(ProductListSearch.size()));
                } catch (IOException ex) {
                    Logger.getLogger(UnsignedUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }


    public void getProduct(String category) throws MalformedURLException {
        connection = JDBCConnection.getJDBCConnection();
        try {
            ProductList.clear();
            switch (category) {
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
                default:
                    query = "";
                    break;
            }

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

            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        int columns = 1;
        int row = 1;
        for (int i = 0; i < ProductList.size(); i++) {
            Product product1 = ProductList.get(i);

            URL url = new File("src/main/java/Views/thumb.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);

            try {
                VBox box = loader.load();
                ThumbController thumbController = loader.getController();
                thumbController.setProduct(product1);

                if(columns == 4){
                    columns = 1;
                    ++row;
                }
                productGrid.add(box,columns++,row);
                GridPane.setMargin(box, new Insets(ProductList.size()));
            } catch (IOException ex) {
                Logger.getLogger(CustomerUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    public void orderSC() throws SQLException, IOException {
        if (!getData.ProductChosenList.isEmpty()) {
            connection = JDBCConnection.getJDBCConnection();
            query = "INSERT INTO ORDERS ( ORDERS_CUSTOMER_ID, ORDERS_STATUS, ORDERS_VOUCHER_ID, ORDERS_DATE, ORDERS_PRICE) " +
                    "VALUES (?,?,?, CURDATE() , 0) ";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customerlogin.getId());
            preparedStatement.setString(2, "PREPARING");
            preparedStatement.setString(3, voucher);
            preparedStatement.execute();

            getData.setOrderid();


            connection = JDBCConnection.getJDBCConnection();
            query = "INSERT INTO ORDER_ITEMS (OI_ORDERS_ID, OI_PRODUCT_ID, OI_QUANTITY, OI_SIZE, OI_PRICE)" +
                    "VALUES (?,?, ?, ?, 0)";


            for (int i = 0; i < getData.ProductChosenList.size(); i++) {
                Integer quantity = 0;
                Connection con = JDBCConnection.getJDBCConnection();
                String sql = "SELECT * FROM SHOPPING_CART " +
                        "WHERE SC_CUSTOMER_ID= ? AND  SC_PRODUCT_ID = ? AND SC_SIZE_PRODUCT = ?";

                PreparedStatement pre = con.prepareStatement(sql);
                pre.setString(1, customerlogin.getId());
                pre.setString(2, getData.ProductChosenList.get(i));
                pre.setString(3, getData.SizeProductChosenList.get(i));
                resultSet = pre.executeQuery();

                if (resultSet.next()) {
                    quantity = resultSet.getInt("SC_AMOUNT");
                }

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, getData.Orderid);
                preparedStatement.setString(2, getData.ProductChosenList.get(i));
                preparedStatement.setInt(3, quantity);
                preparedStatement.setString(4, getData.SizeProductChosenList.get(i));
                preparedStatement.execute();

                Connection connect = JDBCConnection.getJDBCConnection();
                sql = "DELETE FROM SHOPPING_CART " +
                        "WHERE SC_CUSTOMER_ID= ? AND  SC_PRODUCT_ID = ? AND SC_SIZE_PRODUCT = ?";

                PreparedStatement prepared = con.prepareStatement(sql);
                prepared.setString(1, customerlogin.getId());
                prepared.setString(2, getData.ProductChosenList.get(i));
                prepared.setString(3, getData.SizeProductChosenList.get(i));
                prepared.execute();

            }

            displaySuccessful("Your cart has been successfully ordered. ");
            getData.ProductChosenList.clear();
            getData.ProductChosenList.clear();
            setItemsSC();
        }else{
            displayError("Please choose Product need to be ordered");
        }
    }

    @FXML
    public void applyVoucher(){
        myListener.onClickListener();
        voucher = voucherFld.getText();
    }

    public void setTotalSaveLabel(){
        Integer total = 0;
        Double save = 0.0;
        Integer amount = 0;
        String type= null;
        try {

            for (int i=0;i < getData.ProductChosenList.size(); i++){
                Connection connection = JDBCConnection.getJDBCConnection();
                String query= "SELECT PRODUCT_PRICE FROM PRODUCT WHERE PRODUCT_ID= ?";


                   PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, getData.ProductChosenList.get(i));
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()){
                    String query1;
                    Connection connection1 = JDBCConnection.getJDBCConnection();
                    if (!getData.SizeProductChosenList.get(i).equals("FREE SIZE")) {
                        query1 = "SELECT * FROM SHOPPING_CART " +
                                "WHERE SC_CUSTOMER_ID= ? and SC_PRODUCT_ID= ? and SC_SIZE_PRODUCT= ?";
                    }else{
                        query1 = "SELECT * FROM SHOPPING_CART " +
                                "WHERE SC_CUSTOMER_ID= ? and SC_PRODUCT_ID= ?";
                    }
                    PreparedStatement pre  = connection1.prepareStatement(query1);
                    pre.setString(1, customerlogin.getId());
                    pre.setString(2, getData.ProductChosenList.get(i));
                    if (!getData.SizeProductChosenList.get(i).equals("FREE SIZE")) {
                        pre.setString(3, getData.SizeProductChosenList.get(i));
                    }
                    ResultSet rs = pre.executeQuery();
                    if (rs.next()){
                        total+= rs.getInt("SC_PRICE");
                    }

//                   total+= resultSet.getInt("PRODUCT_PRICE");
                }

            }


            if (voucherFld.getText() != null) {
                Connection connection = JDBCConnection.getJDBCConnection();
                String query = "SELECT * FROM VOUCHER WHERE VOUCHER_ID = ? and VOUCHER_STATUS=?";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, voucherFld.getText());
                preparedStatement.setString(2, "INUSE");
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    save = resultSet.getDouble("VOUCHER_VALUE");
                    type = resultSet.getString("VOUCHER_TYPE");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (type!= null) {
            if (type.equals("SUBTRACT")) {
                totalLabel.setText((new DecimalFormat("#,###").format(total - save)));
                saveLabel.setText((new DecimalFormat("#,###").format(save)));
            } else if (type.equals("DIVIDE")) {
                totalLabel.setText((new DecimalFormat("#,###").format(total / save)));
                saveLabel.setText((new DecimalFormat("#,###").format(total - (total / save))));
            }
        }else{
            totalLabel.setText((new DecimalFormat("#,###").format(total - save)));
            saveLabel.setText((new DecimalFormat("#,###").format(save)));
        }

    }

    public void loadDateOrders() {

        connection = JDBCConnection.getJDBCConnection();
        refreshOrder();

        orderIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        orderVoucherIDCol.setCellValueFactory(new PropertyValueFactory<>("voucher_id"));
        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
    public void refreshOrder() {
        try {
            OrdersList.clear();

            query = "SELECT * FROM `orders` WHERE  ORDERS_CUSTOMER_ID = ?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,customerlogin.getId());
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

    public void viewOrderDoubleClick(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            viewOrder(event);
        }
    }

    public void viewOrder(MouseEvent event) throws IOException {
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
        updateOrdersController.setOrderID(orders.getId(), orders.getStatus());
        updateOrdersController.setStatusLabel(orders.getStatus());

        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(parent));
        stage.show();

    }

    public void cancelOrder() throws IOException {
        orders = orderTable.getSelectionModel().getSelectedItem();
        if (orders.getStatus().equals("PREPARING")) {
            try {

                query = "UPDATE `orders` SET ORDERS_STATUS = 'CANCELED' WHERE ORDERS_ID  = ? and ORDERS_CUSTOMER_ID= ?";

                connection = JDBCConnection.getJDBCConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, orders.getId());
                preparedStatement.setString(2, customerlogin.getId());
                preparedStatement.execute();
                displaySuccessful("The order was successfully canceled!");
            } catch (SQLException | IOException ex) {
                Logger.getLogger(CustomerUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadDateOrders();
        }else if(orders.getStatus().equals("DELIVERING")){
            displayError("The order was Delivering. Can't cancel;");
        }else if(orders.getStatus().equals("DONE")){
            displayError("The order was done. Can't Cancel");
        }else if(orders.getStatus().equals("CANCELED")){
            displayError("The order was cancelled!");
        }
    }




    @FXML
    public void switchCategory(ActionEvent event) throws MalformedURLException {
        if (event.getSource() == tshirtBtn) {
            productGrid.getChildren().clear(); // Xóa tất cả các thành phần con trong GridPane

            getProduct("T-Shirt");

            tshirtBtn.setStyle("-fx-background-color: #fbece4; -fx-underline: true;");
            jeansBtn.setStyle("-fx-background-color: transparent");
            jacketBtn.setStyle("-fx-background-color: transparent");
            sneakerBtn.setStyle("-fx-background-color: transparent");
            jewelleryBtn.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == jeansBtn) {
            productGrid.getChildren().clear();

            productGrid.setDisable(true);
            productGrid.setDisable(false);
            getProduct("Jeans");

            tshirtBtn.setStyle("-fx-background-color: transparent");
            jeansBtn.setStyle("-fx-background-color: #fbece4; -fx-underline: true;");
            jacketBtn.setStyle("-fx-background-color: transparent");
            sneakerBtn.setStyle("-fx-background-color: transparent");
            jewelleryBtn.setStyle("-fx-background-color: transparent");


        } else if (event.getSource() == jacketBtn) {
            productGrid.getChildren().clear();

            getProduct("Jacket");

            tshirtBtn.setStyle("-fx-background-color: transparent");
            jeansBtn.setStyle("-fx-background-color: transparent");
            jacketBtn.setStyle("-fx-background-color: #fbece4; -fx-underline: true;");
            sneakerBtn.setStyle("-fx-background-color: transparent");
            jewelleryBtn.setStyle("-fx-background-color: transparent");


        } else if (event.getSource() == sneakerBtn) {
            productGrid.getChildren().clear();

            productGrid.setDisable(true);
            productGrid.setDisable(false);
            getProduct("Sneaker");

            tshirtBtn.setStyle("-fx-background-color: transparent");
            jeansBtn.setStyle("-fx-background-color: transparent");
            jacketBtn.setStyle("-fx-background-color: transparent");
            sneakerBtn.setStyle("-fx-background-color: #fbece4; -fx-underline: true;");
            jewelleryBtn.setStyle("-fx-background-color: transparent");


        } else if (event.getSource() == jewelleryBtn) {
            productGrid.getChildren().clear();

            productGrid.setDisable(true);
            productGrid.setDisable(false);
            getProduct("Jewellery");

            tshirtBtn.setStyle("-fx-background-color: transparent");
            jeansBtn.setStyle("-fx-background-color: transparent");
            jacketBtn.setStyle("-fx-background-color: transparent");
            sneakerBtn.setStyle("-fx-background-color: transparent");
            jewelleryBtn.setStyle("-fx-background-color: #fbece4; -fx-underline: true;");

        }
    }
    @FXML
    public void switchForm(ActionEvent event){
        if(event.getSource() == homeBtn){
            startForm();

        } else if (event.getSource() == scBtn) {
            home_form.setVisible(false);
            sc_form.setVisible(true);
            account_form.setVisible(false);
            order_form.setVisible(false);
            contact_form.setVisible(false);

            homeBtn.setStyle("-fx-background-color:transparent");
            scBtn.setStyle("-fx-background-color: #BAD1C2;");
            accountBtn.setStyle("-fx-background-color:transparent");
            orderBtn.setStyle("-fx-background-color:transparent");
            contactBtn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == accountBtn) {
            home_form.setVisible(false);
            sc_form.setVisible(false);
            account_form.setVisible(true);
            order_form.setVisible(false);
            contact_form.setVisible(false);

            homeBtn.setStyle("-fx-background-color:transparent");
            scBtn.setStyle("-fx-background-color:transparent");
            accountBtn.setStyle("-fx-background-color: #BAD1C2;");
            orderBtn.setStyle("-fx-background-color:transparent");
            contactBtn.setStyle("-fx-background-color:transparent");

        }else if (event.getSource() == orderBtn) {
            home_form.setVisible(false);
            sc_form.setVisible(false);
            account_form.setVisible(false);
            order_form.setVisible(true);
            contact_form.setVisible(false);

            homeBtn.setStyle("-fx-background-color:transparent");
            scBtn.setStyle("-fx-background-color:transparent");
            accountBtn.setStyle("-fx-background-color:transparent");
            orderBtn.setStyle("-fx-background-color: #BAD1C2;");
            contactBtn.setStyle("-fx-background-color:transparent");

        }else if (event.getSource() == contactBtn) {
            home_form.setVisible(false);
            sc_form.setVisible(false);
            account_form.setVisible(false);
            order_form.setVisible(false);
            contact_form.setVisible(true);

            homeBtn.setStyle("-fx-background-color:transparent");
            scBtn.setStyle("-fx-background-color:transparent");
            accountBtn.setStyle("-fx-background-color:transparent");
            orderBtn.setStyle("-fx-background-color:transparent");
            contactBtn.setStyle("-fx-background-color: #BAD1C2;");
        }
    }
    private void startForm (){
        home_form.setVisible(true);
        sc_form.setVisible(false);
        account_form.setVisible(false);
        order_form.setVisible(false);
        contact_form.setVisible(false);
        confirmAccountForm.setVisible(false);

        homeBtn.setStyle("-fx-background-color: #BAD1C2");
        scBtn.setStyle("-fx-background-color:transparent");
        accountBtn.setStyle("-fx-background-color:transparent");
        orderBtn.setStyle("-fx-background-color:transparent");
        contactBtn.setStyle("-fx-background-color:transparent");
    }
    public void close(){
        System.exit(0);
    }

    public void minimize(){
        Stage stage = (Stage)this.main_form.getScene().getWindow();
        stage.setIconified(true);
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

    public boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerlogin = getData.customer;
        setCustomerNameLabel(customerlogin.getName());
        productGrid.getChildren().clear();

        productGrid.setDisable(true);
        productGrid.setDisable(false);
        try {
            getProduct("Sneaker");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        loadDateOrders();
        startForm();
        setTextField();

        try {
            setItemsSC();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tshirtBtn.setStyle("-fx-background-color: transparent");
        jeansBtn.setStyle("-fx-background-color: transparent");
        jacketBtn.setStyle("-fx-background-color: transparent");
        sneakerBtn.setStyle("-fx-background-color: #fbece4; -fx-underline: true;");
        jewelleryBtn.setStyle("-fx-background-color: transparent");



    }
}
