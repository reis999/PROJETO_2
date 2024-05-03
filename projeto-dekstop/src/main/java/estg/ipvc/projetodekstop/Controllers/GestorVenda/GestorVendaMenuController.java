package estg.ipvc.projetodekstop.Controllers.GestorVenda;

import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class GestorVendaMenuController {

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
    void insertTransport(ActionEvent event) {
        LoadFXML.getInstance().loadResource("gestorvendaaddtransporte.fxml", "Adicionar Transporte", event);
    }

    @FXML
    void listClients(ActionEvent event) {
        LoadFXML.getInstance().loadResource("gestorvendalistclients.fxml", "Lista de Clientes", event);
    }

    @FXML
    void manageSales(ActionEvent event) {
        LoadFXML.getInstance().loadResource("gestorvendamanagesales.fxml", "Gerir Vendas", event);
    }

    @FXML
    void manageTransport(ActionEvent event) {
        LoadFXML.getInstance().loadResource("gestorvendamanagetransport.fxml", "Gerir Transportes", event);
    }

    @FXML
    void profile(ActionEvent event) {
        LoadFXML.getInstance().loadResource("profile.fxml", "Perfil", event);
    }

    @FXML
    void registerSale(ActionEvent event) {
        LoadFXML.getInstance().loadResource("gestorvendaregistersale.fxml", "Registar Venda", event);
    }

}
