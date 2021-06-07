package sample;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import hibernate.DAO.PersonDAO;
import hibernate.POJO.Person;
import hibernate.POJO.Semester;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherScreenController implements Initializable {

    @FXML
    private FontAwesomeIconView signOut;

    @FXML
    private FontAwesomeIconView curAccount;

    @FXML
    private Label curUser;

    @FXML
    private Label curSemester;

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

    private Person curAcc;
    private Semester curSem;

    public void setCurUser(Person temp) {
        curAcc = temp;
        curUser.setText(curAcc.getName());
    }

    public void setCurSemester(Semester temp) {
        curSem = temp;
        if (temp != null)
            curSemester.setText("Học kì hiện tại: " + curSem.getName() + " - " + curSem.getYear());
    }

    @FXML
    void checkAccount(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserAccount.fxml"));
        loader.load();
        UserAccountController uAC = loader.getController();
        uAC.setUserAcc(curAcc);
        stage = new Stage();
        Image icon = new Image("OIP.png");
        stage.getIcons().add(icon);
        stage.setTitle("HCMUS Portal");
        scene = new Scene(loader.getRoot());
        stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(curAccount.getScene().getWindow());
        stage.setResizable(Boolean.FALSE);
        stage.showAndWait();
        String id = curAcc.getId();
        setCurUser(PersonDAO.searchSingleTeacherById(id));
    }

    @FXML
    void classManagement(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClassManagement.fxml"));
        loader.load();
        ClassManagementController cMC = loader.getController();
        cMC.setCurUser(curAcc);
        cMC.setCurSemester(curSem);
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
    void courseManagement(MouseEvent event) throws Exception {
        if (curSem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("!!!THIẾU THÔNG TIN ĐỂ THỰC HIỆN CHỨC NĂNG!!!");
            alert.setContentText("Vui lòng thiết lập học kì hiện tại để thực hiện chức năng này!!!");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseManagement.fxml"));
            loader.load();
            CourseManagementController cMC = loader.getController();
            cMC.setCurUser(curAcc);
            cMC.setCurSemester(curSem);
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
    }

    @FXML
    void logOut(MouseEvent event) throws Exception {
        LoginController lC = new LoginController();
        lC.loadLogOut(event, stage, scene);
    }

    @FXML
    void semesterManagement(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SemesterManagement.fxml"));
        loader.load();
        SemesterManagementController sMC = loader.getController();
        sMC.setCurUser(curAcc);
        sMC.setCurSemester(curSem);
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
    void courseRegistManagement(MouseEvent event) throws Exception {
        if (curSem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("!!!THIẾU THÔNG TIN ĐỂ THỰC HIỆN CHỨC NĂNG!!!");
            alert.setContentText("Vui lòng thiết lập học kì hiện tại để thực hiện chức năng này!!!");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseRegistrationPeriod.fxml"));
            loader.load();
            CourseRegistrationPeriodController cRPC = loader.getController();
            cRPC.setCurUser(curAcc);
            cRPC.setCurSemester(curSem);
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
    }

    @FXML
    void subjectManagement(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SubjectManagement.fxml"));
        loader.load();
        SubjectManagementController sMC = loader.getController();
        sMC.setCurUser(curAcc);
        sMC.setCurSemester(curSem);
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
    void teacherManagement(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherAccounts.fxml"));
        loader.load();
        TeacherAccountsController tAC = loader.getController();
        tAC.setCurUser(curAcc);
        tAC.setCurSemester(curSem);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loadTeacherScreen(MouseEvent event, Stage stage, Scene scene, Person curAcc, Semester curSeme) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherScreen.fxml"));
        loader.load();
        TeacherScreenController tSC = loader.getController();
        tSC.setCurUser(curAcc);
        tSC.setCurSemester(curSeme);
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
}

