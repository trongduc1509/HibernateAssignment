package sample;

import hibernate.DAO.PersonDAO;
import hibernate.POJO.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
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
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;


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

    private List<Person> userList = PersonDAO.getAllUser();

    public void loginRequest(ActionEvent event) throws Exception {
        String userN = userInputN1.getText();
        String userP = userInputP1.getText();
        Person curAcc = findExistAccount(userN, userP);

        if (curAcc == null)
            txtStatus1.setText("Wrong username or password!!");
        else if (curAcc.getRole().compareTo("GV") == 0)
            switchTeacherScreen(event, curAcc);
        else
            switchStudentScreen(event, curAcc);
    }

    public void switchTeacherScreen(ActionEvent event, Person curAcc) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherScreen.fxml"));
        loader.load();
        TeacherScreenController tSC = loader.getController();
        tSC.setCurUser(curAcc);
        tSC.setCurSemester(null);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Image icon = new Image("OIP.png");
        stage.getIcons().add(icon);
        stage.setTitle("HCMUS Portal");
        scene = new Scene(loader.getRoot());
        stage.setScene(scene);
        stage.setResizable(Boolean.FALSE);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
    }

    public void switchStudentScreen(ActionEvent event, Person curAcc) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentScreen.fxml"));
        loader.load();
        StudentScreenController sSC = loader.getController();
        sSC.setCurUserAcc(curAcc);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Image icon = new Image("OIP.png");
        stage.getIcons().add(icon);
        stage.setTitle("HCMUS Portal");
        scene = new Scene(loader.getRoot());
        stage.setScene(scene);
        stage.setResizable(Boolean.FALSE);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
    }

    private Person findExistAccount(String name, String pass) {
        for (Person item : userList) {
            if (item.getUsername().equals(name) && item.getPassword().equals(pass))
                return item;
        }
        return null;
    }
}
