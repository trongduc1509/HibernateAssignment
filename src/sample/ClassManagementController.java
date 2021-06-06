package sample;

import hibernate.DAO.ClazzDAO;
import hibernate.POJO.Clazz;
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
import java.util.List;
import java.util.ResourceBundle;

public class ClassManagementController implements Initializable {
    private Person curAcc;
    private Semester curSem;
    private Stage stage;
    private Scene scene;
    private ObservableList<Clazz> clazzList = FXCollections.observableArrayList();
    private List<Clazz> funcList = null;

    @FXML
    private Label curUser;

    @FXML
    private Label curSemester;

    @FXML
    private TextField find;

    @FXML
    private TableView<Clazz> table;

    @FXML
    private TableColumn<Clazz, String> idColumn;

    @FXML
    private TableColumn<Clazz, Integer> totalColumn;

    @FXML
    private TableColumn<Clazz, Integer> maleColumn;

    @FXML
    private TableColumn<Clazz, Integer> femaleColumn;

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
    void addClazz(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddingClassroom.fxml"));
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
        updateClassListByTable();
    }

    @FXML
    void delClazz(MouseEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Clazz ob = table.getSelectionModel().getSelectedItem();
            if (ob.getTotal() != 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("!!!Lỗi xóa lớp học!!!");
                alert.setContentText("Không thể xóa lớp học hiện đang có sinh viên!!!");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("!!!Xác nhận xóa lớp học!!!");
                alert.setContentText("Bạn có chắc chắn muốn xóa lớp học " + ob.getId() + " không?");
                if (alert.showAndWait().get() == ButtonType.OK) {
                    ClazzDAO.delete(ob);
                    updateClassListByTable();
                }
            }
        }
    }

    @FXML
    void findClass(MouseEvent event) {
        if (!find.getText().isEmpty()) {
            clazzList.clear();
            funcList = ClazzDAO.searchClassById(find.getText());
            clazzList.addAll(funcList);

            idColumn.setCellValueFactory(new PropertyValueFactory<Clazz, String>("id"));
            totalColumn.setCellValueFactory(new PropertyValueFactory<Clazz, Integer>("total"));
            maleColumn.setCellValueFactory(new PropertyValueFactory<Clazz, Integer>("male"));
            femaleColumn.setCellValueFactory(new PropertyValueFactory<Clazz, Integer>("female"));

            table.setItems(clazzList);
        }
    }

    @FXML
    void getBack(MouseEvent event) throws Exception {
        TeacherScreenController tSC = new TeacherScreenController();
        tSC.loadTeacherScreen(event, stage, scene, curAcc, curSem);
    }

    @FXML
    void logOut(MouseEvent event) throws Exception {
        LoginController lC = new LoginController();
        lC.loadLogOut(event, stage, scene);
    }

    @FXML
    void seeClassDetail(MouseEvent event) throws Exception {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Clazz ob = table.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClassInfo.fxml"));
            loader.load();
            ClassInfoController cIC = loader.getController();
            cIC.setCurUser(curAcc);
            cIC.setCurSemester(curSem);
            cIC.setCurClass(ob);
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
    void updateClassListByTable() {
        clazzList.clear();
        funcList = ClazzDAO.getAllClass();
        clazzList.addAll(funcList);

        idColumn.setCellValueFactory(new PropertyValueFactory<Clazz, String>("id"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<Clazz, Integer>("total"));
        maleColumn.setCellValueFactory(new PropertyValueFactory<Clazz, Integer>("male"));
        femaleColumn.setCellValueFactory(new PropertyValueFactory<Clazz, Integer>("female"));

        table.setItems(clazzList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateClassListByTable();
    }
}
