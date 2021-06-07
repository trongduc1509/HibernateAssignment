package sample;

import hibernate.DAO.ClazzDAO;
import hibernate.DAO.SubjectDAO;
import hibernate.POJO.Clazz;
import hibernate.POJO.Semester;
import hibernate.POJO.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

public class AddingClassroomController {

    @FXML
    private TextField nameP;

    @FXML
    private Label status;

    @FXML
    void submit(MouseEvent event) {
        if (nameP.getText().isEmpty())
            status.setText("Chú ý điền đầy đủ các thông tin!!!");
        else {
            String newId = nameP.getText();
            if (ClazzDAO.getDeterminedClass(newId) == null) {
                Clazz newCl = new Clazz();
                newCl.setId(newId);
                newCl.setTotal(0);
                newCl.setMale(0);
                newCl.setFemale(0);

                ClazzDAO.add(newCl);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }
            else
                status.setText("Lớp học " + newId + " đã tồn tại!!!");
        }
    }

}
