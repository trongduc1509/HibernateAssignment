package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private AnchorPane mainStage;

    @FXML
    private SplitPane split;

    @FXML
    private Label txtStatus1;

    @FXML
    private Button loginButton1;

    @FXML
    private PasswordField userInputP1;

    @FXML
    private TextField userInputN1;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void loginRequest(ActionEvent event) throws Exception {
        if (userInputN1.getText().compareTo("admin") == 0 && userInputP1.getText().compareTo("1234") == 0)
           this.switchTeacherScreen(event);
        else if (userInputN1.getText().compareTo("student") == 0 && userInputP1.getText().compareTo("12345") == 0)
            this.switchStudentScreen(event);
        else
            txtStatus1.setText("Wrong username or password!");
    }

    public void switchTeacherScreen(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("TeacherScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Image icon = new Image("OIP.png");
        stage.getIcons().add(icon);
        stage.setTitle("HCMUS Portal");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(Boolean.FALSE);
        stage.show();
    }

    public void switchStudentScreen(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("StudentScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Image icon = new Image("OIP.png");
        stage.getIcons().add(icon);
        stage.setTitle("HCMUS Portal");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(Boolean.FALSE);
        stage.show();
    }

}
