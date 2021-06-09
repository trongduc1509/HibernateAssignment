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
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegistCourseController implements Initializable {
    private Person curAcc;
    private Semester curSem;

    private Stage stage;
    private Scene scene;

    private ObservableList<CourseView> mainList = FXCollections.observableArrayList();
    private List<Course> funcCList = null;
    private List<Course> funcCRList = null;
    private List<Course> funcCSList = null;

    @FXML
    private Label curUser;

    @FXML
    private Label curSemester;

    @FXML
    private Label curUser1;

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
        updateCourseRegisterList();
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
    void regist(MouseEvent event) throws Exception {
        List<CourseView> newRegisted = new ArrayList<CourseView>();
        for (CourseView item : mainList) {
            if (item.getSelect().isSelected() && !item.getSelect().isDisable())
                newRegisted.add(item);
        }
        if (!newRegisted.isEmpty()) {
            Alert alert;
            if (funcCRList.size() + newRegisted.size() > 8) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("!!!Lỗi đăng kí học phần!!!");
                alert.setContentText("Không thể đăng kí quá 8 môn cho một học kì!!!");
                alert.showAndWait();
            }
            else {
                for (CourseView item : newRegisted) {
                    for (CourseView temp : newRegisted) {
                        if (item.getCourseId() != temp.getCourseId() && ((item.getDay().compareTo(temp.getDay()) == 0 && item.getSession().compareTo(temp.getSession()) == 0) || item.getSubjectId().compareTo(temp.getSubjectId()) == 0)) {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText("!!!Lỗi đăng kí học phần!!!");
                            alert.setContentText("Các học phần chuẩn bị đăng kí không hợp lệ (Trùng ngày, giờ hoặc trùng môn học)!!!");
                            alert.showAndWait();
                            return;
                        }
                    }

                    for (Course temp : funcCRList) {
                        if (item.getSubjectId().compareTo(temp.getSubjectId()) == 0 || (item.getDay().compareTo(temp.getDay()) == 0 && item.getSession().compareTo(SessionDAO.getDeterminedSession(temp.getSessionId()).toString()) == 0)) {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText("!!!Lỗi đăng kí học phần!!!");
                            alert.setContentText("Các học phần chuẩn bị đăng kí trùng ngày và giờ hoặc môn học với các học phần đã đăng kí!!!");
                            alert.showAndWait();
                            return;
                        }
                    }
                }
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("!!!Xác nhận đăng kí học phần!!!");
                alert.setContentText("Bạn có chắc chắn muốn đăng kí những học phần đã chọn không?");
                if (alert.showAndWait().get() == ButtonType.OK) {
                    for (CourseView item : newRegisted) {
                        RegistSubject newR = new RegistSubject();
                        newR.setCourseId(item.getCourseId());
                        newR.setStudent(curAcc.getId());
                        RegistSubjectDAO.add(newR);
                    }
                    updateCourseRegisterList();
                }
            }
        }
    }

    void updateCourseRegisterList() {
        mainList.clear();
        funcCList = CourseDAO.getAllCourseBySemester(curSem.getId());
        funcCRList = CourseDAO.getAllCourseRegistedByStudentAndSem(curAcc.getId(), curSem.getId());
        funcCSList = CourseDAO.getAllCourseRegistedByStudent(curAcc.getId());
        for (Course item : funcCList) {
            Subject temp = SubjectDAO.getDeteminedSubject(item.getSubjectId());
            CourseView newCV = new CourseView(item.getCourseId(), item.getSubjectId(), temp.getName(), temp.getCredits(), item.getTeacher(), item.getRoom(), item.getDay(), SessionDAO.getDeterminedSession(item.getSessionId()).toString(), item.getSemesterId(), item.getMaxSlot(), RegistSubjectDAO.countCurrentSlotInCourse(item.getCourseId()));
            if (newCV.getMaxSlot() == newCV.getRegistedSlot())
                newCV.getSelect().setDisable(true);
            for (Course comp : funcCRList) {
                if (newCV.getCourseId() == comp.getCourseId()) {
                    newCV.getSelect().setSelected(true);
                    newCV.getSelect().setDisable(true);
                    break;
                }
            }
            mainList.add(newCV);
        }

        for (Course item : funcCSList) {
            for (CourseView temp : mainList)
            if (item.getSubjectId().compareTo(temp.getSubjectId()) == 0)
                temp.getSelect().setDisable(true);
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
