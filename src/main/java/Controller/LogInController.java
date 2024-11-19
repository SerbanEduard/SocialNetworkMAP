package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LogInController {

    @FXML
    TextField textFieldFirstName;
    @FXML
    TextField textFieldLastName;
    @FXML
    Button buttonLogIn;
    @FXML
    Label labelError;


    @FXML
    public void handleButtonLogInClick(){
        if(textFieldFirstName.getText().isEmpty() || textFieldLastName.getText().isEmpty()){
            labelError.setText("Introduceti o valoare in fiecare casuta!");
        }
        else{

        }
    }

}
