package sample;

import hibernate.DAO.ClazzDAO;
import hibernate.DAO.ClazzInfoDAO;
import hibernate.DAO.PersonDAO;
import hibernate.POJO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ClassInfoController implements Initializable {
    private Clazz curClass;
    private Person curAcc;
    private Semester curSem;

    private Stage stage;
    private Scene scene;

    @FXML
    private Label curUser;

    @FXML
    private Label curSemester;

    @FXML
    private Label curClassroom;

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

    @FXML
    private TableColumn<PersonInfo, String> usernameColumn;

    @FXML
    private TableColumn<PersonInfo, String> passwordColumn;

    private ObservableList<PersonInfo> studentList = FXCollections.observableArrayList();
    private List<Person> funcList = null;

    public void setCurUser(Person cur) {
        curAcc = cur;
        curUser.setText(curAcc.getName());
    }

    public void setCurSemester(Semester temp) {
        curSem = temp;
        if (temp != null)
            curSemester.setText("Học kì hiện tại: " + curSem.getName() + " - " + curSem.getYear());
    }

    public void setCurClass(Clazz temp) {
        curClass = temp;
        curClassroom.setText("LỚP: " + curClass.getId());
        updateStudentListByTable();
    }

    @FXML
    void addStudent(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddingStudentAccount.fxml"));
        loader.load();
        AddingStudentAccountController aSAC = loader.getController();
        aSAC.setCurClass(curClass);
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
        updateStudentListByTable();
    }

    @FXML
    void subjectRegisted(MouseEvent event) throws Exception {
        if (table.getSelectionModel().getSelectedItem() != null) {
            PersonInfo ob = table.getSelectionModel().getSelectedItem();
            Person curStu = PersonDAO.searchSingleStudentById(ob.getId());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentSubjectList.fxml"));
            loader.load();
            StudentSubjectListController sSLC = loader.getController();
            sSLC.setCurUser(curAcc);
            sSLC.setCurSemester(curSem);
            sSLC.setCurClass(curClass);
            sSLC.setCurStu(curStu);
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
    void delStudent(MouseEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            PersonInfo student = table.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("!!!Xóa sinh viên khỏi lớp học!!!");
            alert.setContentText("Bạn có chắc chắn muốn xóa sinh viên " + student.getId() + " khỏi lớp " + curClass.getId() + " không?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                ClazzInfo delOb = new ClazzInfo();
                List<ClazzInfo> stuInClazz = ClazzInfoDAO.getAllInfoByClazz(curClass.getId());
                for (ClazzInfo temp : stuInClazz) {
                    if (temp.getStudentId().compareTo(student.getId()) == 0) {
                        delOb = temp;
                        break;
                    }
                }
                ClazzInfoDAO.delete(delOb);

                curClass.setTotal(curClass.getTotal() - 1);
                if (student.getGender().compareTo("Nam") == 0)
                    curClass.setMale(curClass.getMale() - 1);
                else
                    curClass.setFemale(curClass.getFemale() - 1);
                ClazzDAO.update(curClass);

                PersonDAO.delete(PersonDAO.searchSingleStudentById(student.getId()));

                updateStudentListByTable();
            }
        }
    }

    @FXML
    void editStudent(MouseEvent event) throws Exception {
        if (table.getSelectionModel().getSelectedItem() != null) {
            PersonInfo ob = table.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StuInClassEdit.fxml"));
            loader.load();
            StuInClassEditController sICEC = loader.getController();
            sICEC.setCurrent(PersonDAO.searchSingleStudentById(ob.getId()), curClass);
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
            updateStudentListByTable();
        }
    }

    @FXML
    void findStudent(MouseEvent event) {
        if (!find.getText().isEmpty()) {
            studentList.clear();
            funcList = PersonDAO.searchStuInClass(curClass.getId(), find.getText());
            for (Person temp : funcList)
                studentList.add(new PersonInfo(temp));

            idColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("name"));
            birthdayColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, Date>("birthday"));
            genderColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("gender"));
            usernameColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("username"));
            passwordColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("password"));

            table.setItems(studentList);
        }
    }

    @FXML
    void getBack(MouseEvent event) throws Exception {
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
    void logOut(MouseEvent event) throws Exception {
        LoginController lC = new LoginController();
        lC.loadLogOut(event, stage, scene);
    }

    @FXML
    void resetStudentInfo(MouseEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            PersonInfo ob = table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("!!!Reset mật khẩu tài khoản sinh viên!!!");
            alert.setContentText("Bạn có chắc chắn muốn đặt lại mật khẩu tài khoản sinh viên " + ob.getId()+ "?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Person item = PersonDAO.searchSingleStudentById(ob.getId());
                item.setPassword(item.getId());
                PersonDAO.update(item);
                updateStudentListByTable();
            }
        }
    }

    @FXML
    void updateStudentListByTable() {
        studentList.clear();
        funcList = PersonDAO.getAllStuInClass(curClass.getId());
        for (Person temp : funcList)
            studentList.add(new PersonInfo(temp));

        idColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("name"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, Date>("birthday"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("gender"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("password"));

        table.setItems(studentList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("name"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, Date>("birthday"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("gender"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("password"));

        table.setItems(studentList);
    }
}
