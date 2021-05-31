package sample;

import hibernate.POJO.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class UserAccountController {
    private Person curAcc;
    private Stage stage;
    private Scene scene;

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

    @FXML
    void editUserAccount(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserAccountEdit.fxml"));
        loader.load();
        UserAccountEditController uAEC = loader.getController();
        uAEC.setUserAcc(curAcc);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Image icon = new Image("OIP.png");
        stage.getIcons().add(icon);
        stage.setTitle("HCMUS Portal");
        scene = new Scene(loader.getRoot());
        stage.setScene(scene);
        stage.setResizable(Boolean.FALSE);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
    }
}
