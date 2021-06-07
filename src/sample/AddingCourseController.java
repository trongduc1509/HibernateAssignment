package sample;

import hibernate.DAO.CourseDAO;
import hibernate.DAO.SessionDAO;
import hibernate.DAO.SubjectDAO;
import hibernate.POJO.Course;
import hibernate.POJO.Semester;
import hibernate.POJO.Session;
import hibernate.POJO.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

public class AddingCourseController {
    private Semester curSem;
    private List<Course> checkList = null;

    private List<Subject> tempSubjList = SubjectDAO.getAllSubject();
    private ObservableList<Subject> subjList = FXCollections.observableArrayList(tempSubjList);

    private List<Session> tempSeList = SessionDAO.getAllSession();
    private ObservableList<Session> seList = FXCollections.observableArrayList(tempSeList);

    @FXML
    private ComboBox<Subject> subjectP;

    @FXML
    private TextField teacherP;

    @FXML
    private TextField roomP;

    @FXML
    private TextField dayP;

    @FXML
    private ComboBox<Session> sessionP;

    @FXML
    private TextField slotP;

    @FXML
    private Label status;

    public void setCurSem(Semester temp) {
        curSem = temp;
        subjectP.getItems().addAll(subjList);
        sessionP.getItems().addAll(seList);
    }

    @FXML
    void addTeacherAccount(MouseEvent event) {
        if (subjectP.getSelectionModel().getSelectedItem() == null || teacherP.getText().isEmpty() || roomP.getText().isEmpty() || dayP.getText().isEmpty() || sessionP.getSelectionModel().getSelectedItem() == null || slotP.getText().isEmpty())
            status.setText("Chú ý điền đầy đủ các thông tin!!!");
        else {
            Course newCourse = new Course();
            newCourse.setSubjectId(subjectP.getSelectionModel().getSelectedItem().getId());
            newCourse.setTeacher(teacherP.getText());
            newCourse.setRoom(roomP.getText());
            newCourse.setDay(dayP.getText());
            newCourse.setSessionId(sessionP.getSelectionModel().getSelectedItem().getId());
            newCourse.setSemesterId(curSem.getId());
            newCourse.setMaxSlot(Integer.parseInt(slotP.getText()));

            if (checkSimilarity(newCourse))
                status.setText("Học phần không hợp lệ (Trùng ngày, phòng, ca)");
            else {
                CourseDAO.add(newCourse);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }
        }
    }

    public boolean checkSimilarity(Course temp) {
        checkList = CourseDAO.getAllCourseBySemester(curSem.getId());
        for (Course item : checkList) {
            if (temp.getDay().compareTo(item.getDay()) == 0 && temp.getRoom().compareTo(item.getRoom()) == 0 && temp.getSemesterId().equals(item.getSemesterId()))
                return true;
        }
        return false;
    }
}
