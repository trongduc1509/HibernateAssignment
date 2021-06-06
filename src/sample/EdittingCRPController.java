package sample;

import hibernate.DAO.CrsDAO;
import hibernate.POJO.Crs;
import hibernate.POJO.CrsView;
import hibernate.POJO.Semester;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Date;

public class EdittingCRPController {
    private Semester curSem;
    private CrsView curCrsV;
    private Crs curCrs;

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

    public void setCurrent(Semester temp, CrsView ob) {
        curSem = temp;
        nameP.setText(curSem.getName());
        nameP.setDisable(true);
        yearP.setText(curSem.getYear().toString());
        yearP.setDisable(true);
        curCrsV = ob;
        startP.setValue(curCrsV.getStart().toLocalDate());
        endP.setValue(curCrsV.getEnd().toLocalDate());
    }

    @FXML
    void editCRP(MouseEvent event) {
        if (startP.getValue() == null || endP.getValue() == null)
            status.setText("Chú ý điền đầy đủ các thông tin!!!");
        else {
            Integer curId = curCrsV.getId();
            curCrs = new Crs();
            curCrs.setId(curId);
            curCrs.setStart(Date.valueOf(startP.getValue()));
            curCrs.setEnd(Date.valueOf(endP.getValue()));
            curCrs.setSemesterId(curSem.getId());

            CrsDAO.update(curCrs);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

}
