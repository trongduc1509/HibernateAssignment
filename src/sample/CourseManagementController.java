package sample;

import hibernate.DAO.CourseDAO;
import hibernate.DAO.RegistSubjectDAO;
import hibernate.DAO.SessionDAO;
import hibernate.DAO.SubjectDAO;
import hibernate.POJO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CourseManagementController implements Initializable {
    private Person curAcc;
    private Semester curSem;

    private Stage stage;
    private Scene scene;

    private ObservableList<CourseView> mainList = FXCollections.observableArrayList();
    private List<Course> funcList = null;

    @FXML
    private Label curUser;

    @FXML
    private Label curSemester;

    @FXML
    private Label curUser1;

    @FXML
    private TextField find;

    @FXML
    private TableView<CourseView> table;

    @FXML
    private TableColumn<CourseView, String> idColumn;

    @FXML
    private TableColumn<CourseView, String> nameColumn;

    @FXML
    private TableColumn<CourseView, Integer> creditsColumn;

    @FXML
    private TableColumn<CourseView, String> teacherColumn;

    @FXML
    private TableColumn<CourseView, String> roomColumn;

    @FXML
    private TableColumn<CourseView, String> dayColumn;

    @FXML
    private TableColumn<CourseView, String> sessionColumn;

    @FXML
    private TableColumn<CourseView, Integer> slotColumn;


    public void setCurUser(Person temp) {
        curAcc = temp;
        curUser.setText(curAcc.getName());
    }

    public void setCurSemester(Semester temp) {
        curSem = temp;
        curSemester.setText("Học kì hiện tại: " + curSem.getName() + " - " + curSem.getYear());
        updateCourseListByTable();
    }

    @FXML
    void addCourse(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddingCourse.fxml"));
        loader.load();
        AddingCourseController aC = loader.getController();
        aC.setCurSem(curSem);
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
        stage.initOwner(curUser.getScene().getWindow());
        stage.setResizable(Boolean.FALSE);
        stage.showAndWait();
        updateCourseListByTable();
    }

    @FXML
    void delCourse(MouseEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            CourseView del = table.getSelectionModel().getSelectedItem();
            List<RegistSubject> check = RegistSubjectDAO.getListByCourseId(del.getCourseId());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("!!!XÓA KÌ ĐĂNG KÍ HỌC PHẦN!!!");
            alert.setContentText("Bạn có chắc chắn muốn xóa học phần này không?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Course delOb = CourseDAO.getDeteminedCourse(del.getCourseId());
                if (!check.isEmpty()) {
                    for (RegistSubject item : check)
                        RegistSubjectDAO.delete(item);
                }
                CourseDAO.delete(delOb);
                updateCourseListByTable();
            }
        }
    }

    @FXML
    void findCourse(MouseEvent event) {
        if (!find.getText().isEmpty()) {
            mainList.clear();
            funcList = CourseDAO.getAllCourseBySemesterAndSbj(curSem.getId(), find.getText());
            for (Course item : funcList) {
                Subject temp = SubjectDAO.getDeteminedSubject(item.getSubjectId());
                mainList.add(new CourseView(item.getCourseId(), item.getSubjectId(), temp.getName(), temp.getCredits(), item.getTeacher(), item.getRoom(), item.getDay(), SessionDAO.getDeterminedSession(item.getSessionId()).toString(), item.getSemesterId(), item.getMaxSlot()));
            }

            idColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("subjectId"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("subjectName"));
            creditsColumn.setCellValueFactory(new PropertyValueFactory<CourseView, Integer>("credits"));
            teacherColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("teacher"));
            roomColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("room"));
            dayColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("day"));
            sessionColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("session"));
            slotColumn.setCellValueFactory(new PropertyValueFactory<CourseView, Integer>("maxSlot"));

            table.setItems(mainList);
        }
    }

    @FXML
    void getBack(MouseEvent event) throws Exception {
        TeacherScreenController tSC = new TeacherScreenController();
        tSC.loadTeacherScreen(event, stage, scene, curAcc, curSem);
    }

    @FXML
    void logOut(MouseEvent event) throws Exception {
        LoginController lC = new LoginController();
        lC.loadLogOut(event, stage, scene);
    }

    @FXML
    void updateCourseListByTable() {
        mainList.clear();
        funcList = CourseDAO.getAllCourseBySemester(curSem.getId());
        for (Course item : funcList) {
            Subject temp = SubjectDAO.getDeteminedSubject(item.getSubjectId());
            mainList.add(new CourseView(item.getCourseId(), item.getSubjectId(), temp.getName(), temp.getCredits(), item.getTeacher(), item.getRoom(), item.getDay(), SessionDAO.getDeterminedSession(item.getSessionId()).toString(), item.getSemesterId(), item.getMaxSlot()));
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("subjectId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("subjectName"));
        creditsColumn.setCellValueFactory(new PropertyValueFactory<CourseView, Integer>("credits"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("teacher"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("room"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("day"));
        sessionColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("session"));
        slotColumn.setCellValueFactory(new PropertyValueFactory<CourseView, Integer>("maxSlot"));

        table.setItems(mainList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("subjectId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("subjectName"));
        creditsColumn.setCellValueFactory(new PropertyValueFactory<CourseView, Integer>("credits"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("teacher"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("room"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("day"));
        sessionColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("session"));
        slotColumn.setCellValueFactory(new PropertyValueFactory<CourseView, Integer>("maxSlot"));

        table.setItems(mainList);
    }
}
