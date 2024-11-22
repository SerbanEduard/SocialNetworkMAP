package Controller;

import DTO.FriendDTO;
import Domain.Friend;
import Domain.User;
import Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.socialnetworkmap_good.HelloApplication;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private TableColumn<FriendDTO, Integer> columnId;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonRemove;
    @FXML
    private Button buttonRequests;

    public void initialize(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnSince.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableFriends.setItems(model);
    }

    public void initModel(){
        model.setAll(getFriends());
    }

    private List<FriendDTO> getFriends(){
        return service.getAllFriends(loggedUser)
                .stream()
                .map((friend) -> {
                    User friendedUser = service.getUserById(friend.getId().getSecond());
                    return new FriendDTO(
                            friend.getId().getSecond(),
                            String.format("%s %s",friendedUser.getFirstName(), friendedUser.getLastName()),
                            friend.getDateaAdded());

                })
                .collect(Collectors.toList());
    }


    public void setService(Service service, User user){
        this.service = service;
        loggedUser = user;
        labelWelcome.setText("Welcome " + loggedUser.getFirstName() + " " + loggedUser.getLastName());
        initModel();
    }

    @FXML
    public void handleButtonRemoveClick(){
        FriendDTO selectedItem = tableFriends.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            labelError.setText("You must select a friend!");
        }
        else{
            service.deleteFriend(loggedUser.getId(), selectedItem);
            model.remove(selectedItem);
        }
    }

    @FXML
    public void handleButtonAddClick(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-friend.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddFriendController controller = fxmlLoader.getController();
        controller.setService(service, loggedUser);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)(event.getSource())).getScene().getWindow());
        stage.show();
    }

    @FXML
    public void handleButtonRequestsClick(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("friend-requests.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        FriendRequestsController controller = fxmlLoader.getController();
        controller.setService(service, loggedUser);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)(event.getSource())).getScene().getWindow());
        stage.show();
    }
}
