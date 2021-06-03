package sample;

import hibernate.DAO.SemesterDAO;
import hibernate.POJO.Semester;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class SemesterInfoController implements Initializable {
    @FXML
    private ComboBox<String> semN;

    @FXML
    private TextField yearP;

    @FXML
    private DatePicker startP;

    @FXML
    private DatePicker endP;

    @FXML
    private Label status;

    @FXML
    void addSemester(MouseEvent event) {
        if (semN.getSelectionModel().getSelectedItem() == null || yearP.getText().isEmpty() || startP.getValue() == null || endP.getValue() == null)
            status.setText("Chú ý điền đầy đủ các thông tin!!!");
        else {
            if (SemesterDAO.getDeterminedSemester(semN.getSelectionModel().getSelectedItem().toString(), Integer.parseInt(yearP.getText())) == null) {
                Semester newSem = new Semester();
                newSem.setName(semN.getSelectionModel().getSelectedItem());
                newSem.setYear(Integer.parseInt(yearP.getText()));
                newSem.setStart(Date.valueOf(startP.getValue()));
                newSem.setEnd(Date.valueOf(endP.getValue()));

                SemesterDAO.add(newSem);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }
            else
                status.setText("Học kì:" + semN.getSelectionModel().getSelectedItem() + " - " + yearP.getText() + " đã tồn tại!!!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> nameList = FXCollections.observableArrayList("HK1", "HK2", "HK3");
        semN.setItems(nameList);
    }
}
