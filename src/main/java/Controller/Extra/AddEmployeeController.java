package Controller.Extra;

import Database.JDBCConnection;
import Models.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddEmployeeController implements Initializable {

    @FXML
    private PasswordField passwordFld;
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
    private TextField usernameFld;
    @FXML
    private AnchorPane confirmForm;

    String query1 = null;
    String query2= null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Employee employee = null;
    private boolean update = false;
    String employeeId;
    String employeeUsername;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gender = new ToggleGroup();
        genderFemale.setToggleGroup(gender);
        genderMale.setToggleGroup(gender);
        confirmForm.setVisible(false);
    }
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

    public void confirm() throws IOException {

        getQuery();
        insert();
        clear();
        cancel();

        displaySuccessful("The data was successfully saved!");
    }

    @FXML
    private void save(ActionEvent event) throws IOException {

        connection = JDBCConnection.getJDBCConnection();
        String name = nameFld.getText();
        String username = usernameFld.getText();
        String gender = getgender();
        String birth = String.valueOf(birthFld.getValue());
        String address = addressFld.getText();
        String phone = phoneFld.getText();
        String email = emailFld.getText();
        String password = passwordFld.getText();

        if (name.isEmpty() || birth.isEmpty() || address.isEmpty() || email.isEmpty() ||
            username.isEmpty() || phone.isEmpty()|| gender.isEmpty() || password.isEmpty()) {

            displayError("Please Fill All Data");
        } else if (!isNumber(phone)) {
            displayError("Phone Number only number");

        }else  if(phone.length() >11 ) {
            displayError("Phone Number cannot be more than 11 characters");

        }else if(isUsernameInDB(username) && !update){
            displayError("Username is existed in Databse");

        }else
            confirmForm.setVisible(true);

    }
    private void insert() {

        try {
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setString(1,usernameFld.getText());
            preparedStatement.setString(2,passwordFld.getText());
            preparedStatement.setString(3,"Employee");
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, usernameFld.getText());
            preparedStatement.setString(2, nameFld.getText());
            preparedStatement.setString(3, String.valueOf(birthFld.getValue()));
            preparedStatement.setString(4, getgender());
            preparedStatement.setString(5, addressFld.getText());
            preparedStatement.setString(6, phoneFld.getText());
            preparedStatement.setString(7, emailFld.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AddEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clear() {
        usernameFld.setText(null);
        passwordFld.setText(null);
        nameFld.setText(null);
        birthFld.setValue(null);
        addressFld.setText(null);
        phoneFld.setText(null);
        emailFld.setText(null);
        genderMale.setSelected(false);
        genderFemale.setSelected(false);
    }

    private void getQuery() {

        if (update == false) {

            query1 = "INSERT INTO `acc`(`ACC_USERNAME`, `ACC_PASSWORD`,`ACC_AUTHORITY`)" +
                    "VALUES(?,?,?)";

            query2 = "INSERT INTO `employee`(`EMPLOYEE_USERNAME`, `EMPLOYEE_NAME`, " +
                    "`EMPLOYEE_BIRTH`, `EMPLOYEE_GENDER`,`EMPLOYEE_ADDRESS`,`EMPLOYEE_PHONE`,`EMPLOYEE_EMAIL`)" +
                    " VALUES (?,?,?,?,?,?,?)";

        }else{
            query1 = "UPDATE `acc` SET "
                    + "`ACC_USERNAME`=?,"
                    + "`ACC_PASSWORD`=?,"
                    + "`ACC_AUTHORITY`=? WHERE ACC_USERNAME = '"+employeeUsername+"'";

            query2 = "UPDATE `employee` SET "
                    + "`EMPLOYEE_USERNAME`=?,"
                    + "`EMPLOYEE_NAME`=?,"
                    + "`EMPLOYEE_BIRTH`=?,"
                    + "`EMPLOYEE_GENDER`= ?,"
                    + "`EMPLOYEE_ADDRESS`= ?,"
                    + "`EMPLOYEE_PHONE`= ?,"
                    + "`EMPLOYEE_EMAIL`= ? WHERE EMPLOYEE_ID = '"+employeeId+"'";
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

    public void setTextField(String id, String username,String password, String name, LocalDate toLocalDate,String genderString,
                      String address,String phone, String email){
        employeeId = id;
        employeeUsername = username;
        usernameFld.setText(username);
        passwordFld.setText(password);
        nameFld.setText(name);
        birthFld.setValue(toLocalDate);
        if(genderString.equals("MALE")){
            gender.selectToggle(genderMale);
        } else if (genderString.equals("FEMALE")) {
            gender.selectToggle(genderFemale);
        }
        addressFld.setText(address);
        phoneFld.setText(phone);
        emailFld.setText(email);
    }
    public void setUpdate(boolean b) {
        this.update = b;

    }
    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
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
}
