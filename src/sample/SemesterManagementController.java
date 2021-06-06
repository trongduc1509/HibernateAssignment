package sample;

import hibernate.DAO.CrsDAO;
import hibernate.DAO.SemesterDAO;
import hibernate.POJO.Crs;
import hibernate.POJO.Person;
import hibernate.POJO.Semester;
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

public class SemesterManagementController implements Initializable {
    @FXML
    private Label curUser;

    @FXML
    private Label curSemester;

    @FXML
    private TextField find;

    @FXML
    private TableView<Semester> table;

    @FXML
    private TableColumn<Semester, String> nameColumn;

    @FXML
    private TableColumn<Semester, Integer> yearColumn;

    @FXML
    private TableColumn<Semester, Date> startColumn;

    @FXML
    private TableColumn<Semester, Date> endColumn;

    private Person curAcc;
    private Semester curSem;

    private Stage stage;
    private Scene scene;

    private ObservableList<Semester> semList = FXCollections.observableArrayList();
    private List<Semester> funcList = null;

    public void setCurUser(Person temp) {
        curAcc = temp;
        curUser.setText(curAcc.getName());
    }

    public void setCurSemester(Semester temp) {
        curSem = temp;
        if (temp != null)
            curSemester.setText("Học kì hiện tại: " + curSem.getName() + " - " + curSem.getYear());
    }

    @FXML
    void addSemester(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SemesterInfo.fxml"));
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
        updateSemesterListByTable();
    }

    @FXML
    void delSemester(MouseEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Semester ob = table.getSelectionModel().getSelectedItem();
            List<Crs> checkList = CrsDAO.getAllCrsBySemester(ob.getId());
            if (curSem == null) {
                if (checkList.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("!!!Xóa học kì!!!");
                    alert.setContentText("Bạn có chắc chắn muốn xóa học kì [" + ob.getName() + " - " + ob.getYear() + "]?");
                    if (alert.showAndWait().get() == ButtonType.OK) {
                        SemesterDAO.delete(ob);
                        updateSemesterListByTable();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("!!!Lỗi xóa học kì!!!");
                    alert.setContentText("Tồn tại kì đăng kí học phần thuộc học kì này!!!");
                    alert.showAndWait();
                }
            }
            else {
                if (curSem.getName().compareTo(ob.getName()) == 0 && curSem.getYear().equals(ob.getYear())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("!!!Lỗi xóa học kì!!!");
                    alert.setContentText("Không được xóa học kì hiện tại!!!");
                    alert.showAndWait();
                }
                else {
                    if (checkList.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("!!!Xóa học kì!!!");
                        alert.setContentText("Bạn có chắc chắn muốn xóa học kì [" + ob.getName() + " - " + ob.getYear() + "]?");
                        if (alert.showAndWait().get() == ButtonType.OK) {
                            SemesterDAO.delete(ob);
                            updateSemesterListByTable();
                        }
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("!!!Lỗi xóa học kì!!!");
                        alert.setContentText("Tồn tại kì đăng kí học phần thuộc học kì này!!!");
                        alert.showAndWait();
                    }
                }
            }
        }
    }

    @FXML
    void findYear(MouseEvent event) {
        if (!find.getText().isEmpty()) {
            semList.clear();
            funcList = SemesterDAO.getSemesterByYear(Integer.parseInt(find.getText()));
            semList.addAll(funcList);

            nameColumn.setCellValueFactory(new PropertyValueFactory<Semester, String>("name"));
            yearColumn.setCellValueFactory(new PropertyValueFactory<Semester, Integer>("year"));
            startColumn.setCellValueFactory(new PropertyValueFactory<Semester, Date>("start"));
            endColumn.setCellValueFactory(new PropertyValueFactory<Semester, Date>("end"));

            table.setItems(semList);
        }
    }

    @FXML
    void getBack(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherScreen.fxml"));
        loader.load();
        TeacherScreenController tSC = loader.getController();
        tSC.setCurUser(curAcc);
        tSC.setCurSemester(curSem);
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
    void setCurSemForFunc(MouseEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Semester sem =table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("!!!SET HỌC KÌ HIỆN TẠI!!!");
            alert.setContentText("Bạn có chắc chắn muốn set " + sem.getName() + " - " + sem.getYear() + " làm học kì hiện tại??");
            if (alert.showAndWait().get() == ButtonType.OK)
                setCurSemester(sem);
        }
    }

    @FXML
    void updateSemesterListByTable() {
        semList.clear();
        funcList = SemesterDAO.getAllSemester();
        semList.addAll(funcList);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Semester, String>("name"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<Semester, Integer>("year"));
        startColumn.setCellValueFactory(new PropertyValueFactory<Semester, Date>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<Semester, Date>("end"));

        table.setItems(semList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateSemesterListByTable();
    }
}
