package sample;

import hibernate.DAO.CrsDAO;
import hibernate.POJO.Crs;
import hibernate.POJO.Semester;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.List;

public class AddingCRPController {
    private Semester curSem;

    @FXML
    private TextField nameP;

    @FXML
    private TextField yearP;

    @FXML
    private DatePicker startP;

    @FXML
    private DatePicker endP;

    @FXML
    private Label status;

    public void setCurSem(Semester temp) {
        curSem = temp;
        nameP.setText(curSem.getName());
        nameP.setDisable(true);
        yearP.setText(curSem.getYear().toString());
        yearP.setDisable(true);
    }

    @FXML
    void addCRP(MouseEvent event) {
        if (startP.getValue() == null || endP.getValue() == null)
            status.setText("Chú ý điền đầy đủ các thông tin!!!");
        else {
            Crs newOb = new Crs();
            newOb.setStart(Date.valueOf(startP.getValue()));
            newOb.setEnd(Date.valueOf(endP.getValue()));
            newOb.setSemesterId(curSem.getId());
            if (checkExist(newOb))
                status.setText("Kỳ đăng kí học phần này đã tồn tại!!!");
            else {
                CrsDAO.add(newOb);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            }
        }
    }

    public boolean checkExist(Crs check) {
        List<Crs> checkList = CrsDAO.getAllCrsBySemester(curSem.getId());
        for (Crs item : checkList) {
            if (item.getStart().equals(check.getStart()) && item.getEnd().equals(check.getEnd()))
                return true;
        }
        return false;
    }
}
