package estg.ipvc.projetodekstop.Controllers.GestorProd;

import estg.ipvc.projeto.data.BLL.DBConnect;
import estg.ipvc.projeto.data.BLL.GestorProdBLL;
import estg.ipvc.projeto.data.Entity.TipoCereal;
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

public class GestorProdManageCerealTypeController implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private Button addBtn2;

    @FXML
    private Button cancelAddBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TableColumn<TipoCereal, String> cerealName;

    @FXML
    private TableColumn<TipoCereal, Integer> cerealid;

    @FXML
    private Button confirmBtn;

    @FXML
    private Button editBtn;

    @FXML
    private TextField name;

    @FXML
    private Text nameTxt;

    @FXML
    private Button removeBtn;

    @FXML
    private TableView<TipoCereal> tipoCerealTable;

    private final EntityManager em = DBConnect.getEntityManager();

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("gestorprodmenu.fxml", "Menu Gestor de Produção", event);
    }

    @FXML
    void addCerealType() {
        nameTxt.setVisible(true);
        name.setVisible(true);
        addBtn2.setVisible(true);
        cancelAddBtn.setVisible(true);
        addBtn.setVisible(false);
        removeBtn.setVisible(false);
        editBtn.setVisible(false);
        cancelBtn.setVisible(false);
        confirmBtn.setVisible(false);
    }


    @FXML
    void cancelPersist() {
        nameTxt.setVisible(false);
        name.setVisible(false);
        addBtn2.setVisible(false);
        cancelAddBtn.setVisible(false);
        addBtn.setVisible(true);
        removeBtn.setVisible(true);
        editBtn.setVisible(true);
    }

    @FXML
    void cancelUpdate() {
        nameTxt.setVisible(false);
        name.setVisible(false);
        confirmBtn.setVisible(false);
        cancelBtn.setVisible(false);
        addBtn.setVisible(true);
        removeBtn.setVisible(true);
        editBtn.setVisible(true);
    }

    @FXML
    void persist() {
        if(name.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Preencha todos os campos");
            alert.showAndWait();
            return;
        }

        TipoCereal tc = new TipoCereal();
        tc.setNomeCereal(name.getText());
        try {
            GestorProdBLL.addTipoCereal(tc);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao adicionar cereal");
            alert.showAndWait();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Cereal adicionado com sucesso");
        alert.showAndWait();
        name.clear();
        refresTable();
        cancelPersist();

    }

    @FXML
    void remove() {
        TipoCereal tc = tipoCerealTable.getSelectionModel().getSelectedItem();

        if (tc == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Selecione um cereal");
            alert.showAndWait();
        }
        else {
            try {
                GestorProdBLL.removeTipoCereal(tc);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao remover cereal");
                alert.showAndWait();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Cereal removido com sucesso");
            alert.showAndWait();
            refresTable();
        }
    }

    @FXML
    void confirmUpdate(){

        TipoCereal tc = tipoCerealTable.getSelectionModel().getSelectedItem();

        if(name.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Preencha todos os campos");
            alert.showAndWait();
            return;
        }

        tc.setNomeCereal(name.getText());
        try {
            GestorProdBLL.updateTipoCereal(tc);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao atualizar cereal");
            alert.showAndWait();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Cereal atualizado com sucesso");
        alert.showAndWait();
        refresTable();
        cancelUpdate();
    }


    @FXML
    void update() {

        tipoCerealTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                name.setText(newSelection.getNomeCereal());

            }
        });

        TipoCereal tc = tipoCerealTable.getSelectionModel().getSelectedItem();

        if(tc == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Selecione um cereal");
            alert.showAndWait();
            return;
        }

        nameTxt.setVisible(true);
        name.setVisible(true);
        confirmBtn.setVisible(true);
        cancelBtn.setVisible(true);
        addBtn.setVisible(false);
        removeBtn.setVisible(false);
        editBtn.setVisible(false);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addBtn.setVisible(true);
        removeBtn.setVisible(true);
        editBtn.setVisible(true);
        nameTxt.setVisible(false);
        name.setVisible(false);
        confirmBtn.setVisible(false);
        cancelBtn.setVisible(false);
        addBtn2.setVisible(false);
        cancelAddBtn.setVisible(false);

        List<TipoCereal> tipoCereals = em.createQuery("SELECT tc FROM TipoCereal tc", TipoCereal.class).getResultList();
        tipoCerealTable.getItems().clear();
        for (TipoCereal tc : tipoCereals) {
            fillTable();
            tipoCerealTable.getItems().add(tc);
        }
    }

    private void fillTable(){
        cerealid.setCellValueFactory(new PropertyValueFactory<>("idTipoCereal"));
        cerealName.setCellValueFactory(new PropertyValueFactory<>("nomeCereal"));
    }

    private void refresTable(){
        List<TipoCereal> tipoCereals = em.createQuery("SELECT tc FROM TipoCereal tc", TipoCereal.class).getResultList();
        tipoCerealTable.getItems().clear();
        for (TipoCereal tc : tipoCereals) {
            fillTable();
            tipoCerealTable.getItems().add(tc);
        }
    }

}
