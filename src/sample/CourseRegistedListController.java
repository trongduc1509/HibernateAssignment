package sample;

import antlr.collections.impl.LList;
import hibernate.DAO.*;
import hibernate.POJO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CourseRegistedListController implements Initializable {
    private Person curAcc;
    private Semester curSem;

    private Stage stage;
    private Scene scene;

    private List<Course> funcList = null;
    private ObservableList<CourseView> mainList = FXCollections.observableArrayList();

    private List<Semester> tempSList = SemesterDAO.getAllSemester();
    private ObservableList<Semester> semesterList = FXCollections.observableArrayList(tempSList);

    @FXML
    private Label curUser;

    @FXML
    private Label curSemester;

    @FXML
    private Label curUser1;

    @FXML
    private ComboBox<Semester> semList;

    @FXML
    private Label info;

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

    public void setCurUser(Person temp) {
        curAcc = temp;
        curUser.setText(curAcc.getName());
    }

    public void setCurSemester(Semester temp) {
        curSem = temp;
        curSemester.setText("Học kì hiện tại: " + curSem.getName() + " - " + curSem.getYear());
        semList.getItems().addAll(semesterList);
        semList.setValue(curSem);
        updateCourseRegistedList(curSem);
    }

    @FXML
    void getBack(MouseEvent event) throws Exception {
        StudentScreenController sSC = new StudentScreenController();
        sSC.loadStudentScreen(event, stage, scene, curAcc, curSem);
    }

    @FXML
    void logOut(MouseEvent event) throws Exception {
        LoginController lC = new LoginController();
        lC.loadLogOut(event, stage, scene);
    }

    @FXML
    void showList() {
        updateCourseRegistedList(semList.getSelectionModel().getSelectedItem());
    }

    void updateCourseRegistedList(Semester sem) {
        mainList.clear();
        funcList = CourseDAO.getAllCourseRegistedByStudentAndSem(curAcc.getId(), sem.getId());
        for (Course item : funcList) {
            Subject temp = SubjectDAO.getDeteminedSubject(item.getSubjectId());
            mainList.add(new CourseView(item.getCourseId(), item.getSubjectId(), temp.getName(), temp.getCredits(), item.getTeacher(), item.getRoom(), item.getDay(), SessionDAO.getDeterminedSession(item.getSessionId()).toString(), item.getSemesterId(), item.getMaxSlot(), RegistSubjectDAO.countCurrentSlotInCourse(item.getCourseId())));
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("subjectId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("subjectName"));
        creditsColumn.setCellValueFactory(new PropertyValueFactory<CourseView, Integer>("credits"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("teacher"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("room"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("day"));
        sessionColumn.setCellValueFactory(new PropertyValueFactory<CourseView, String>("session"));

        table.setItems(mainList);

        info.setText("Số HP đã đăng kí: " + funcList.size());
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

        table.setItems(mainList);
    }
}
