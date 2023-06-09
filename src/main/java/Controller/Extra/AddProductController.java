package Controller.Extra;

import Database.JDBCConnection;
import Models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    @FXML
    private AnchorPane confirmForm;

    @FXML
    private AnchorPane main_form;
    @FXML
    private TextField descriptionFld;

    @FXML
    private TextField idFld;

    @FXML
    private TextField nameFld;

    @FXML
    private TextField originFld;

    @FXML
    private TextField priceFld;

    @FXML
    private ImageView product_imageView;

    @FXML
    private TextField quantityFld;

    @FXML
    private ToggleGroup status;

    @FXML
    private RadioButton statusAvailable;

    @FXML
    private RadioButton statusOutofstock;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Product product = null;
    private boolean update = false;
    private Image image;
    String productId;

    public void cancel(){
        confirmForm.setVisible(false);
    }

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

    public void confirm() throws SQLException, IOException {
        getQuery();
        insert();
        clear();
        cancel();

        displaySuccessful("The data was successfully saved!");
    }

    @FXML
    void productInsertImage(ActionEvent event) {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("File Image", new String[]{ "*jpg", "*png","jpeg"}));
        File file = open.showOpenDialog(main_form.getScene().getWindow());
        if (file != null){
            image = new Image(file.toURI().toString(),291,270,false,true);
            product_imageView.setImage(image);
        }

    }

    @FXML
    void save(ActionEvent event) throws IOException {
        connection = JDBCConnection.getJDBCConnection();
        String id = idFld.getText();
        String name = nameFld.getText();
        String price = priceFld.getText();
        String quantity = quantityFld.getText();
        String status = getStatus();
        String origin = originFld.getText();

        if(id.isEmpty() || name.isEmpty() || price.isEmpty() || quantity.isEmpty() ||
            status.isEmpty() || origin.isEmpty()) {
            displayError("Please Fill All Data");

        } else if (!isNumberInteger(price)) {
            displayError("Price only number integer");

        } else if (!isNumberInteger(quantity)) {
            displayError("Price only number integer");

        } else if (isProductidInDB(id)) {
            displayError("Product ID is existed in Databse");

        } else
            confirmForm.setVisible(true);

    }

    public void insert() throws SQLException, FileNotFoundException {
        InputStream fileInputStream;
        if (product_imageView.getImage() != null) {
            String filepath = convertURLToFilepath(product_imageView.getImage().getUrl());
            fileInputStream = new FileInputStream(filepath);
        }else {
            fileInputStream = null;
        }

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, idFld.getText());
        preparedStatement.setString(2, nameFld.getText());
        preparedStatement.setString(3, descriptionFld.getText());
        preparedStatement.setBinaryStream(4, fileInputStream);
        preparedStatement.setInt(5,Integer.parseInt(priceFld.getText()));
        preparedStatement.setInt(6, Integer.parseInt(quantityFld.getText()));
        preparedStatement.setString(7, getStatus());
        preparedStatement.setString(8, originFld.getText());

        preparedStatement.execute();

    }

    @FXML
    void clear() {
        idFld.setText(null);
        nameFld.setText(null);
        descriptionFld.setText(null);
        product_imageView.setImage(null);
        priceFld.setText(null);
        quantityFld.setText(null);
        statusAvailable.setSelected(false);
        statusOutofstock.setSelected(false);
        originFld.setText(null);
    }

    private void getQuery(){
        if (update == false) {

            query = "INSERT INTO `product`(`PRODUCT_ID`, `PRODUCT_NAME`, " +
                    "`PRODUCT_DESCRIPTION`, `PRODUCT_IMAGE`,`PRODUCT_PRICE`," +
                    "`PRODUCT_QUANTITY`,`PRODUCT_STATUS`,`PRODUCT_ORIGIN`)" +
                    " VALUES (?,?,?,?,?,?,?,?)";

        }else{

            query = "UPDATE `product` SET "
                    + "`PRODUCT_ID`=?,"
                    + "`PRODUCT_NAME`=?,"
                    + "`PRODUCT_DESCRIPTION`=?,"
                    + "`PRODUCT_IMAGE`= ?,"
                    + "`PRODUCT_PRICE`= ?,"
                    + "`PRODUCT_QUANTITY`= ?,"
                    + "`PRODUCT_STATUS`= ?,"
                    + "`PRODUCT_ORIGIN`= ? WHERE PRODUCT_ID = '"+ productId+"'";
        }

    }

    public String getStatus(){
        if (statusAvailable.isSelected()) {
            return "AVAILABLE";
        } else if (statusOutofstock.isSelected()) {
            return "OUT OF STOCK";
        } else {
            return null;
        }
    }

    public void setTextField(String id, String name, String description, Image image, Integer price,
                             Integer quantity, String statusString, String origin){
        productId = id;
        idFld.setText(id);
        nameFld.setText(name);
        descriptionFld.setText(description);
        product_imageView.setImage(image);
        priceFld.setText(String.valueOf(price));
        quantityFld.setText(String.valueOf(quantity));
        if(statusString.equals("AVAILABLE")){
            status.selectToggle(statusAvailable);
        } else if (statusString.equals("OUT OF STOCK")) {
            status.selectToggle(statusOutofstock);
        }
        originFld.setText(origin);
    }

    public void setUpdate(boolean b) {
        this.update = b;

    }

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        status = new ToggleGroup();
        statusAvailable.setToggleGroup(status);
        statusOutofstock.setToggleGroup(status);
        confirmForm.setVisible(false);
    }

    public boolean isProductidInDB(String productid)  {
        try {
            String sql = "SELECT COUNT(*) FROM product WHERE PRODUCT_ID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productid);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean isNumberInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static String convertURLToFilepath(String url) {
        String path = url.replace("file:", "");
        return path.substring(1);
    }

}
