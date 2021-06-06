package sample;

import hibernate.DAO.CrsDAO;
import hibernate.DAO.PersonDAO;
import hibernate.POJO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
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

public class CourseRegistrationPeriodController implements Initializable {
    private Person curAcc;
    private Semester curSem;

    private Stage stage;
    private Scene scene;

    private ObservableList<CrsView> crpList = FXCollections.observableArrayList();
    private List<Crs> funcList = null;

    @FXML
    private Label curUser;

    @FXML
    private Label curSemester;

    @FXML
    private Label curUser1;

    @FXML
    private TextField find;

    @FXML
    private TableView<CrsView> table;

    @FXML
    private TableColumn<CrsView, String> nameColumn;

    @FXML
    private TableColumn<CrsView, Integer> yearColumn;

    @FXML
    private TableColumn<CrsView, Date> startColumn;

    @FXML
    private TableColumn<CrsView, Date> endColumn;

    public void setCurUser(Person temp) {
        curAcc = temp;
        curUser.setText(curAcc.getName());
    }

    public void setCurSemester(Semester temp) {
        curSem = temp;
        curSemester.setText("Học kì hiện tại: " + curSem.getName() + " - " + curSem.getYear());
        updateCRPListByTable();
    }

    @FXML
    void addCRP(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddingCRP.fxml"));
        loader.load();
        AddingCRPController aC = loader.getController();
        aC.setCurSem(curSem);
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
        updateCRPListByTable();
    }

    @FXML
    void delCRP(MouseEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            CrsView ob = table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("!!!XÓA KÌ ĐĂNG KÍ HỌC PHẦN!!!");
            alert.setContentText("Bạn có chắc chắn muốn xóa kì đăng kí học phần: " + ob.getSemName() + " - " + ob.getSemYear() + " [" + ob.getStart().toString() + " - " + ob.getEnd().toString()+ "] không?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Crs delOb = new Crs();
                delOb.setId(ob.getId());
                delOb.setStart(ob.getStart());
                delOb.setEnd(ob.getEnd());
                delOb.setSemesterId(curSem.getId());
                CrsDAO.delete(delOb);
                updateCRPListByTable();
            }
        }
    }

    @FXML
    void editCRP(MouseEvent event) throws Exception {
        if (table.getSelectionModel().getSelectedItem() != null) {
            CrsView ob = table.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EdittingCRP.fxml"));
            loader.load();
            EdittingCRPController eC = loader.getController();
            eC.setCurrent(curSem, ob);
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
            updateCRPListByTable();
        }
    }

    @FXML
    void findCRP(MouseEvent event) {
        if (!find.getText().isEmpty()) {
            crpList.clear();
            funcList = CrsDAO.searchAllCrsBySemName(find.getText());
            for (Crs temp : funcList)
                crpList.add(new CrsView(temp.getId(), temp.getStart(), temp.getEnd(), curSem.getName(), curSem.getYear()));

            nameColumn.setCellValueFactory(new PropertyValueFactory<CrsView, String>("semName"));
            yearColumn.setCellValueFactory(new PropertyValueFactory<CrsView, Integer>("semYear"));
            startColumn.setCellValueFactory(new PropertyValueFactory<CrsView, Date>("start"));
            endColumn.setCellValueFactory(new PropertyValueFactory<CrsView, Date>("end"));

            table.setItems(crpList);
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
    void updateCRPListByTable() {
        crpList.clear();
        funcList = CrsDAO.getAllCrsBySemester(curSem.getId());
        for (Crs temp : funcList)
            crpList.add(new CrsView(temp.getId(), temp.getStart(), temp.getEnd(), curSem.getName(), curSem.getYear()));

        nameColumn.setCellValueFactory(new PropertyValueFactory<CrsView, String>("semName"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<CrsView, Integer>("semYear"));
        startColumn.setCellValueFactory(new PropertyValueFactory<CrsView, Date>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<CrsView, Date>("end"));

        table.setItems(crpList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<CrsView, String>("semName"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<CrsView, Integer>("semYear"));
        startColumn.setCellValueFactory(new PropertyValueFactory<CrsView, Date>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<CrsView, Date>("end"));

        table.setItems(crpList);
    }
}
