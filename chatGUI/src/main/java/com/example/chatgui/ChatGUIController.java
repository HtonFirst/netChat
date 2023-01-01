package com.example.chatgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class ChatGUIController  {


    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    public static final String ADDRESS = "localhost";
    public static final int PORT = 6001;

    Stage stage;



    @FXML
    private static String USERNAME;

    @FXML
    AnchorPane aPane;

    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    @FXML
    Button sendBtn;

    @FXML
    Button setNameBtn;

    @FXML
    TextField nameTextField;

    @FXML
    Button submitNameBtn;

    @FXML
    HBox authPanel;

    @FXML
    TextField loginField;

    @FXML
    PasswordField passwordField;

    @FXML
    Button sgnBtn;

    @FXML
    HBox bottomPanel;
    private boolean isAuthorized;
    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;

        if(!isAuthorized) {
            authPanel.setVisible(true);
            authPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);

        } else {
            authPanel.setVisible(false);
            authPanel.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
        }

    }

    public void connect() {

        try {
            socket = new Socket(ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {

                    while (true) {
                        String str = in.readUTF();
                        if ("auth-ok".equals(str)) {
                            setAuthorized(true);
                            textArea.clear();
                            break;

                        } else {
                            textArea.appendText(str +"\n");
                        }
                    }

                    while (true) {
                        String str = in.readUTF();
                        if ("/serverClosed".equals(str)) {
                            break;
                        }
                        textArea.appendText(str + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setAuthorized(false);
                }

            }).start();

        } catch (UnknownHostException e) {
            e.printStackTrace();
            textArea.appendText ("Connection refused\n");
        } catch (IOException e) {
            e.printStackTrace();
            textArea.appendText ("Connection refused\n");
        }

    }

    public void sendMessage() {
        if(!textField.getText().isBlank()) {
            try {
                out.writeUTF(textField.getText());
                textField.clear();
                textField.requestFocus();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void tryToAuth(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeUTF("/auth" + loginField.getText() + " " + passwordField.getText());
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}