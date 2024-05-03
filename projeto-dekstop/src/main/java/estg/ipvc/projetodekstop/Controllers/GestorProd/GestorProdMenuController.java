package estg.ipvc.projetodekstop.Controllers.GestorProd;

import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class GestorProdMenuController {

    @FXML
    void ManageCerealType(ActionEvent event) {
        LoadFXML.getInstance().loadResource("gestorprodmanagecerealtype.fxml", "Gerir Tipos de Cereais", event);
    }

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
    void listBatches(ActionEvent event) {
        LoadFXML.getInstance().loadResource("gestorprodlistbatches.fxml", "Lista de Lotes", event);
    }

    @FXML
    void manageBatches(ActionEvent event) {
        LoadFXML.getInstance().loadResource("gestorprodmanagebatches.fxml", "Gerir Lotes", event);
    }

    @FXML
    void manageCultivation(ActionEvent event) {
        LoadFXML.getInstance().loadResource("gestorprodmanagecultivation.fxml", "Gerir Cultivo", event);
    }

    @FXML
    void profile(ActionEvent event) {
        LoadFXML.getInstance().loadResource("profile.fxml", "Perfil", event);
    }

    @FXML
    void registerBatch(ActionEvent event) {
        LoadFXML.getInstance().loadResource("gestorprodregisterbatch.fxml", "Registar Lote", event);
    }

}
