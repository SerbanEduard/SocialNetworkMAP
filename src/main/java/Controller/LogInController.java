package Controller;

import Service.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LogInController {
    Service service;

    @FXML
    TextField textFieldFirstName;
    @FXML
    TextField textFieldLastName;
    @FXML
    Button buttonLogIn;
    @FXML
    Label labelError;

    @FXML
    public void initialize(){
    }

    public void setService(Service service){
        this.service = service;
    }

    @FXML
    public void handleButtonLogInClick(){
        if(textFieldFirstName.getText().isEmpty() || textFieldLastName.getText().isEmpty()){
            labelError.setText("Introduceti o valoare in fiecare casuta!");
        }
        else{
            try{
                service.logInUser(textFieldFirstName.getText(), textFieldLastName.getText());
            }
            catch (IllegalArgumentException e){
                labelError.setText(e.getMessage());
            }
        }
    }

}
