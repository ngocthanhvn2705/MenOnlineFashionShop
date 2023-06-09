package Controller.Extra;

import Models.getData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SuccessfulController implements Initializable {


    @FXML
    private Label successfulLabel;

    public void setLabel(String a){
        successfulLabel.setText(a);
    }
    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void closeEnter(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
