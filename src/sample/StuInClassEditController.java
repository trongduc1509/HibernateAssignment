package sample;

import hibernate.DAO.ClazzDAO;
import hibernate.DAO.PersonDAO;
import hibernate.POJO.Clazz;
import hibernate.POJO.Person;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Date;

public class StuInClassEditController {
    private Clazz curClass;
    private Person curStu;

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

    public void setCurrent(Person tempStu, Clazz tempCl) {
        curStu = tempStu;
        curClass = tempCl;
        idP.setText(curStu.getId());
        nameP.setText(curStu.getName());
        dobP.setValue(curStu.getBirthday().toLocalDate());
        if (curStu.getGender() == 1)
            male.setSelected(true);
        else
            female.setSelected(true);
        userNP.setText(curStu.getUsername());
        userPP.setText(curStu.getPassword());
        idP.setDisable(true);
        userNP.setDisable(true);
    }

    @FXML
    void submit(MouseEvent event) {
        if (nameP.getText().isEmpty() || dobP.getValue() == null || (!male.isSelected() && !female.isSelected()) || userPP.getText().isEmpty())
            status.setText("Chú ý điền đầy đủ các thông tin!!!");
        else {
            String role = curStu.getRole();
            Integer oldGender = curStu.getGender();
            curStu = new Person();
            curStu.setId(idP.getText());
            curStu.setName(nameP.getText());
            curStu.setBirthday(Date.valueOf(dobP.getValue()));
            if (male.isSelected())
                curStu.setGender(1);
            else
                curStu.setGender(0);
            curStu.setRole(role);
            curStu.setUsername(userNP.getText());
            curStu.setPassword(userPP.getText());
            PersonDAO.update(curStu);

            if (!curStu.getGender().equals(oldGender)) {
                if (curStu.getGender() == 1)
                    curClass.setMale(curClass.getMale() + 1);
                else
                    curClass.setMale(curClass.getMale() - 1);
                curClass.setFemale(curClass.getTotal() - curClass.getMale());
                ClazzDAO.update(curClass);
            }

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
