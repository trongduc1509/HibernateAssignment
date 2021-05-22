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
        Parent root = FXMLLoader.load(getClass().getResource("PortalLogin.fxml"));
        Image icon = new Image("OIP.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("HCMUS Portal");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(Boolean.FALSE);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
