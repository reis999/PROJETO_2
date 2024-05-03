package estg.ipvc.projetodekstop.Controllers.GestorProd;

import estg.ipvc.projeto.data.BLL.DBConnect;
import estg.ipvc.projeto.data.BLL.GestorProdBLL;
import estg.ipvc.projeto.data.Entity.Cultivo;
import estg.ipvc.projeto.data.Entity.Lote;
import estg.ipvc.projeto.data.Entity.LoteCultivo;
import estg.ipvc.projeto.data.Entity.TipoCereal;
import estg.ipvc.projetodekstop.Controllers.LoginController;
import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import jakarta.persistence.EntityManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class GestorProdManageBatchesController implements Initializable {

    @FXML
    private TableView<Lote> batchTable;

    @FXML
    private Button cancelBtn;

    @FXML
    private ChoiceBox<String> cerealType;

    @FXML
    private TableColumn<Lote, String> cerealTypeCol;

    @FXML
    private TableColumn<Lote, Date> collectDateCol;

    @FXML
    private Button confirmBtn;

    @FXML
    private ChoiceBox<String> cultivation;

    @FXML
    private TableColumn<Lote, String> cultivationCol;

    @FXML
    private Button editBtn;

    @FXML
    private TableColumn<Lote, Integer> idCol;

    @FXML
    private TableColumn<Lote, Date> plantDateCol;

    @FXML
    private TextField price;

    @FXML
    private TableColumn<Lote, Double> priceCol;

    @FXML
    private TableColumn<Lote, Integer> qtdCol;

    @FXML
    private TextField quantity;

    @FXML
    private Button removeBtn;

    private final EntityManager em = DBConnect.getEntityManager();

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("gestorprodmenu.fxml", "Menu de Gestor de Produção", event);
    }

    @FXML
    void cancel() {
        cancelBtn.setVisible(false);
        confirmBtn.setVisible(false);
        editBtn.setVisible(true);
        removeBtn.setVisible(true);
        quantity.setEditable(false);
        price.setEditable(false);
        cultivation.setDisable(true);
        cerealType.setDisable(true);
        quantity.clear();
        price.clear();
    }

    @FXML
    void edit() {

        Lote lote = batchTable.getSelectionModel().getSelectedItem();

        if(lote == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Selecione um lote para editar");
            alert.showAndWait();
            return;
        }

        cancelBtn.setVisible(true);
        confirmBtn.setVisible(true);
        editBtn.setVisible(false);
        removeBtn.setVisible(false);
        quantity.setEditable(true);
        price.setEditable(true);
        cultivation.setDisable(false);
        cerealType.setDisable(false);
    }

    @FXML
    void persist() {

        if(quantity.getText().isEmpty() || price.getText().isEmpty() || cultivation.getValue() == null || cerealType.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Preencha todos os campos");
            alert.showAndWait();
            return;
        }

        if(!quantity.getText().matches("[0-9]+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Quantidade inválida");
            alert.showAndWait();
            return;
        }

        if(!price.getText().matches("[0-9]+(\\.[0-9]+)?")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Preço inválido");
            alert.showAndWait();
            return;
        }

        Lote lote = batchTable.getSelectionModel().getSelectedItem();
        lote.setQuantidade(Integer.parseInt(quantity.getText()));
        lote.setPrecoUnidade(BigDecimal.valueOf(Double.parseDouble(price.getText())));
        LoteCultivo loteCultivo = em.createQuery("SELECT lc FROM LoteCultivo lc WHERE lc.loteByIdLote = :lote", LoteCultivo.class)
                .setParameter("lote", lote)
                .getSingleResult();
        loteCultivo.setCultivoByIdCultivo(em.createQuery("SELECT c FROM Cultivo c WHERE c.tipoCultivo = :cultivo", Cultivo.class)
                .setParameter("cultivo", cultivation.getValue())
                .getSingleResult());
        lote.setTipoCereal(em.createQuery("SELECT tc FROM TipoCereal tc WHERE tc.nomeCereal = :cereal", TipoCereal.class)
                .setParameter("cereal", cerealType.getValue())
                .getSingleResult());

        try {
            GestorProdBLL.updateLote(lote);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar lote");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Lote editado com sucesso");
        alert.showAndWait();

        cancel();
        quantity.clear();
        price.clear();
        cultivation.setValue(null);
        cerealType.setValue(null);
        List<Lote> lotes = em.createQuery("SELECT l FROM Lote l", Lote.class).getResultList();
        batchTable.getItems().clear();
        for (Lote lote1 : lotes) {
            batchTable.getItems().add(lote1);
            fillTable();
        }
    }

    @FXML
    void remove() {
        Lote lote = batchTable.getSelectionModel().getSelectedItem();

        if(lote == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Selecione um lote para remover");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remover Lote");
        alert.setHeaderText("Tem a certeza que deseja remover o lote?");
        alert.showAndWait();
        if(alert.getResult().getText().equals("CANCELAR"))
            return;

        try {
            GestorProdBLL.removeLote(LoginController.gestorProducao, lote);
        }catch (Exception e){
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Erro");
            alert1.setHeaderText("Erro ao remover lote");
            alert1.showAndWait();
            return;
        }

        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Sucesso");
        alert1.setHeaderText("Lote removido com sucesso");
        alert1.showAndWait();

        List<Lote> lotes = em.createQuery("SELECT l FROM Lote l", Lote.class).getResultList();
        batchTable.getItems().clear();
        for (Lote lote1 : lotes) {
            batchTable.getItems().add(lote1);
            fillTable();
        }
        cancel();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        quantity.setEditable(false);
        price.setEditable(false);
        cultivation.setDisable(true);
        cerealType.setDisable(true);
        confirmBtn.setVisible(false);
        cancelBtn.setVisible(false);
        editBtn.setVisible(true);
        removeBtn.setVisible(true);



        batchTable.getItems().clear();
        cultivation.getItems().addAll(em.createQuery("SELECT c.tipoCultivo FROM Cultivo c", String.class).getResultList());
        cerealType.getItems().addAll(em.createQuery("SELECT c.nomeCereal FROM TipoCereal c", String.class).getResultList());

        List<Lote> lotes = em.createQuery("SELECT l FROM Lote l", Lote.class).getResultList();
        for (Lote lote : lotes) {
            batchTable.getItems().add(lote);
            fillTable();
        }

        batchTable.getSelectionModel().selectedItemProperty().addListener((observableValue, lote, t1) -> {
            if(t1 != null){
                quantity.setText(String.valueOf(t1.getQuantidade()));
                price.setText(String.valueOf(t1.getPrecoUnidade()));
                cultivation.setValue(em.createQuery("SELECT lc.cultivoByIdCultivo.tipoCultivo FROM LoteCultivo lc WHERE lc.loteByIdLote = :lote", String.class)
                        .setParameter("lote", t1)
                        .getSingleResult());
                cerealType.setValue(em.createQuery("SELECT tc.nomeCereal FROM TipoCereal tc WHERE tc.idTipoCereal = :id", String.class)
                        .setParameter("id", t1.getTipoCereal().getIdTipoCereal())
                        .getSingleResult());
            }
        });

    }

    private void fillTable(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("idLote"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("precoUnidade"));
        qtdCol.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        plantDateCol.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        collectDateCol.setCellValueFactory(new PropertyValueFactory<>("dataRecolha"));
        cultivationCol.setCellValueFactory(cellData -> {
            Lote lote = cellData.getValue();
            LoteCultivo loteCultivo = em.createQuery("SELECT lc FROM LoteCultivo lc WHERE lc.loteByIdLote = :lote", LoteCultivo.class)
                    .setParameter("lote", lote)
                    .getSingleResult();
            return new SimpleStringProperty(loteCultivo.getCultivoByIdCultivo().getTipoCultivo());
        });

        cerealTypeCol.setCellValueFactory(cellData -> {
            Lote lote = cellData.getValue();
            return new SimpleStringProperty(lote.getTipoCereal().getNomeCereal());
        });
    }

}
