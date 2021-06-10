package sample;

import hibernate.DAO.CourseDAO;
import hibernate.DAO.RegistSubjectDAO;
import hibernate.DAO.SessionDAO;
import hibernate.DAO.SubjectDAO;
import hibernate.POJO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EdittingRegistedCourseController implements Initializable {
    private Person curAcc;
    private Semester curSem;

    private Stage stage;
    private Scene scene;

    private ObservableList<CourseView> mainList = FXCollections.observableArrayList();
    private List<Course> funcCRList = null;

    @FXML
    private Label curUser;

    @FXML
    private Label curSemester;

    @FXML
    private Label curUser1;

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

    @FXML
    private TableColumn<CourseView, Integer> slotColumn;

    @FXML
    private TableColumn<CourseView, Integer> currentColumn;

    @FXML
    private TableColumn<CourseView, CheckBox> selectColumn;

    public void setCurUser(Person temp) {
        curAcc = temp;
        curUser.setText(curAcc.getName());
    }

    public void setCurSemester(Semester temp) {
        curSem = temp;
        curSemester.setText("Học kì hiện tại: " + curSem.getName() + " - " + curSem.getYear());
        updateCourseRegistedList();
    }

    @FXML
    void cancel(MouseEvent event) {
        List<RegistSubject> checkList = RegistSubjectDAO.getListByStuAndSem(curAcc.getId(), curSem.getId());
        List<CourseView> cancelList = new ArrayList<>();
        for (CourseView item : mainList) {
            if (item.getSelect().isSelected())
                cancelList.add(item);
        }
        if (!cancelList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("!!!Xác nhận hủy đăng kí học phần!!!");
            alert.setContentText("Bạn có chắc chắn muốn hủy đăng kí những học phần đã chọn không?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                for (CourseView item : cancelList) {
                    for (RegistSubject check : checkList) {
                        if (item.getCourseId() == check.getCourseId()) {
                            RegistSubjectDAO.delete(check);
                            checkList.remove(check);
                            break;
                        }
                    }
                }
                updateCourseRegistedList();
            }
        }
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

    void updateCourseRegistedList() {
        mainList.clear();
        funcCRList = CourseDAO.getAllCourseRegistedByStudentAndSem(curAcc.getId(), curSem.getId());
        for (Course item : funcCRList) {
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
        slotColumn.setCellValueFactory(new PropertyValueFactory<CourseView, Integer>("maxSlot"));
        currentColumn.setCellValueFactory(new PropertyValueFactory<CourseView, Integer>("registedSlot"));
        selectColumn.setCellValueFactory(new PropertyValueFactory<CourseView, CheckBox>("select"));

        table.setItems(mainList);

        info.setText("Số HP đã đăng kí: " + funcCRList.size());
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
        currentColumn.setCellValueFactory(new PropertyValueFactory<CourseView, Integer>("registedSlot"));
        selectColumn.setCellValueFactory(new PropertyValueFactory<CourseView, CheckBox>("select"));

        table.setItems(mainList);
    }
}
