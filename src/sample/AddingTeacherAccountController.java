package sample;

import hibernate.DAO.PersonDAO;
import hibernate.POJO.Person;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Date;

public class AddingTeacherAccountController {

    @FXML
    private TextField idP;

    @FXML
    private TextField nameP;

    @FXML
    private DatePicker dobP;

    @FXML
    private RadioButton male;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton female;

    @FXML
    private TextField userNP;

    @FXML
    private TextField userPP;

    @FXML
    private Label status;

    @FXML
    void addTeacherAccount(MouseEvent event) {
        if (idP.getText().isEmpty() || nameP.getText().isEmpty() || dobP.getValue() == null || (!male.isSelected() && !female.isSelected()))
            status.setText("Chú ý điền đầy đủ các thông tin!!!");
        else {
            if (PersonDAO.searchSingleTeacherById(idP.getText()) == null) {
                Person newAcc = new Person();
                newAcc.setId(idP.getText());
                newAcc.setName(nameP.getText());
                newAcc.setBirthday(Date.valueOf(dobP.getValue()));
                if (male.isSelected())
                    newAcc.setGender(1);
                else
                    newAcc.setGender(0);
                newAcc.setRole("GV");
                newAcc.setUsername(idP.getText());
                newAcc.setPassword(idP.getText());

                PersonDAO.add(newAcc);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }
            else
                status.setText("Mã tài khoản giáo vụ này đã tồn tại!!!");
        }
    }

}
