package Controller;

import Domain.Friend;
import Domain.User;
import Service.Service;
import Utils.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class FriendRequestsController {
    private User loggedUser;
    private Service service;
    ObservableList<User> model = FXCollections.observableArrayList();

    @FXML
    private TableView<User> tableRequests;
    @FXML
    private TableColumn<User, String> columnFirstName;
    @FXML
    private TableColumn<User, String> columnLastName;
    @FXML
    private Button buttonAccept;
    @FXML
    private Button buttonReject;
    @FXML
    private Label labelError;

    @FXML
    public void initialize(){
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableRequests.setItems(model);
    }

    public void setService(Service service, User user){
        this.service = service;
        this.loggedUser = user;
        initModel();
    }

    private void initModel(){
        model.setAll(getPending());
    }

    private List<User> getPending(){
        return service.getPendingUsers(loggedUser);
    }

    private boolean checkInput(User user){
        if(user == null){
            labelError.setText("You must select a user!");
            return false;
        }
        return true;
    }

    @FXML
    public void handleButtonAcceptClick(){
        User user = tableRequests.getSelectionModel().getSelectedItem();
        if(checkInput(user)){
            service.updateStatus(loggedUser.getId(), user.getId());
            model.remove(user);
        }
    }

    @FXML
    public void handleButtonRejectClick(){
        User user = tableRequests.getSelectionModel().getSelectedItem();
        if(checkInput(user)){
            service.declineFriend(loggedUser.getId(), user.getId());
            model.remove(user);
        }
    }
}
