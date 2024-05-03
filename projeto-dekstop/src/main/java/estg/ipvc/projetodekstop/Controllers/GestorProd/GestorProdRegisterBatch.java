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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class GestorProdRegisterBatch implements Initializable {

    @FXML
    private ChoiceBox<String> cerealType;

    @FXML
    private DatePicker collectDate;

    @FXML
    private ChoiceBox<String> cultivo;

    @FXML
    private DatePicker plantDate;

    @FXML
    private TextField price;

    @FXML
    private TextField qtd;

    private final EntityManager em = DBConnect.getEntityManager();

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("gestorprodmenu.fxml", "Menu Gestor de Produção", event);
    }

    @FXML
    void persist(ActionEvent event) {

        if(price.getText().isEmpty() || qtd.getText().isEmpty() || collectDate.getValue() == null || plantDate.getValue() == null || cerealType.getSelectionModel().isEmpty() || cultivo.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Campos em falta");
            alert.setContentText("Por favor preencha todos os campos");
            alert.showAndWait();
            return;
        }

        if(plantDate.getValue().isAfter(collectDate.getValue())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Datas inválidas");
            alert.setContentText("A data de plantação não pode ser posterior à data de recolha");
            alert.showAndWait();
            return;
        }

        if(!price.getText().matches("-?\\d+(\\.\\d+)?") || !qtd.getText().matches("[0-9]+")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Valores inválidos");
            alert.setContentText("Por favor insira valores válidos");
            alert.showAndWait();
            return;
        }

        Lote lote = new Lote();
        LoteCultivo lc = new LoteCultivo();
        Cultivo c;
        c = em.createQuery("SELECT c FROM Cultivo c WHERE c.tipoCultivo = :tipo", Cultivo.class).setParameter("tipo", cultivo.getSelectionModel().getSelectedItem()).getSingleResult();
        lote.setPrecoUnidade(BigDecimal.valueOf(Double.parseDouble(price.getText())));
        lote.setQuantidade(Integer.parseInt(qtd.getText()));
        lote.setDataInicio(java.sql.Date.valueOf(plantDate.getValue()));
        lote.setDataRecolha(java.sql.Date.valueOf(collectDate.getValue()));
        lote.setTipoCereal(em.createQuery("SELECT t FROM TipoCereal t WHERE t.nomeCereal = :nome", TipoCereal.class).setParameter("nome", cerealType.getSelectionModel().getSelectedItem()).getSingleResult());

        try {
            GestorProdBLL.addLote(LoginController.gestorProducao, lote);
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao registar lote");
            alert.setContentText("Ocorreu um erro ao registar o lote");
            alert.showAndWait();
            return;
        }

        try {
            System.out.println(c.getTipoCultivo());

            GestorProdBLL.registerLoteCultivo(lote, c, lc);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao registar lote");
            alert.setContentText("Ocorreu um erro ao relacionar o lote ao cultivo");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Lote registado com sucesso");
        alert.setContentText("O lote foi registado com sucesso");
        alert.showAndWait();

        LoadFXML.getInstance().loadResource("gestorprodmenu.fxml", "Menu Gestor de Produção", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cerealType.getItems().addAll(em.createQuery("SELECT t.nomeCereal FROM TipoCereal t", String.class).getResultList());
        cultivo.getItems().addAll(em.createQuery("SELECT c.tipoCultivo FROM Cultivo c", String.class).getResultList());
    }
}
