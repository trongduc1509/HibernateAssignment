package sample;

import hibernate.DAO.PersonDAO;
import hibernate.DAO.SubjectDAO;
import hibernate.POJO.Subject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SubjectInfoController implements Initializable {
    private Subject curSubj;
    private boolean isUpdate;

    @FXML
    private Label title;

    @FXML
    private TextField idP;

    @FXML
    private TextField nameP;

    @FXML
    private TextField creditsP;

    @FXML
    private Button btn;

    @FXML
    private Label status;

    @FXML
    void submit(MouseEvent event) {
        if (isUpdate) {
            if (nameP.getText().isEmpty() || creditsP.getText().isEmpty())
                status.setText("Chú ý điền đầy đủ các thông tin!!!");
            else {
                curSubj.setName(nameP.getText());
                curSubj.setCredits(Integer.parseInt(creditsP.getText()));

                SubjectDAO.update(curSubj);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }
        }
        else {
            if (idP.getText().isEmpty() || nameP.getText().isEmpty() || creditsP.getText().isEmpty())
                status.setText("Chú ý điền đầy đủ các thông tin!!!");
            else {
                if (SubjectDAO.getDeteminedSubject(idP.getText()) == null) {
                    Subject newSubj = new Subject();
                    newSubj.setId(idP.getText());
                    newSubj.setName(nameP.getText());
                    newSubj.setCredits(Integer.parseInt(creditsP.getText()));

                    SubjectDAO.add(newSubj);
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.close();
                }
                else
                    status.setText("Mã môn học này đã tồn tại!!!");
            }
        }
    }

    public void loadSubjectInfo(MouseEvent event, Stage stage, Scene scene, Subject subj, Label label, boolean update) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SubjectInfo.fxml"));
        loader.load();
        SubjectInfoController sIC = loader.getController();
        sIC.isUpdate = update;
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
        stage.initOwner(label.getScene().getWindow());
        stage.setResizable(Boolean.FALSE);
        if (update) {
            sIC.setCurSubj(subj);
            sIC.idP.setDisable(true);
            sIC.title.setText("THAY ĐỔI THÔNG TIN MÔN HỌC");
            sIC.btn.setText("XÁC NHẬN THAY ĐỔI");
        }
        else {
            sIC.title.setText("THÊM MÔN HỌC");
            sIC.btn.setText("THÊM");
        }
        stage.showAndWait();
    }

    public void setCurSubj(Subject temp) {
        curSubj = temp;
        idP.setText(curSubj.getId());
        nameP.setText(curSubj.getName());
        creditsP.setText(String.valueOf(curSubj.getCredits()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
