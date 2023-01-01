package com.example.chatgui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatGUIApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChatGUIApp.class.getResource("chat.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);

        stage.setTitle("Chat");


        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}