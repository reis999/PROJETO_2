package estg.ipvc.projetodekstop.Controllers.GestorVenda;

import estg.ipvc.projeto.data.BLL.DBConnect;
import estg.ipvc.projeto.data.BLL.GestorVendaBLL;
import estg.ipvc.projeto.data.Entity.LinhaVenda;
import estg.ipvc.projeto.data.Entity.Venda;
import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import jakarta.persistence.EntityManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class GestorVendaManageSalesController implements Initializable {

    @FXML
    private TableColumn<Venda, Integer> batchCol;

    @FXML
    private Button cancelBtn;

    @FXML
    private TableColumn<Venda, Integer> clientCol;

    @FXML
    private Button confirmBtn;

    @FXML
    private DatePicker date;

    @FXML
    private TableColumn<Venda, Date> dateCol;

    @FXML
    private Button editBtn;

    @FXML
    private TableColumn<Venda, Integer> idCol;

    @FXML
    private TableColumn<Venda, Integer> qtdCol;

    @FXML
    private TextField quantity;

    @FXML
    private TableView<Venda> saleTable;

    @FXML
    private ChoiceBox<String> state;

    @FXML
    private TableColumn<Venda, String> stateCol;

    @FXML
    private ChoiceBox<String> transport;

    @FXML
    private TableColumn<Venda, Integer> transportCol;

    private final EntityManager em = DBConnect.getEntityManager();

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("gestorvendamenu.fxml", "Gestão de Vendas", event);
    }

    @FXML
    void cancel() {
        cancelBtn.setVisible(false);
        confirmBtn.setVisible(false);
        editBtn.setVisible(true);
    }

    @FXML
    void edit() {
        Venda v = saleTable.getSelectionModel().getSelectedItem();

        if(v == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Selecione uma venda");
            alert.showAndWait();
            return;
        }

        cancelBtn.setVisible(true);
        confirmBtn.setVisible(true);
        editBtn.setVisible(false);
        quantity.setEditable(true);
        state.setDisable(false);
        transport.setDisable(false);
        date.setDisable(false);

    }

    @FXML
    void persist() {

        if(date.getValue() == null || state.getValue() == null || transport.getValue() == null || quantity.getText().isEmpty()){
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

        Venda v = saleTable.getSelectionModel().getSelectedItem();
        v.setData(java.sql.Date.valueOf(date.getValue()));
        v.setEstado(state.getValue());
        v.getTransporte().setIdTransporte(Integer.parseInt(transport.getValue()));
        LinhaVenda lv = em.createQuery("SELECT lv FROM LinhaVenda lv WHERE lv.idVenda = :id", LinhaVenda.class)
                .setParameter("id", v.getIdVenda())
                .getSingleResult();
        lv.setQuantidade(Integer.parseInt(quantity.getText()));


        if(Integer.parseInt(quantity.getText()) > em.createQuery("SELECT l.quantidade FROM Lote l WHERE l.idLote = :id", Integer.class)
                .setParameter("id", lv.getIdLote())
                .getSingleResult() ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Quantidade inválida");
            alert.showAndWait();
            return;
        }


        if(v.getLinhaVendas().contains(lv)){
            v.getLinhaVendas().remove(lv);
            v.getLinhaVendas().add(lv);
        }

        try{
            GestorVendaBLL.updateVenda(v);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao atualizar a venda");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Venda atualizada com sucesso");
        alert.showAndWait();

        cancelBtn.setVisible(false);
        confirmBtn.setVisible(false);
        editBtn.setVisible(true);
        quantity.setEditable(false);
        state.setDisable(true);
        transport.setDisable(true);
        date.setDisable(true);
        quantity.clear();
        refreshTable();


    }

    private void refreshTable() {
        List<Venda> vendas = em.createQuery("SELECT v FROM Venda v", Venda.class).getResultList();
        saleTable.getItems().setAll(vendas);
        for(Venda ignored : vendas){
            fillTable();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        date.setEditable(false);
        date.setDisable(true);
        quantity.setEditable(false);
        state.setDisable(true);
        transport.setDisable(true);

        cancelBtn.setVisible(false);
        confirmBtn.setVisible(false);
        editBtn.setVisible(true);
        saleTable.getItems().clear();
        state.getItems().addAll("PENDENTE", "ENTREGUE", "CANCELADO", "A CAMINHO");
        transport.getItems().addAll(em.createQuery("SELECT t.idTransporte FROM Transporte t", String.class).getResultList());
        refreshTable();

        saleTable.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) ->{
            if (newValue != null) {
                date.setValue(newValue.getData().toLocalDate());
                state.setValue(newValue.getEstado());
                transport.setValue(newValue.getTransporte().getIdTransporte() + "");
                LinhaVenda lv = em.createQuery("SELECT lv FROM LinhaVenda lv WHERE lv.idVenda = :id", LinhaVenda.class)
                        .setParameter("id", newValue.getIdVenda())
                        .getSingleResult();
                quantity.setText(lv.getQuantidade() + "");
            }
        });

    }

    private void fillTable(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("idVenda"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("estado"));


        qtdCol.setCellValueFactory(cellData -> {
            Venda venda = cellData.getValue();
            LinhaVenda lv = em.createQuery("SELECT lv FROM LinhaVenda lv WHERE lv.idVenda = :id", LinhaVenda.class)
                    .setParameter("id", venda.getIdVenda())
                    .getSingleResult();
            return new SimpleObjectProperty<>(lv.getQuantidade());
        });

        batchCol.setCellValueFactory(cellData -> {
            Venda venda = cellData.getValue();
            LinhaVenda lv = em.createQuery("SELECT lv FROM LinhaVenda lv WHERE lv.idVenda = :id", LinhaVenda.class)
                    .setParameter("id", venda.getIdVenda())
                    .getSingleResult();
            return new SimpleObjectProperty<>(lv.getIdLote());
        });
        clientCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCliente().getIdCliente()));
        transportCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTransporte().getIdTransporte()));
    }

}
