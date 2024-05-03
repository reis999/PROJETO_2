package estg.ipvc.projetodekstop.Controllers.GestorVenda;

import estg.ipvc.projeto.data.BLL.DBConnect;
import estg.ipvc.projeto.data.BLL.GestorVendaBLL;
import estg.ipvc.projeto.data.Entity.*;
import estg.ipvc.projetodekstop.Controllers.LoginController;
import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import jakarta.persistence.EntityManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GestorVendaRegisterSaleController implements Initializable {

    @FXML
    public TextField qtd;
    @FXML
    public Label custoLabel;
    @FXML
    private ListView<String> clientList;

    @FXML
    private DatePicker date;

    @FXML
    private ListView<String> loteList;

    @FXML
    private ListView<String> transportList;

    private final EntityManager em = DBConnect.getEntityManager();

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("gestorvendamenu.fxml", "Menu Gestor de Vendas", event);
    }

    @FXML
    void persist(ActionEvent event) {
        String loteString = loteList.getSelectionModel().getSelectedItem();
        String clientString = clientList.getSelectionModel().getSelectedItem();
        String transportString = transportList.getSelectionModel().getSelectedItem();

        Lote lote = em.createQuery("SELECT l FROM Lote l WHERE l.idLote = :id", Lote.class).setParameter("id", Integer.parseInt(loteString.split(" - ")[0])).getSingleResult();
        Cliente cliente = em.createQuery("SELECT c FROM Cliente c WHERE c.idCliente = :id", Cliente.class).setParameter("id", Integer.parseInt(clientString.split(" - ")[0])).getSingleResult();
        Transporte transporte = em.createQuery("SELECT t FROM Transporte t WHERE t.idTransporte = :id", Transporte.class).setParameter("id", Integer.parseInt(transportString.split(" - ")[0])).getSingleResult();

        if(lote == null || cliente == null || transporte == null || date.getValue() == null || qtd.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Campos em falta");
            alert.setContentText("Por favor preencha todos os campos");
            alert.showAndWait();
            return;
        }

        if(!qtd.getText().matches("[0-9]+")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Quantidade inválida");
            alert.setContentText("Por favor insira uma quantidade válida");
            alert.showAndWait();
            return;
        }

        try {
            Venda venda = new Venda();
            LinhaVenda linhaVenda = new LinhaVenda();
            linhaVenda.setQuantidade(Integer.parseInt(qtd.getText()));
            venda.setGestorVenda(LoginController.gestorVenda);
            venda.setData(java.sql.Date.valueOf(date.getValue()));
            venda.setCliente(cliente);
            venda.setTransporteByIdTransporte(transporte);
            venda.setEstado("PENDENTE");
            GestorVendaBLL.addVenda(LoginController.gestorVenda, venda, cliente);
            GestorVendaBLL.registerLinhaVenda(venda, lote, linhaVenda);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao registar venda");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Venda registada com sucesso");
        alert.showAndWait();
        LoadFXML.getInstance().loadResource("gestorvendamenu.fxml", "Menu Gestor de Vendas", event);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loteList.getItems().clear();
        List<Lote> lotes = em.createQuery("SELECT l FROM Lote l", Lote.class).getResultList();
        for (Lote l : lotes) {
            loteList.getItems().addAll(l.getIdLote() + " - " + l.getTipoCereal().getNomeCereal());
        }
        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        for (Cliente c : clientes) {
            clientList.getItems().addAll(c.getIdCliente() + " - " + c.getUtilizador().getNome());
        }
        List<Transporte> transportes = em.createQuery("SELECT t FROM Transporte t", Transporte.class).getResultList();
        for (Transporte t : transportes) {
            transportList.getItems().addAll(t.getIdTransporte() + " - " + t.getTipo());
        }


        qtd.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!qtd.getText().isEmpty()){
                Lote lote = em.createQuery("SELECT l FROM Lote l WHERE l.idLote = :id", Lote.class).setParameter("id", Integer.parseInt(loteList.getSelectionModel().getSelectedItem().split(" - ")[0])).getSingleResult();
                custoLabel.setText(String.valueOf(BigDecimal.valueOf(Integer.parseInt(qtd.getText())).multiply(lote.getPrecoUnidade())));
            }else{
                custoLabel.setText("0");
            }
        });
    }
}
