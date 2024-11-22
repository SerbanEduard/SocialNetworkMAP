package Controller;

import Domain.User;
import Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AddFriendController {
    private Service service;
    private User loggedUser;
    ObservableList<User> model = FXCollections.observableArrayList();

    @FXML
    private TableView<User> tableUsers;
    @FXML
    private TableColumn<User, String> columnFirstName;
    @FXML
    private TableColumn<User, String> columnLastName;
    @FXML
    private Button buttonAdd;
    @FXML
    private Label labelError;
    @FXML
    private TextField textFieldLastName;

    @FXML
    public void initialize(){
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        FilteredList<User> filteredData = new FilteredList<>(model, p -> true);
        textFieldLastName.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (user.getLastName().toLowerCase().contains(lowerCaseFilter) || user.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        tableUsers.setItems(filteredData);
    }

    public void setService(Service service, User user){
        this.loggedUser = user;
        this.service = service;
        initModel();
    }

    public void initModel(){
        model.setAll(getUsers());
    }

    public List<User> getUsers(){
        return service.getNonFriendedUsers(loggedUser);
    }

    @FXML
    public void handleButtonAddClick(){
        User user = tableUsers.getSelectionModel().getSelectedItem();
        if(user == null){
            labelError.setText("You must select a user!");
        }
        else{
            service.addFriend(loggedUser.getId(), user.getId());
            model.remove(user);
        }
    }
}
