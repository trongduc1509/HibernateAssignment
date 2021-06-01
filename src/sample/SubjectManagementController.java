package sample;

import hibernate.DAO.PersonDAO;
import hibernate.DAO.SubjectDAO;
import hibernate.POJO.Person;
import hibernate.POJO.Subject;
import javafx.beans.Observable;
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
import java.util.List;
import java.util.ResourceBundle;

public class SubjectManagementController implements Initializable {
    private Person curAcc;
    private Stage stage;
    private Scene scene;

    @FXML
    private Label curUser;

    @FXML
    private TextField find;

    @FXML
    private TableView<Subject> table;

    @FXML
    private TableColumn<Subject, String> idColumn;

    @FXML
    private TableColumn<Subject, String> nameColumn;

    @FXML
    private TableColumn<Subject, Integer> creditsColumn;

    private ObservableList<Subject> subjectList = FXCollections.observableArrayList();
    private List<Subject> funcList = null;

    public void setCurUser(Person temp) {
        this.curAcc = temp;
        curUser.setText(curAcc.getName());
    }

    @FXML
    void addSubject(MouseEvent event) throws Exception {
        SubjectInfoController sIC = new SubjectInfoController();
        sIC.loadSubjectInfo(event, stage, scene, null, curUser, false);
        updateSubjectListByTable();
    }

    @FXML
    void delSubject(MouseEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Subject ob = table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("!!!Xóa môn học!!!");
            alert.setContentText("Bạn có chắc chắn muốn xóa môn học " + ob.getId() + " - " + ob.getName() + "?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Subject item = SubjectDAO.getDeteminedSubject(ob.getId());
                SubjectDAO.delete(item);
                updateSubjectListByTable();
            }
        }
    }

    @FXML
    void editSubject(MouseEvent event) throws Exception {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Subject subj = table.getSelectionModel().getSelectedItem();
            SubjectInfoController sIC = new SubjectInfoController();
            sIC.loadSubjectInfo(event, stage, scene, subj, curUser, true);
            updateSubjectListByTable();
        }
    }

    @FXML
    void findSubject(MouseEvent event) {
        if (!find.getText().isEmpty()) {
            subjectList.clear();
            funcList = SubjectDAO.searchSubjectById(find.getText());
            for (Subject item : funcList) {
                Subject temp = new Subject();
                temp.setId(item.getId());
                temp.setName(item.getName());
                temp.setCredits(item.getCredits());
                subjectList.add(temp);
            }

            idColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("name"));
            creditsColumn.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("credits"));

            table.setItems(subjectList);
        }
    }

    @FXML
    void getBack(MouseEvent event) throws Exception {
        TeacherScreenController tSC = new TeacherScreenController();
        tSC.loadTeacherScreen(event, stage, scene, curAcc);
    }

    @FXML
    void logOut(MouseEvent event) throws Exception {
        LoginController lC = new LoginController();
        lC.loadLogOut(event, stage, scene);
    }

    @FXML
    void updateSubjectListByTable() {
        subjectList.clear();
        funcList = SubjectDAO.getAllSubject();
        for (Subject item : funcList) {
            Subject temp = new Subject();
            temp.setId(item.getId());
            temp.setName(item.getName());
            temp.setCredits(item.getCredits());
            subjectList.add(temp);
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("name"));
        creditsColumn.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("credits"));

        table.setItems(subjectList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateSubjectListByTable();
    }
}
