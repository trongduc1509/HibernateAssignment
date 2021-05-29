package sample;

import hibernate.POJO.Person;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UserAccountController {
    private Person curAcc;

    @FXML
    private TextField idP;

    @FXML
    private TextField nameP;

    @FXML
    private TextField dobP;

    @FXML
    private TextField genderP;

    @FXML
    private TextField userNP;

    @FXML
    private TextField userPP;

    public void setUserAcc(Person temp) {
        curAcc = temp;
        idP.setText(curAcc.getId());
        nameP.setText(curAcc.getName());
        dobP.setText(curAcc.getBirthday().toString());
        genderP.setText((curAcc.getGender() == 1) ? "Nam" : "Nữ");
        userNP.setText(curAcc.getUsername());
        userPP.setText(curAcc.getPassword());
    }
}
