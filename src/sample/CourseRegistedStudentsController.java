package sample;

import hibernate.DAO.PersonDAO;
import hibernate.POJO.CourseView;
import hibernate.POJO.Person;
import hibernate.POJO.PersonInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CourseRegistedStudentsController implements Initializable {
    private ObservableList<PersonInfo> studentList = FXCollections.observableArrayList();
    private List<Person> funcList = null;
    private CourseView curC;

    @FXML
    private Label curCourse;

    @FXML
    private TextField find;

    @FXML
    private TableView<PersonInfo> table;

    @FXML
    private TableColumn<PersonInfo, String> idColumn;

    @FXML
    private TableColumn<PersonInfo, String> nameColumn;

    @FXML
    private TableColumn<PersonInfo, Date> birthdayColumn;

    @FXML
    private TableColumn<PersonInfo, String> genderColumn;

    public void setCurC(CourseView temp) {
        curC = temp;
        curCourse.setText("Học phần: " +curC.getSubjectName() + " - " + curC.getRoom() + " - " + curC.getDay() + " - [" + curC.getSession() + "]");
        updateStudentListByTable();
    }

    @FXML
    void findStudent(MouseEvent event) {
        if (!find.getText().isEmpty()) {
            String idFind = find.getText();
            studentList.clear();
            funcList = PersonDAO.getAllStudentRegistCourse(curC.getCourseId());
            for (Person ob : funcList) {
                if (ob.getId().contains(idFind))
                    studentList.add(new PersonInfo(ob));
            }

            idColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("name"));
            birthdayColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, Date>("birthday"));
            genderColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("gender"));

            table.setItems(studentList);
        }
    }

    @FXML
    void updateStudentListByTable() {
        studentList.clear();
        funcList = PersonDAO.getAllStudentRegistCourse(curC.getCourseId());
        for (Person ob : funcList)
            studentList.add(new PersonInfo(ob));

        idColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("name"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, Date>("birthday"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("gender"));

        table.setItems(studentList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("name"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, Date>("birthday"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("gender"));

        table.setItems(studentList);
    }
}
