package sample;

import hibernate.DAO.ClazzDAO;
import hibernate.DAO.ClazzInfoDAO;
import hibernate.DAO.PersonDAO;
import hibernate.POJO.Clazz;
import hibernate.POJO.ClazzInfo;
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

public class AddingStudentAccountController {
    private Clazz curClass;

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

    public void setCurClass(Clazz temp) {
        curClass = temp;
    }

    @FXML
    void addStudentAccount(MouseEvent event) {
        if (idP.getText().isEmpty() || nameP.getText().isEmpty() || dobP.getValue() == null || (!male.isSelected() && !female.isSelected()))
            status.setText("Chú ý điền đầy đủ các thông tin!!!");
        else {
            if (PersonDAO.searchSingleStudentById(idP.getText()) == null) {
                Person newAcc = new Person();
                newAcc.setId(idP.getText());
                newAcc.setName(nameP.getText());
                newAcc.setBirthday(Date.valueOf(dobP.getValue()));
                if (male.isSelected())
                    newAcc.setGender(1);
                else
                    newAcc.setGender(0);
                newAcc.setRole("SV");
                newAcc.setUsername(idP.getText());
                newAcc.setPassword(idP.getText());
                PersonDAO.add(newAcc);

                ClazzInfo cI = new ClazzInfo();
                cI.setClassId(curClass.getId());
                cI.setStudentId(newAcc.getId());
                ClazzInfoDAO.add(cI);

                curClass.setTotal(curClass.getTotal() + 1);
                curClass.setMale((newAcc.getGender() == 1) ? curClass.getMale() + 1 : curClass.getMale());
                curClass.setFemale(curClass.getTotal() - curClass.getMale());
                ClazzDAO.update(curClass);

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }
            else
                status.setText("Mã sinh viên này đã tồn tại!!!");
        }
    }

}
