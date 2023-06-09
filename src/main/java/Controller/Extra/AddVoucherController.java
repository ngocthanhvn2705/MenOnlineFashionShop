package Controller.Extra;

import Database.JDBCConnection;
import Models.Voucher;
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

public class AddVoucherController implements Initializable {

    @FXML
    private TextField amountFld;

    @FXML
    private AnchorPane confirmForm;

    @FXML
    private ToggleGroup type;

    @FXML
    private RadioButton typeDivide;

    @FXML
    private RadioButton typeSubtract;

    @FXML
    private ToggleGroup status;

    @FXML
    private RadioButton statusINUSE;

    @FXML
    private RadioButton statusNOUSE;
    @FXML
    private TextField valueFld;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Voucher voucher = null;
    private boolean update = false;
    String voucherId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        type = new ToggleGroup();
        typeSubtract.setToggleGroup(type);
        typeDivide.setToggleGroup(type);
        confirmForm.setVisible(false);
    }

    public void cancel() {
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
    void save(ActionEvent event) throws IOException {
        connection = JDBCConnection.getJDBCConnection();
        String type = getType();
        String value = valueFld.getText();
        String amount = amountFld.getText();
        String status = getStatus();

        if(type.isEmpty() || value.isEmpty() || amount.isEmpty() || status.isEmpty()){
            displayError("Please Fill All Data");
        }else if (!isNumberDouble(value)){
            displayError("Data type of Value is Double");
        } else if (!isNumberInteger(amount)) {
            displayError("Data type of Amount is Integer");
        } else if (Double.parseDouble(value) <= 0){
            displayError("Value always greater than 0");
        }else if (Integer.parseInt(amount) <0){
            displayError("Amount is always greater than or equal to 0");
        }else
            confirmForm.setVisible(true);
    }

    private void insert() throws SQLException {
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,getType());
        preparedStatement.setDouble(2,Double.parseDouble(valueFld.getText()));
        preparedStatement.setInt(3,Integer.parseInt(amountFld.getText()));
        preparedStatement.setString(4, getStatus());
        preparedStatement.execute();
    }

    private void getQuery(){
        if (update == false){
            query = "INSERT INTO voucher (VOUCHER_TYPE, VOUCHER_VALUE, VOUCHER_AMOUNT, VOUCHER_STATUS) " +
                    "VALUES(?,?,?,?)";

        }else {
            query= "UPDATE voucher SET " +
                    "VOUCHER_TYPE = ?, " +
                    "VOUCHER_VALUE = ?, " +
                    "VOUCHER_AMOUNT = ?, " +
                    "VOUCHER_STATUS = ? WHERE VOUCHER_ID ='"+voucherId+"'";

        }
    }
    public void clear() {
        typeDivide.setSelected(false);
        typeSubtract.setSelected(false);
        statusINUSE.setSelected(false);
        statusNOUSE.setSelected(false);
        valueFld.setText(null);
        amountFld.setText(null);

    }

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }



    public String getType() {
        if (typeSubtract.isSelected()) {
            return "SUBTRACT";
        } else if (typeDivide.isSelected()) {
            return "DIVIDE";
        } else {
            return null;
        }
    }
    public String getStatus(){
        if (statusINUSE.isSelected()) {
            return "INUSE";
        } else if (statusNOUSE.isSelected()) {
            return "NOUSE";
        } else {
            return null;
        }
    }

    public void setTextField(String id, String typeString, Double value, Integer amount, String statusString){
        voucherId = id;
        if(typeString.equals("SUBTRACT")){
            type.selectToggle(typeSubtract);
        } else if (typeString.equals("DIVIDE")) {
            type.selectToggle(typeDivide);
        }
        valueFld.setText(String.valueOf(value));
        amountFld.setText(String.valueOf(amount));
        if(statusString.equals("INUSE")){
            status.selectToggle(statusINUSE);
        } else if (statusString.equals("NOUSE")) {
            status.selectToggle(statusNOUSE);
        }
    }
    public boolean isNumberDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
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
    public void setUpdate(boolean b) {
        this.update = b;

    }

}
