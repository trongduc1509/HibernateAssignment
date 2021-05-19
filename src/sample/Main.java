package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Login loginPage = new Login();
        loginPage.start(primaryStage);
        primaryStage.setResizable(Boolean.FALSE);
    }


    public static void main(String[] args) {
        launch(args);
    }
}