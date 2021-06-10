package sample;

import hibernate.DAO.SubjectDAO;
import hibernate.POJO.Clazz;
import hibernate.POJO.Person;
import hibernate.POJO.Semester;
import hibernate.POJO.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentSubjectListController implements Initializable {
    private Clazz curCl;
    private Person curAcc;
    private Semester curSem;
    private Person curStu;

    private Stage stage;
    private Scene scene;


    @FXML
    private Label curUser;

    @FXML
    private Label curSemester;

    @FXML
    private Label curUser1;

    @FXML
    private Label stuInfo;

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

    public void setCurSemester(Semester temp) {
        curSem = temp;
        if (temp != null)
            curSemester.setText("Học kì hiện tại: " + curSem.getName() + " - " + curSem.getYear());
    }

    public void setCurClass(Clazz temp) {
        this.curCl = temp;
    }

    public void setCurStu(Person temp) {
        this.curStu = temp;
        stuInfo.setText("SV: " + curStu.getId() + " - " + curStu.getName());
        updateSubjectListByTable();
    }

    @FXML
    void findSubject(MouseEvent event) {
        if (!find.getText().isEmpty()) {
            String idFind = find.getText();
            subjectList.clear();
            List<Subject> tempList = new ArrayList<>();
            for (Subject item : funcList) {
                if (item.getId().contains(idFind))
                    tempList.add(item);
            }
            subjectList.addAll(tempList);

            idColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("name"));
            creditsColumn.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("credits"));

            table.setItems(subjectList);
        }
    }

    @FXML
    void getBack(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClassInfo.fxml"));
        loader.load();
        ClassInfoController cIC = loader.getController();
        cIC.setCurUser(curAcc);
        cIC.setCurSemester(curSem);
        cIC.setCurClass(curCl);
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
    void updateSubjectListByTable() {
        subjectList.clear();
        funcList = SubjectDAO.getAllSubjectOfStudent(curStu.getId());
        subjectList.addAll(funcList);

        idColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("name"));
        creditsColumn.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("credits"));

        table.setItems(subjectList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("name"));
        creditsColumn.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("credits"));

        table.setItems(subjectList);
    }
}
