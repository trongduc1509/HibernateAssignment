package sample;

import hibernate.DAO.PersonDAO;
import hibernate.POJO.Person;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;

public class UserAccountEditController {
    private Person curAcc;

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

    public void setUserAcc(Person temp) {
        curAcc = temp;
        idP.setText(curAcc.getId());
        nameP.setText(curAcc.getName());
        dobP.setValue(curAcc.getBirthday().toLocalDate());
        if (curAcc.getGender() == 1)
            male.setSelected(true);
        else
            female.setSelected(true);
        userNP.setText(curAcc.getUsername());
        userPP.setText(curAcc.getPassword());
    }

    @FXML
    void submit(MouseEvent event) {
        if (nameP.getText().isEmpty() || dobP.getValue() == null || (!male.isSelected() && !female.isSelected()) || userPP.getText().isEmpty())
            status.setText("Chú ý điền đầy đủ các thông tin!!!");
        else {
            String role = curAcc.getRole();
            curAcc = new Person();
            curAcc.setId(idP.getText());
            curAcc.setName(nameP.getText());
            curAcc.setBirthday(Date.valueOf(dobP.getValue()));
            if (male.isSelected())
                curAcc.setGender(1);
            else
                curAcc.setGender(0);
            curAcc.setRole(role);
            curAcc.setUsername(userNP.getText());
            curAcc.setPassword(userPP.getText());

            PersonDAO.update(curAcc);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
