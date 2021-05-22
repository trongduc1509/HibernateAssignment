package sample;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TeacherScreenController {

    @FXML
    private FontAwesomeIconView signOut;

    @FXML
    private FontAwesomeIconView curAccount;

    @FXML
    private AnchorPane courseList;

    @FXML
    private AnchorPane semesterList;

    @FXML
    private AnchorPane subjectList;

    @FXML
    private AnchorPane studentList;

    @FXML
    private AnchorPane classList;

    @FXML
    private AnchorPane teacherList;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void checkAccount(MouseEvent event) {

    }

    @FXML
    void classManagement(MouseEvent event) {

    }

    @FXML
    void courseManagement(MouseEvent event) {

    }

    @FXML
    void logOut(MouseEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("PortalLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Image icon = new Image("OIP.png");
        stage.getIcons().add(icon);
        stage.setTitle("HCMUS Portal");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(Boolean.FALSE);
        stage.show();
    }

    @FXML
    void semesterManagement(MouseEvent event) {

    }

    @FXML
    void studentManagement(MouseEvent event) {

    }

    @FXML
    void subjectManagement(MouseEvent event) {

    }

    @FXML
    void teacherManagement(MouseEvent event) {

    }

}

