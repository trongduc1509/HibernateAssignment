package sample;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import hibernate.POJO.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentScreenController implements Initializable {
    @FXML
    Label curUser;

    @FXML
    private FontAwesomeIconView signOut;

    @FXML
    private FontAwesomeIconView curAccount;

    @FXML
    private AnchorPane cR;

    @FXML
    private AnchorPane cL;

    @FXML
    private AnchorPane cE;

    private Stage stage;
    private Scene scene;

    private Person curUserAcc;

    public void setCurUserAcc(Person temp) {
        curUserAcc = temp;
        curUser.setText(curUserAcc.getName());
    }

    @FXML
    void checkAccount(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserAccount.fxml"));
        loader.load();
        UserAccountController uAC = loader.getController();
        uAC.setUserAcc(curUserAcc);
        stage = new Stage();
        Image icon = new Image("OIP.png");
        stage.getIcons().add(icon);
        stage.setTitle("HCMUS Portal");
        scene = new Scene(loader.getRoot());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(curAccount.getScene().getWindow());
        stage.setResizable(Boolean.FALSE);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
        stage.showAndWait();
    }

    @FXML
    void courseErase(MouseEvent event) {

    }

    @FXML
    void courseList(MouseEvent event) {

    }

    @FXML
    void logOut(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PortalLogin.fxml"));
        loader.load();
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

    @FXML
    void registCourse(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
