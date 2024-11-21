package Controller;

import DTO.FriendDTO;
import Domain.Friend;
import Domain.User;
import Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;

public class MainUiController {
    private static User loggedUser;
    private Service service;
    private ObservableList<FriendDTO> model = FXCollections.observableArrayList();

    @FXML
    private Label labelWelcome;
    @FXML
    private Label labelError;
    @FXML
    private TableView<FriendDTO> tableFriends;
    @FXML
    private TableColumn<FriendDTO, String> columnUsername;
    @FXML
    private TableColumn<FriendDTO, LocalDateTime> columnSince;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonRemove;
    @FXML
    private Button buttonRequests;

    public void initalize(){
        columnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnSince.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableFriends.setItems(model);
    }

    public void initModel(){
        model.setAll(getFriends());
    }

    private List<FriendDTO> getFriends(){

    }


    public void setService(Service service, User user){
        this.service = service;
        loggedUser = user;
        labelWelcome.setText("Welcome " + loggedUser.getFirstName() + " " + loggedUser.getLastName());
        initModel();
    }

}
