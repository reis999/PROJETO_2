package estg.ipvc.projetodekstop.Controllers.GestorProd;

import estg.ipvc.projeto.data.BLL.DBConnect;
import estg.ipvc.projeto.data.Entity.Lote;
import estg.ipvc.projeto.data.Entity.TipoCereal;
import estg.ipvc.projetodekstop.Controllers.LoginController;
import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import jakarta.persistence.EntityManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class GestorProdListBatchesController implements Initializable {

    @FXML
    private TableView<Lote> batchTable;

    @FXML
    private TableColumn<Lote, String> cerealType;

    @FXML
    private TableColumn<Lote, Date> collectDateCol;

    @FXML
    private TableColumn<Lote, Integer> idCol;

    @FXML
    private TableColumn<Lote, Date> plantDateCol;

    @FXML
    private TableColumn<Lote, Double> priceCol;

    @FXML
    private TableColumn<Lote, Integer> qtdCol;

    private final EntityManager em = DBConnect.getEntityManager();

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("gestorprodmenu.fxml", "Menu Gestor de Produção", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Lote> batches = em.createQuery("SELECT l FROM Lote l WHERE l.gestorProducao = :id", Lote.class)
                .setParameter("id", LoginController.gestorProducao)
                .getResultList();

        for(Lote l : batches){
            batchTable.getItems().add(l);
            idCol.setCellValueFactory(new PropertyValueFactory<>("idLote"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("precoUnidade"));
            qtdCol.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            plantDateCol.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
            collectDateCol.setCellValueFactory(new PropertyValueFactory<>("dataRecolha"));

            cerealType.setCellValueFactory(cellData -> {
                TipoCereal tc = new TipoCereal();
                tc.setIdTipoCereal(cellData.getValue().getTipoCereal().getIdTipoCereal());
                tc.setNomeCereal(cellData.getValue().getTipoCereal().getNomeCereal());
                return new SimpleStringProperty(tc.getNomeCereal());
            });
        }
    }
}
