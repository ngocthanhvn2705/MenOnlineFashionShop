package Controller;

import Database.JDBCConnection;
import Models.Customer;
import Models.Product;
import Models.getData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
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
    private TextField searchProductFLd;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane main_form;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Product product = null;
    ObservableList<Product> ProductList = FXCollections.observableArrayList();
    ObservableList<Product> ProductListSearch = FXCollections.observableArrayList();
    private double x = 0;
    private double y = 0;


    Customer customerlogin;



    public void setCustomerNameLabel(String name){
        String[] words = name.split(" ");
        customerNameLabel.setText(words[words.length - 1]);
    }

    @FXML
    public void searchProduct(KeyEvent event) throws MalformedURLException {
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

        startForm();
        tshirtBtn.setStyle("-fx-background-color: transparent");
        jeansBtn.setStyle("-fx-background-color: transparent");
        jacketBtn.setStyle("-fx-background-color: transparent");
        sneakerBtn.setStyle("-fx-background-color: #fbece4; -fx-underline: true;");
        jewelleryBtn.setStyle("-fx-background-color: transparent");

    }
}
