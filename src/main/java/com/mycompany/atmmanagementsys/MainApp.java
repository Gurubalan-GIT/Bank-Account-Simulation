/*
MIT License
Copyright (c) 2018 Guru
Only for Educational purposes and for reference.
*/
package com.mycompany.atmmanagementsys;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        Image icon = new Image("/icons/LoginPage.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        Media someSound = new Media(getClass().getResource("/audio/Welcome.mp3").toString());    
        MediaPlayer mp = new MediaPlayer(someSound);
        mp.play();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
