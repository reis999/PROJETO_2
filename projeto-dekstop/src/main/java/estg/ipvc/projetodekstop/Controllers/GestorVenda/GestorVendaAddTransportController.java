package estg.ipvc.projetodekstop.Controllers.GestorVenda;

import estg.ipvc.projeto.data.BLL.TransportBLL;
import estg.ipvc.projeto.data.Entity.Transporte;
import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;

public class GestorVendaAddTransportController {

    @FXML
    private TextField cost;

    @FXML
    private TextField deliveryTime;

    @FXML
    private TextField type;

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("gestorvendamenu.fxml", "Menu Gestor de Vendas", event);
    }

    @FXML
    void persist(ActionEvent event) {
        String costString = cost.getText();


        if (cost.getText().isEmpty() || deliveryTime.getText().isEmpty() || type.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Preencha todos os campos");
            alert.showAndWait();
            return;
        }

        if (!costString.matches("-?\\d+(\\.\\d+)?")){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Custo inválido");
            alert.showAndWait();
            return;
        }

        Transporte tp = new Transporte();
        tp.setCusto(BigDecimal.valueOf(Double.parseDouble(cost.getText())));
        tp.setTempoEntrega(deliveryTime.getText());
        tp.setTipo(type.getText());
        try {
            TransportBLL.create(tp);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao adicionar transporte");
            alert.showAndWait();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Transporte adicionado com sucesso");
        alert.showAndWait();
        LoadFXML.getInstance().loadResource("gestorvendamenu.fxml", "Menu Gestor de Vendas", event);
    }

}
