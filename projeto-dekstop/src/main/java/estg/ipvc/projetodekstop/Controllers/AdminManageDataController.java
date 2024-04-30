package estg.ipvc.projetodekstop.Controllers;

import estg.ipvc.projeto.data.BLL.DBConnect;
import estg.ipvc.projeto.data.Entity.*;
import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import jakarta.persistence.EntityManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AdminManageDataController implements Initializable {

    @FXML
    private TableView<Cultivo> cultivoTable;
    @FXML
    private TableColumn<Cultivo, Integer> idCultivo;
    @FXML
    private TableColumn<Cultivo, String> tipoCultivo;



    @FXML
    private TableView<Transporte> transportTable;
    @FXML
    private TableColumn<Transporte, Integer> idTransport;
    @FXML
    private TableColumn<Transporte, String> tipoTransport;
    @FXML
    private TableColumn<Transporte, Double> custoTransport;
    @FXML
    private TableColumn<Transporte, String> tempoTransport;



    @FXML
    private TableView<Venda> vendaTable;
    @FXML
    private TableColumn<Venda, Integer> idVenda;
    @FXML
    private TableColumn<Venda, Date> dataVenda;
    @FXML
    private TableColumn<Venda, String> estadoVenda;
    @FXML
    private TableColumn<Venda, Integer> idClienteVenda;
    @FXML
    private TableColumn<Venda, Integer> idGestorVenda;
    @FXML
    private TableColumn<Venda, Integer> idTransportVenda;



    @FXML
    private TableView<LinhaVenda> linhaVendaTable;
    @FXML
    private TableColumn<LinhaVenda, Integer> idLoteLinhaVenda;
    @FXML
    private TableColumn<LinhaVenda, Integer> idVendaLinhaVenda;
    @FXML
    private TableColumn<LinhaVenda, Integer> quantidadeLinhaVenda;



    @FXML
    private TableView<Lote> loteTable;
    @FXML
    private TableColumn<Lote, Integer> idLote;
    @FXML
    private TableColumn<Lote, Double> priceLote;
    @FXML
    private TableColumn<Lote, Integer> qtdLote;
    @FXML
    private TableColumn<Lote, Date> dtIniLote;
    @FXML
    private TableColumn<Lote, Date> dtRecolhaLote;
    @FXML
    private TableColumn<Lote, Integer> idGestorLote;
    @FXML
    private TableColumn<Lote, Integer> idTipoCLote;



    @FXML
    private TableView<LoteCultivo> loteCultivoTable;
    @FXML
    private TableColumn<LoteCultivo, Integer> idLoteLC;
    @FXML
    private TableColumn<LoteCultivo, Integer> idCultivoLC;
    @FXML
    private TableColumn<LoteCultivo, Integer> qtdLC;



    @FXML
    private TableView<TipoCereal> tipoCerealTable;
    @FXML
    private TableColumn<TipoCereal, Integer> idTipoCereal;
    @FXML
    private TableColumn<TipoCereal, String> descTipoCereal;


    @FXML
    private ChoiceBox<String> tableChoice;

    private final EntityManager em = DBConnect.getEntityManager();

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("adminmenu.fxml", "Menu Admin", event);
    }

    @FXML
    void removeData(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remover");
        alert.setHeaderText("Tem a certeza que deseja remover?");
        alert.showAndWait();


        if(alert.getResult().equals(ButtonType.CANCEL) || alert.getResult().equals(ButtonType.CLOSE))
            return;

        switch (tableChoice.getValue()) {
            case "Cultivo" -> {
                Cultivo cultivo = cultivoTable.getSelectionModel().getSelectedItem();
                em.getTransaction().begin();
                em.remove(cultivo);
                em.getTransaction().commit();
                cultivoTable.getItems().remove(cultivo);
            }
            case "Transporte" -> {
                Transporte transporte = transportTable.getSelectionModel().getSelectedItem();
                em.getTransaction().begin();
                em.remove(transporte);
                em.getTransaction().commit();
                transportTable.getItems().remove(transporte);
            }
            case "Venda" -> {
                Venda venda = vendaTable.getSelectionModel().getSelectedItem();
                em.getTransaction().begin();
                em.remove(venda);
                em.getTransaction().commit();
                vendaTable.getItems().remove(venda);
            }
            case "LinhaVenda" -> {
                LinhaVenda linhaVenda = linhaVendaTable.getSelectionModel().getSelectedItem();
                em.getTransaction().begin();
                em.remove(linhaVenda);
                em.getTransaction().commit();
                linhaVendaTable.getItems().remove(linhaVenda);
            }
            case "Lote" -> {
                Lote lote = loteTable.getSelectionModel().getSelectedItem();
                em.getTransaction().begin();
                em.remove(lote);
                em.getTransaction().commit();
                loteTable.getItems().remove(lote);
            }
            case "LoteCultivo" -> {
                LoteCultivo loteCultivo = loteCultivoTable.getSelectionModel().getSelectedItem();
                em.getTransaction().begin();
                em.remove(loteCultivo);
                em.getTransaction().commit();
                loteCultivoTable.getItems().remove(loteCultivo);
            }
            case "TipoCereal" -> {
                TipoCereal tipoCereal = tipoCerealTable.getSelectionModel().getSelectedItem();
                em.getTransaction().begin();
                em.remove(tipoCereal);
                em.getTransaction().commit();
                tipoCerealTable.getItems().remove(tipoCereal);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableChoice.getItems().addAll("Cultivo", "Transporte", "Venda", "LinhaVenda", "Lote", "LoteCultivo", "TipoCereal");
        tableChoice.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            switch (newValue) {
                case "Cultivo" -> {
                    cultivoTable.setVisible(true);
                    transportTable.setVisible(false);
                    vendaTable.setVisible(false);
                    linhaVendaTable.setVisible(false);
                    loteTable.setVisible(false);
                    loteCultivoTable.setVisible(false);
                    tipoCerealTable.setVisible(false);
                    ObservableList<Cultivo> cultivosT = cultivoTable.getItems();
                    cultivosT.clear();
                    List<Cultivo> cultivos = em.createQuery("select c from Cultivo c", Cultivo.class).getResultList();
                    for(Cultivo c : cultivos){
                        cultivosT.add(c);
                        fillCultivoTable();
                    }

                }
                case "Transporte" -> {
                    cultivoTable.setVisible(false);
                    transportTable.setVisible(true);
                    vendaTable.setVisible(false);
                    linhaVendaTable.setVisible(false);
                    loteTable.setVisible(false);
                    loteCultivoTable.setVisible(false);
                    tipoCerealTable.setVisible(false);
                    ObservableList<Transporte> transportesT = transportTable.getItems();
                    transportesT.clear();
                    List<Transporte> transportes = em.createQuery("select t from Transporte t", Transporte.class).getResultList();
                    for(Transporte t : transportes){
                        transportesT.add(t);
                        fillTransportTable();
                    }
                }
                case "Venda" -> {
                    cultivoTable.setVisible(false);
                    transportTable.setVisible(false);
                    vendaTable.setVisible(true);
                    linhaVendaTable.setVisible(false);
                    loteTable.setVisible(false);
                    loteCultivoTable.setVisible(false);
                    tipoCerealTable.setVisible(false);
                    ObservableList<Venda> vendasT = vendaTable.getItems();
                    vendasT.clear();
                    List<Venda> vendas = em.createQuery("select v from Venda v", Venda.class).getResultList();
                    for(Venda venda : vendas){
                        vendasT.add(venda);
                        fillVendaTable();
                    }
                }
                case "LinhaVenda" -> {
                    cultivoTable.setVisible(false);
                    transportTable.setVisible(false);
                    vendaTable.setVisible(false);
                    linhaVendaTable.setVisible(true);
                    loteTable.setVisible(false);
                    loteCultivoTable.setVisible(false);
                    tipoCerealTable.setVisible(false);
                    ObservableList<LinhaVenda> linhaVendasT = linhaVendaTable.getItems();
                    linhaVendasT.clear();
                    List<LinhaVenda> linhaVendas = em.createQuery("select lv from LinhaVenda lv", LinhaVenda.class).getResultList();
                    for(LinhaVenda lv : linhaVendas){
                        linhaVendasT.add(lv);
                        fillLinhaVendaTable();
                    }
                }
                case "Lote" -> {
                    cultivoTable.setVisible(false);
                    transportTable.setVisible(false);
                    vendaTable.setVisible(false);
                    linhaVendaTable.setVisible(false);
                    loteTable.setVisible(true);
                    loteCultivoTable.setVisible(false);
                    tipoCerealTable.setVisible(false);
                    ObservableList<Lote> lotesT = loteTable.getItems();
                    lotesT.clear();
                    List<Lote> lotes = em.createQuery("select l from Lote l", Lote.class).getResultList();
                    for(Lote l : lotes){
                        lotesT.add(l);
                        fillLoteTable();
                    }
                }
                case "LoteCultivo" -> {
                    cultivoTable.setVisible(false);
                    transportTable.setVisible(false);
                    vendaTable.setVisible(false);
                    linhaVendaTable.setVisible(false);
                    loteTable.setVisible(false);
                    loteCultivoTable.setVisible(true);
                    tipoCerealTable.setVisible(false);
                    ObservableList<LoteCultivo> loteCultivosT = loteCultivoTable.getItems();
                    loteCultivosT.clear();
                    List<LoteCultivo> loteCultivos = em.createQuery("select lc from LoteCultivo lc", LoteCultivo.class).getResultList();
                    for(LoteCultivo lc : loteCultivos) {
                        loteCultivosT.add(lc);
                        fillLoteCultivoTable();
                    }
                }
                case "TipoCereal" -> {
                    cultivoTable.setVisible(false);
                    transportTable.setVisible(false);
                    vendaTable.setVisible(false);
                    linhaVendaTable.setVisible(false);
                    loteTable.setVisible(false);
                    loteCultivoTable.setVisible(false);
                    tipoCerealTable.setVisible(true);
                    ObservableList<TipoCereal> tipoCerealsT = tipoCerealTable.getItems();
                    tipoCerealsT.clear();
                    List<TipoCereal> tipoCereals = em.createQuery("select tc from TipoCereal tc", TipoCereal.class).getResultList();
                    for(TipoCereal tc : tipoCereals){
                        tipoCerealsT.add(tc);
                        fillTipoCerealTable();
                    }
                }
            }
        });

    }

    private void fillCultivoTable(){
        idCultivo.setCellValueFactory(new PropertyValueFactory<>("idCultivo"));
        tipoCultivo.setCellValueFactory(new PropertyValueFactory<>("tipoCultivo"));
    }

    private void fillTransportTable(){
        idTransport.setCellValueFactory(new PropertyValueFactory<>("idTransporte"));
        tipoTransport.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        custoTransport.setCellValueFactory(new PropertyValueFactory<>("custo"));
        tempoTransport.setCellValueFactory(new PropertyValueFactory<>("tempoEntrega"));
    }

    private void fillVendaTable(){
        idVenda.setCellValueFactory(new PropertyValueFactory<>("idVenda"));
        dataVenda.setCellValueFactory(new PropertyValueFactory<>("data"));
        estadoVenda.setCellValueFactory(new PropertyValueFactory<>("estado"));
        idClienteVenda.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        idGestorVenda.setCellValueFactory(new PropertyValueFactory<>("gestorVenda"));
        idTransportVenda.setCellValueFactory(new PropertyValueFactory<>("transporte"));
    }

    private void fillLinhaVendaTable(){
        idLoteLinhaVenda.setCellValueFactory(new PropertyValueFactory<>("idLote"));
        idVendaLinhaVenda.setCellValueFactory(new PropertyValueFactory<>("idVenda"));
        quantidadeLinhaVenda.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
    }

    private void fillLoteTable(){
        idLote.setCellValueFactory(new PropertyValueFactory<>("idLote"));
        priceLote.setCellValueFactory(new PropertyValueFactory<>("precoUnidade"));
        qtdLote.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        dtIniLote.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        dtRecolhaLote.setCellValueFactory(new PropertyValueFactory<>("dataRecolha"));
        idGestorLote.setCellValueFactory(new PropertyValueFactory<>("gestorProducao"));
        idTipoCLote.setCellValueFactory(new PropertyValueFactory<>("tipoCereal"));
    }

    private void fillLoteCultivoTable(){
        idLoteLC.setCellValueFactory(new PropertyValueFactory<>("idLote"));
        idCultivoLC.setCellValueFactory(new PropertyValueFactory<>("idCultivo"));
        qtdLC.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
    }

    private void fillTipoCerealTable(){
        idTipoCereal.setCellValueFactory(new PropertyValueFactory<>("idTipoCereal"));
        descTipoCereal.setCellValueFactory(new PropertyValueFactory<>("nomeCereal"));
    }

}
