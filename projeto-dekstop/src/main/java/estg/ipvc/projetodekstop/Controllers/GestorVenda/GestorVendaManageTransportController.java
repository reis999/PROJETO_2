package estg.ipvc.projetodekstop.Controllers.GestorVenda;

import estg.ipvc.projeto.data.BLL.DBConnect;
import estg.ipvc.projeto.data.BLL.TransportBLL;
import estg.ipvc.projeto.data.Entity.Transporte;
import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GestorVendaManageTransportController implements Initializable {

    @FXML
    private Button cancelBtn;

    @FXML
    private Button confirmBtn;

    @FXML
    private TextField cost;

    @FXML
    private TableColumn<Transporte, Double> costCol;

    @FXML
    private Button editBtn;

    @FXML
    private Button removeBtn;

    @FXML
    private TextField time;

    @FXML
    private TableColumn<Transporte, String> timeCol;

    @FXML
    private TableView<Transporte> transportTable;

    @FXML
    private TextField type;

    @FXML
    private TableColumn<Transporte, String> typeCol;

    private final EntityManager em = DBConnect.getEntityManager();

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("gestorvendamenu.fxml", "Menu Gestor de Vendas", event);
    }

    @FXML
    void cancelEdit() {
        fieldEdit(false);
        cancelBtn.setVisible(false);
        confirmBtn.setVisible(false);
        removeBtn.setVisible(true);
        editBtn.setVisible(true);
    }

    @FXML
    void confirmEdit() {
        Transporte tp = transportTable.getSelectionModel().getSelectedItem();
        tp.setCusto(BigDecimal.valueOf(Double.parseDouble(cost.getText())));
        tp.setTempoEntrega(time.getText());
        tp.setTipo(type.getText());

        try {
            TransportBLL.update(tp);
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar transporte");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Transporte editado com sucesso");
        alert.showAndWait();
        refreshTable();
        removeBtn.setVisible(true);
        editBtn.setVisible(true);
        cancelBtn.setVisible(false);
        confirmBtn.setVisible(false);
    }

    private void refreshTable() {
        fieldEdit(false);
        cancelBtn.setVisible(false);
        confirmBtn.setVisible(false);

        transportTable.getItems().clear();
        List<Transporte> transportes = em.createQuery("SELECT t FROM Transporte t", Transporte.class).getResultList();
        for(Transporte t : transportes){
            fillTable();
            transportTable.getItems().add(t);
        }
    }

    @FXML
    void edit() {
        Transporte tp = transportTable.getSelectionModel().getSelectedItem();
        if(tp == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Selecione um transporte");
            alert.showAndWait();
        }
        else{
            fieldEdit(true);
            removeBtn.setVisible(false);
            editBtn.setVisible(false);
            cancelBtn.setVisible(true);
            confirmBtn.setVisible(true);
        }
    }

    @FXML
    void remove() {
        Transporte tp = transportTable.getSelectionModel().getSelectedItem();
        if(tp == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Selecione um transporte");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover");
            alert.setHeaderText("Tem a certeza que deseja remover o transporte?");
            alert.showAndWait();
            if(alert.getResult().getText().equals("OK")){
                TransportBLL.remove(tp);
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Transporte removido com sucesso");
        alert.showAndWait();

        transportTable.getItems().clear();
        List<Transporte> transportes = em.createQuery("SELECT t FROM Transporte t", Transporte.class).getResultList();
        for(Transporte t : transportes){
            fillTable();
            transportTable.getItems().add(t);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTable();
        transportTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillFields(newSelection);
            }
        });
    }

    private void fieldEdit(boolean b){
        cost.setEditable(b);
        time.setEditable(b);
        type.setEditable(b);
    }

    private void fillFields(Transporte tp){
        cost.setText(tp.getCusto().toString());
        time.setText(tp.getTempoEntrega());
        type.setText(tp.getTipo());
    }

    private void fillTable(){
        typeCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("custo"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("tempoEntrega"));
    }

}
