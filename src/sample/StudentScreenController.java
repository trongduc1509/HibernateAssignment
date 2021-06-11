package sample;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import hibernate.DAO.*;
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
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentScreenController implements Initializable {
    @FXML
    Label curUser;

    @FXML
    private Label curSemester;

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
    private Semester curSem;

    public void setCurUserAcc(Person temp) {
        curUserAcc = temp;
        curUser.setText(curUserAcc.getName());
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
        String id = curUserAcc.getId();
        setCurUserAcc(PersonDAO.searchSingleStudentById(id));
    }

    @FXML
    void courseErase(MouseEvent event) throws Exception {
        if (curSem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("!!!Không thể chỉnh sửa đăng kí học phần!!!");
            alert.setContentText("Giáo vụ chưa set học kì hiện tại!!!");
            alert.showAndWait();
            return;
        }
        if (CrsDAO.checkCanRegist(curSem.getId())) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EdittingRegistedCourse.fxml"));
            loader.load();
            EdittingRegistedCourseController eRCC = loader.getController();
            eRCC.setCurUser(curUserAcc);
            eRCC.setCurSemester(curSem);
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
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("!!!Không thể chỉnh sửa đăng kí học phần!!!");
            alert.setContentText("Không tồn tại kì đăng kí học phần nào hiện tại / hết thời hạn đăng kí (chỉnh sửa) học phần!!!");
            alert.showAndWait();
        }

    }

    @FXML
    void courseList(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseRegistedList.fxml"));
        loader.load();
        CourseRegistedListController cRLC = loader.getController();
        cRLC.setCurUser(curUserAcc);
        cRLC.setCurSemester(curSem);
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
    void logOut(MouseEvent event) throws Exception {
        LoginController lC = new LoginController();
        lC.loadLogOut(event, stage, scene);
    }

    @FXML
    void registCourse(MouseEvent event) throws Exception {
        if (CrsDAO.checkCanRegist(curSem.getId())) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistCourse.fxml"));
            loader.load();
            RegistCourseController rCC = loader.getController();
            rCC.setCurUser(curUserAcc);
            rCC.setCurSemester(curSem);
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
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("!!!Không thể đăng kí học phần!!!");
            alert.setContentText("Không tồn tại kì đăng kí học phần nào hiện tại!!!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loadStudentScreen(MouseEvent event, Stage stage, Scene scene, Person curAcc, Semester curSem) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentScreen.fxml"));
        loader.load();
        StudentScreenController sSC = loader.getController();
        sSC.setCurUserAcc(curAcc);
        int loadSem = SemesterDAO.loadCurrentSemester();
        if (loadSem == -1)
            sSC.setCurSemester(null);
        else
            sSC.setCurSemester(SemesterDAO.getSemesterById(loadSem));
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
