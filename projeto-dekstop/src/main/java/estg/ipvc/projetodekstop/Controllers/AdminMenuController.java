package estg.ipvc.projetodekstop.Controllers;

import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class AdminMenuController {

    @FXML
    void back(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Voltar");
        alert.setHeaderText("Tem a certeza que deseja sair?");
        alert.showAndWait();
        if(alert.getResult().getText().equals("OK"))
            LoadFXML.getInstance().loadResource("login.fxml", "Gestão de Produção de Cereais", event);
    }

    @FXML
    void listManagers(ActionEvent event) {

    }

    @FXML
    void manageData(ActionEvent event) {

    }

    @FXML
    void profile(ActionEvent event) {
        LoadFXML.getInstance().loadResource("profile.fxml", "Perfil", event);
    }

    @FXML
    void registerAdmin(ActionEvent event) {
        LoadFXML.getInstance().loadResource("registaradmin.fxml", "Registar Admin", event);
    }

    @FXML
    void registerUser(ActionEvent event) {

    }

    @FXML
    void stats(ActionEvent event) {

    }

}
