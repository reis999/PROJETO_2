package estg.ipvc.projetodekstop.Controllers.GestorVenda;

import estg.ipvc.projeto.data.BLL.DBConnect;
import estg.ipvc.projeto.data.Entity.Cliente;
import estg.ipvc.projeto.data.Entity.Utilizador;
import estg.ipvc.projeto.data.Entity.Venda;
import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import jakarta.persistence.EntityManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GestorVendaListClientsController implements Initializable {

    @FXML
    private TableView<Cliente> clientTable;

    @FXML
    private TableColumn<Cliente, String> companyCol;

    @FXML
    private TableColumn<Cliente, String> nameCol;

    @FXML
    private TableColumn<Cliente, Integer> nifCol;

    @FXML
    private TableColumn<Cliente, Number> ordersCol;

    @FXML
    private TableColumn<Cliente, BigDecimal> totalCol;
    private final EntityManager em = DBConnect.getEntityManager();

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("gestorvendamenu.fxml", "Menu Gestor de Vendas", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        clientTable.getItems().clear();

        for(Cliente ignored : clientes){
            fillTable();
        }
    }

    private void fillTable() {
        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();

        nameCol.setCellValueFactory(cellData -> {
            Cliente cliente = cellData.getValue();
            Utilizador utilizador = cliente.getUtilizador();
            return new SimpleStringProperty(utilizador.getNome());
        });

        companyCol.setCellValueFactory(new PropertyValueFactory<>("nomeEmpresa"));

        nifCol.setCellValueFactory(new PropertyValueFactory<>("nif"));

        ordersCol.setCellValueFactory(cellData -> {
            Cliente cliente = cellData.getValue();
            List<Venda> vendas = em.createQuery("SELECT v FROM Venda v WHERE v.cliente = :cliente", Venda.class)
                    .setParameter("cliente", cliente)
                    .getResultList();
            int numOrders = vendas.size();
            return new SimpleIntegerProperty(numOrders);
        });

        totalCol.setCellValueFactory(cellData -> {
            Cliente cliente = cellData.getValue();
            List<Venda> vendas = em.createQuery("SELECT v FROM Venda v WHERE v.cliente = :cliente", Venda.class)
                    .setParameter("cliente", cliente)
                    .getResultList();
            BigDecimal total = vendas.stream()
                    .map(venda -> venda.getLinhaVendas().stream()
                            .map(linhaVenda -> linhaVenda.getLoteByIdLote().getPrecoUnidade().multiply(new BigDecimal(linhaVenda.getQuantidade())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            return new SimpleObjectProperty<>(total);
        });

        clientTable.getItems().setAll(clientes);
    }


}

