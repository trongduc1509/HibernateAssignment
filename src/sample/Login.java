package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Login extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PortalLogin.fxml"));
        Image icon = new Image("OIP.png");
        stage.getIcons().add(icon);
        stage.setTitle("HCMUS Portal");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
