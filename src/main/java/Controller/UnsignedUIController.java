package Controller;
import Controller.Extra.ErrorController;
import Controller.Extra.SuccessfulController;
import Database.JDBCConnection;
import Models.Customer;
import Models.Employee;
import Models.Manager;
import Models.getData;
import Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnsignedUIController implements Initializable {
    @FXML
    private Button loginBtn;
    @FXML
    private AnchorPane main_form;
    @FXML
    private AnchorPane login_form;
    @FXML
    private AnchorPane signup_form;
    @FXML
    private AnchorPane shopping_page;
    @FXML
    private AnchorPane sendOTP_from;
    @FXML
    private AnchorPane newpassword_form;
    @FXML
    private AnchorPane contact_form;
    @FXML
    private AnchorPane successfulDiaglog;
    @FXML
    private Button jacketBtn;

    @FXML
    private Button jeansBtn;

    @FXML
    private Button jewelleryBtn;
    @FXML
    private Button sneakerBtn;

    @FXML
    private Button signInBtn;
    @FXML
    private Button tshirtBtn;
    @FXML
    private Button homeBtn;
    @FXML
    private Button contactBtn;
    @FXML
    private TextField usernameFld;
    @FXML
    private PasswordField passwordFld;

    @FXML
    private PasswordField passwordSignupFld;
    @FXML
    private TextField addressFld;

    @FXML
    private DatePicker birthFld;

    @FXML
    private TextField emailFld;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton genderFemale;

    @FXML
    private RadioButton genderMale;

    @FXML
    private TextField nameFld;

    @FXML
    private TextField phoneFld;

    @FXML
    private TextField usernameSignupFld;

    @FXML
    private TextField searchProductFLd;

    @FXML
    private GridPane productGrid;

    @FXML
    private ScrollPane scrollPane;
    String userNamelogin;
    String authority;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Product product = null;
    ObservableList<Product> ProductList = FXCollections.observableArrayList();
    ObservableList<Product> ProductListSearch = FXCollections.observableArrayList();

    private double x = 0;
    private double y = 0;
    private String messageReturn;
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
    @FXML
    public void signIn2 (){
        signInAuthority(authority);
    }

    @FXML
    public void signIn2Enter (KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) {
            signInAuthority(authority);
        }
    }
    @FXML
    public void signIn1() throws SQLException, IOException {
        connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT * FROM acc WHERE ACC_USERNAME = ? and ACC_PASSWORD = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, usernameFld.getText());
        preparedStatement.setString(2, passwordFld.getText());
        resultSet = preparedStatement.executeQuery();

        if (!usernameFld.getText().isEmpty() && !passwordFld.getText().isEmpty()){
            if(resultSet.next()){
                    authority = resultSet.getString("ACC_AUTHORITY");
                    getData.username = usernameFld.getText();
                    getData.authority = authority;

                    successfulDiaglog.setVisible(true);
            }else{
                displayError("Wrong Username/Password");
            }
        } else {
            displayError("Please fill all blank fields");
        }
    }


    public void signInAuthority(String authority) {
        try {
            switch(authority){
                case "Manager":
                    getData.setManager();
                    break;
                case "Employee":
                    getData.setEmployee();
                    break;
                case "Customer":
                    getData.setCustomer();
            }

            signInBtn.getScene().getWindow().hide();
            URL url = new File("src/main/java/Views/"+authority+"UI.fxml").toURI().toURL();
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
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    void signUpCustomer(ActionEvent event) throws SQLException, IOException {

        connection = JDBCConnection.getJDBCConnection();
        String name = nameFld.getText();
        String username = usernameSignupFld.getText();
        String gender = getgender();
        String birth = String.valueOf(birthFld.getValue());
        String address = addressFld.getText();
        String phone = phoneFld.getText();
        String email = emailFld.getText();
        String password = passwordSignupFld.getText();

        if (name.isEmpty() || birth.isEmpty() || address.isEmpty() || email.isEmpty() ||
                username.isEmpty() || phone.isEmpty()|| gender.isEmpty() || password.isEmpty()) {

            displayError("Please Fill All Data");
        } else if (!isNumber(phone)) {
            displayError("Phone Number only number");

        }else  if(phone.length() >11 ) {
            displayError("Phone Number cannot be more than 11 characters");

        }else if(isUsernameInDB(username)){
            displayError("Username is existed in Databse");

        }else {


            String query1 = "INSERT INTO `acc`(`ACC_USERNAME`, `ACC_PASSWORD`,`ACC_AUTHORITY`)" +
                    "VALUES(?,?,?)";

            String query2 = "INSERT INTO `customer`(`CUSTOMER_USERNAME`, `CUSTOMER_NAME`, " +
                    "`CUSTOMER_BIRTH`, `CUSTOMER_GENDER`,`CUSTOMER_ADDRESS`,`CUSTOMER_PHONE`,`CUSTOMER_EMAIL`)" +
                    " VALUES (?,?,?,?,?,?,?)";


            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setString(1, usernameSignupFld.getText());
            preparedStatement.setString(2, passwordSignupFld.getText());
            preparedStatement.setString(3, "Customer");
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, usernameSignupFld.getText());
            preparedStatement.setString(2, nameFld.getText());
            preparedStatement.setString(3, String.valueOf(birthFld.getValue()));
            preparedStatement.setString(4, getgender());
            preparedStatement.setString(5, addressFld.getText());
            preparedStatement.setString(6, phoneFld.getText());
            preparedStatement.setString(7, emailFld.getText());
            preparedStatement.execute();

            displaySuccessful("You have successfully signed up !!!");
        }
    }


    @FXML
    public String getgender(){
        if (genderMale.isSelected()) {
            return "MALE";
        } else if (genderFemale.isSelected()) {
            return "FEMALE";
        } else {
            return null;
        }
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
            Logger.getLogger(UnsignedUIController.class.getName()).log(Level.SEVERE, null, ex);
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
        if(event.getSource()== homeBtn){
            shopping_page.setVisible(true);
            contact_form.setVisible(false);

        } else if (event.getSource() == contactBtn) {
            shopping_page.setVisible(false);
            contact_form.setVisible(true);
        }
    }

    public void close(){
        System.exit(0);
    }

    public void minimize(){
        Stage stage = (Stage)this.main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void loginCus(ActionEvent event){
        shopping_page.setVisible(false);
        login_form.setVisible(true);
    }
    public void exitLog(ActionEvent event){
        shopping_page.setVisible(true);
        login_form.setVisible(false);
    }
    public void exitSignin(ActionEvent event){
        signup_form.setVisible(false);
        shopping_page.setVisible(false);
        login_form.setVisible(true);
        sendOTP_from.setVisible(false);
    }
    public void signUp(ActionEvent event){
        login_form.setVisible(false);
        shopping_page.setVisible(false);
        signup_form.setVisible(true);
    }
    public void resetPassword(ActionEvent event){
        login_form.setVisible(false);
        sendOTP_from.setVisible(true);
    }
    public void sendmeOTP(ActionEvent event){
        sendOTP_from.setVisible(false);
        newpassword_form.setVisible(true);
    }
    public void exitSendmeOTP(ActionEvent event){
        newpassword_form.setVisible(false);
        sendOTP_from.setVisible(true);

    }


    public boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isUsernameInDB(String username)  {
        try {
            String sql = "SELECT COUNT(*) FROM acc WHERE ACC_USERNAME = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shopping_page.setVisible(true);
        login_form.setVisible(false);
        signup_form.setVisible(false);
        sendOTP_from.setVisible(false);
        newpassword_form.setVisible(false);
        contact_form.setVisible(false);
        successfulDiaglog.setVisible(false);

        productGrid.getChildren().clear();

        productGrid.setDisable(true);
        productGrid.setDisable(false);
        try {
            getProduct("Sneaker");

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        tshirtBtn.setStyle("-fx-background-color: transparent");
        jeansBtn.setStyle("-fx-background-color: transparent");
        jacketBtn.setStyle("-fx-background-color: transparent");
        sneakerBtn.setStyle("-fx-background-color: #fbece4; -fx-underline: true;");
        jewelleryBtn.setStyle("-fx-background-color: transparent");

        gender = new ToggleGroup();
        genderFemale.setToggleGroup(gender);
        genderMale.setToggleGroup(gender);

    }

}

