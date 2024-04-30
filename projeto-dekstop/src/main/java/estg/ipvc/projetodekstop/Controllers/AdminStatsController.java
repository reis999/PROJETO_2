package estg.ipvc.projetodekstop.Controllers;

import estg.ipvc.projeto.data.BLL.DBConnect;
import estg.ipvc.projeto.data.Entity.*;
import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class AdminStatsController implements Initializable {

    @FXML
    private Label clientLabel;

    @FXML
    private Label loteLabel;

    @FXML
    private Label salesLabel;

    @FXML
    private Label transportLabel;

    private final EntityManager em = DBConnect.getEntityManager();

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("adminmenu.fxml", "Menu Admin", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int clients = 0;
        BigDecimal lote = new BigDecimal(0);
        AtomicReference<Double> sales = new AtomicReference<>((double) 0);
        double transport = 0;
        List<Lote> lotes = em.createQuery("SELECT l FROM Lote l", Lote.class).getResultList();
        for(Lote l : lotes){
            lote = lote.add(l.getPrecoUnidade());
        }
        loteLabel.setText(lote.toString());

        List<Utilizador> users = em.createQuery("SELECT u FROM Utilizador u", Utilizador.class).getResultList();
        for(Utilizador u : users){
            Cliente cli = null;
            try {
                cli = em.createQuery("select c from Cliente c where c.utilizador = :user", Cliente.class).setParameter("user", u).getSingleResult();
            }catch (Exception ignored){
            }
            if(cli != null){
                clients++;
            }
        }
        clientLabel.setText(String.valueOf(clients));

        List<Venda> vendas = em.createQuery("SELECT v FROM Venda v", Venda.class).getResultList();
        for(Venda v : vendas){
            v.getLinhaVendas().iterator().forEachRemaining(lv -> sales.updateAndGet(v1 -> v1 + lv.getQuantidade() * lv.getLoteByIdLote().getPrecoUnidade().doubleValue()));
        }

        salesLabel.setText(String.valueOf(sales.get()));

        for(Venda v : vendas){
            transport += v.getTransporte().getCusto().doubleValue();
        }

        transportLabel.setText(String.valueOf(transport));

    }
}
