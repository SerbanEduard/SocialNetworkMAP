package Controller;

import Domain.User;
import Domain.Validators.ValidationException;
import Service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.socialnetworkmap_good.HelloApplication;

import java.io.IOException;
import java.util.Optional;

public class LogInController {
    Service service;

    @FXML
    TextField textFieldFirstName;
    @FXML
    TextField textFieldLastName;
    @FXML
    Button buttonLogIn;
    @FXML
    Button buttonSignUp;
    @FXML
    Label labelError;

    @FXML
    public void initialize(){
    }

    public void setService(Service service){
        this.service = service;
    }

    private void startMainUi(ActionEvent event, User user) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-ui.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainUiController controller = fxmlLoader.getController();
        controller.setService(service, user);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    public void handleButtonLogInClick(ActionEvent event){
            try{
                User user = service.logInUser(textFieldFirstName.getText(), textFieldLastName.getText());
                startMainUi(event, user);
            }
            catch (IllegalArgumentException | ValidationException e){
                labelError.setText(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    @FXML
    public void handleButtonSignUpClick(ActionEvent event){
            try{
                User user = service.signUpUser(textFieldFirstName.getText(), textFieldLastName.getText());
                startMainUi(event, user);
            }
            catch (IllegalArgumentException | ValidationException e){
                labelError.setText(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

}
