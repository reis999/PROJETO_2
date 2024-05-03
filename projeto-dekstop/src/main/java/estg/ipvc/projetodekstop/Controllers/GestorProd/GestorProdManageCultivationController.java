package estg.ipvc.projetodekstop.Controllers.GestorProd;

import estg.ipvc.projeto.data.BLL.DBConnect;
import estg.ipvc.projeto.data.BLL.GestorProdBLL;
import estg.ipvc.projeto.data.Entity.Cultivo;
import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GestorProdManageCultivationController implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private Button addBtn2;

    @FXML
    private Button cancelAddBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TableColumn<Cultivo, Integer> cerealid;

    @FXML
    private Button confirmBtn;

    @FXML
    private TableView<Cultivo> cultivationTable;

    @FXML
    private TextArea cultivo;

    @FXML
    private Button editBtn;

    @FXML
    private Text nameTxt;

    @FXML
    private Button removeBtn;

    @FXML
    private TableColumn<Cultivo, String> typeCol;

    private final EntityManager em = DBConnect.getEntityManager();

    @FXML
    void addCultivation() {
        nameTxt.setVisible(true);
        cultivo.setVisible(true);
        addBtn2.setVisible(true);
        cancelAddBtn.setVisible(true);
        addBtn.setVisible(false);
        removeBtn.setVisible(false);
        editBtn.setVisible(false);
        cancelBtn.setVisible(false);
        confirmBtn.setVisible(false);
    }

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("gestorprodmenu.fxml", "Menu Gestor de Produção", event);
    }

    @FXML
    void cancelPersist() {
        nameTxt.setVisible(false);
        cultivo.setVisible(false);
        addBtn2.setVisible(false);
        cancelAddBtn.setVisible(false);
        addBtn.setVisible(true);
        removeBtn.setVisible(true);
        editBtn.setVisible(true);

    }

    @FXML
    void cancelUpdate() {
        nameTxt.setVisible(false);
        cultivo.setVisible(false);
        confirmBtn.setVisible(false);
        cancelBtn.setVisible(false);
        addBtn.setVisible(true);
        removeBtn.setVisible(true);
        editBtn.setVisible(true);
    }

    @FXML
    void confirmUpdate() {

        if (cultivo.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Campo em falta");
            alert.setContentText("Por favor preencha o campo");
            alert.showAndWait();
            return;
        }

        Cultivo c = cultivationTable.getSelectionModel().getSelectedItem();
        c.setTipoCultivo(cultivo.getText());
        try {
            GestorProdBLL.updateCultivo(c);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao atualizar cultivo");
            alert.setContentText("Ocorreu um erro ao atualizar o cultivo");
            alert.showAndWait();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Cultivo atualizado com sucesso");
        alert.showAndWait();
        cultivo.clear();
        refreshTable();
        cancelUpdate();
    }

    @FXML
    void persist() {

        if(cultivo.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Campo em falta");
            alert.setContentText("Por favor preencha o campo");
            alert.showAndWait();
            return;
        }

        Cultivo c = new Cultivo();
        c.setTipoCultivo(cultivo.getText());
        try {
            GestorProdBLL.addCultivo(c);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao atualizar cultivo");
            alert.setContentText("Ocorreu um erro ao atualizar o cultivo");
            alert.showAndWait();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Cultivo registado com sucesso");
        alert.showAndWait();
        cultivo.clear();
        refreshTable();
        cancelPersist();
    }

    @FXML
    void remove() {
        Cultivo c = cultivationTable.getSelectionModel().getSelectedItem();
        if (c == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhum cultivo selecionado");
            alert.setContentText("Por favor selecione um cultivo");
            alert.showAndWait();
            return;
        }
        try {
            GestorProdBLL.removeCultivo(c);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao remover cultivo");
            alert.setContentText("Ocorreu um erro ao remover o cultivo");
            alert.showAndWait();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Cultivo removido com sucesso");
        alert.showAndWait();
        refreshTable();

    }

    @FXML
    void update() {
        Cultivo c = cultivationTable.getSelectionModel().getSelectedItem();
        if(c == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhum cultivo selecionado");
            alert.setContentText("Por favor selecione um cultivo");
            alert.showAndWait();
            return;
        }
        cultivo.setText(c.getTipoCultivo());
        nameTxt.setVisible(true);
        cultivo.setVisible(true);
        confirmBtn.setVisible(true);
        cancelBtn.setVisible(true);
        addBtn.setVisible(false);
        removeBtn.setVisible(false);
        editBtn.setVisible(false);
        addBtn2.setVisible(false);
        cancelAddBtn.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addBtn.setVisible(true);
        removeBtn.setVisible(true);
        editBtn.setVisible(true);
        nameTxt.setVisible(false);
        cultivo.setVisible(false);
        confirmBtn.setVisible(false);
        cancelBtn.setVisible(false);
        addBtn2.setVisible(false);
        cancelAddBtn.setVisible(false);

        List<Cultivo> cultivos = em.createQuery("SELECT c FROM Cultivo c", Cultivo.class).getResultList();
        for (Cultivo c : cultivos) {
            fillTable();
            cultivationTable.getItems().add(c);
        }
    }

    private void fillTable(){
        cerealid.setCellValueFactory(new PropertyValueFactory<>("idCultivo"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("tipoCultivo"));
    }

    private void refreshTable(){
        cultivationTable.getItems().clear();
        List<Cultivo> cultivos = em.createQuery("SELECT c FROM Cultivo c", Cultivo.class).getResultList();
        for (Cultivo c : cultivos) {
            fillTable();
            cultivationTable.getItems().add(c);
        }
    }
}
