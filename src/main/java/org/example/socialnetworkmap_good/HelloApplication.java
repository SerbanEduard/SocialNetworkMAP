package org.example.socialnetworkmap_good;

import Controller.LogInController;
import Domain.Friend;
import Domain.Tuple;
import Domain.User;
import Domain.Validators.FriendValidator;
import Domain.Validators.UserValidator;
import Repository.FriendDBRepository;
import Repository.UserDBRepository;
import Service.Service;
import Utils.Status;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serial;
import java.time.LocalDateTime;

public class HelloApplication extends Application {
    private static Service service;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LogInController controller = fxmlLoader.getController();
        controller.setService(service);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        UserDBRepository userDBRepository = new UserDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres", new UserValidator());
        FriendDBRepository friendDBRepository = new FriendDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres", new FriendValidator());
        service = new Service(userDBRepository, friendDBRepository);
        launch();
    }
}