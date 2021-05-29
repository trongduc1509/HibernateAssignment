package sample;

import hibernate.POJO.Person;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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
}
