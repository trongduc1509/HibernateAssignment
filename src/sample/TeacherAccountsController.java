package sample;

import hibernate.DAO.PersonDAO;
import hibernate.POJO.Person;
import hibernate.POJO.PersonInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class TeacherAccountsController implements Initializable {
    private Person curAcc;
    private Stage stage;
    private Scene scene;

    @FXML
    private Label curUser;

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

    private ObservableList<PersonInfo> teacherList = FXCollections.observableArrayList();
    private List<Person> funcList = null;

    public void setCurUser(Person cur) {
        curAcc = cur;
        curUser.setText(curAcc.getName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTeacherListByTable();
    }

    @FXML
    void addTeacher(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddingTeacherAccount.fxml"));
        loader.load();
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
        updateTeacherListByTable();
    }

    @FXML
    void delTeacher(MouseEvent event) throws Exception {
        if (table.getSelectionModel().getSelectedItem() != null) {
            PersonInfo ob = table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("!!!Xóa tài khoản giáo vụ!!!");
            alert.setContentText("Bạn có chắc chắn muốn xóa tài khoản giáo vụ " + ob.getId()+ "?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Person item = PersonDAO.searchSingleTeacherById(ob.getId());
                PersonDAO.delete(item);
                updateTeacherListByTable();
                if (ob.getId().compareTo(curAcc.getId()) == 0)
                    logOut(event);
            }
        }
    }

    @FXML
    void editTeacher(MouseEvent event) throws Exception {
        if (table.getSelectionModel().getSelectedItem() != null) {
            PersonInfo ob = table.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserAccount.fxml"));
            loader.load();
            UserAccountController uAC = loader.getController();
            uAC.setUserAcc(PersonDAO.searchSingleTeacherById(ob.getId()));
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
            updateTeacherListByTable();
            if (ob.getId().compareTo(curAcc.getId()) == 0)
                setCurUser(PersonDAO.searchSingleTeacherById(ob.getId()));
        }
    }

    @FXML
    void findTeacher(MouseEvent event) {
        if (!find.getText().isEmpty()) {
            teacherList.clear();
            funcList = PersonDAO.searchTeacherById(find.getText());
            for (Person temp : funcList)
                teacherList.add(new PersonInfo(temp));

            idColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("name"));
            birthdayColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, Date>("birthday"));
            genderColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("gender"));
            usernameColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("username"));
            passwordColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("password"));

            table.setItems(teacherList);
        }

    }

    @FXML
    public void updateTeacherListByTable() {
        teacherList.clear();
        funcList = PersonDAO.getAllTeacher();
        for (Person temp : funcList)
            teacherList.add(new PersonInfo(temp));

        idColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("name"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, Date>("birthday"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("gender"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<PersonInfo, String>("password"));

        table.setItems(teacherList);
    }

    @FXML
    void getBack(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherScreen.fxml"));
        loader.load();
        TeacherScreenController tSC = loader.getController();
        tSC.setCurUser(curAcc);
        tSC.setCurSemester(null);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PortalLogin.fxml"));
        loader.load();
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
    void resetTeacherInfo(MouseEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            PersonInfo ob = table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("!!!Reset mật khẩu tài khoản giáo vụ!!!");
            alert.setContentText("Bạn có chắc chắn muốn đặt lại mật khẩu tài khoản giáo vụ " + ob.getId()+ "?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Person item = PersonDAO.searchSingleTeacherById(ob.getId());
                item.setPassword(item.getId());
                PersonDAO.update(item);
                updateTeacherListByTable();
            }
        }
    }
}
