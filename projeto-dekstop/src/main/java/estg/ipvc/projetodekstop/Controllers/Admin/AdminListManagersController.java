package estg.ipvc.projetodekstop.Controllers.Admin;

import estg.ipvc.projeto.data.BLL.DBConnect;
import estg.ipvc.projeto.data.BLL.GestorProdBLL;
import estg.ipvc.projeto.data.Entity.GestorProducao;
import estg.ipvc.projeto.data.Entity.GestorVenda;
import estg.ipvc.projeto.data.Entity.Utilizador;
import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import jakarta.persistence.EntityManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminListManagersController implements Initializable {

    @FXML
    private TableColumn<Utilizador, String> cpostal;

    @FXML
    private TableView<Utilizador> gestorTable;

    @FXML
    private TableColumn<Utilizador, Integer> id;

    @FXML
    private TableColumn<Utilizador, String> nome;

    @FXML
    private TableColumn<Utilizador, Integer> nporta;

    @FXML
    private TableColumn<Utilizador, String> password;

    @FXML
    private TableColumn<Utilizador, String> rua;

    @FXML
    private ChoiceBox<String> tableChoice;

    @FXML
    private TableColumn<Utilizador, String> username;

    private final EntityManager em = DBConnect.getEntityManager();

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("adminmenu.fxml", "Menu Admin", event);
    }

    @FXML
    void removeData(ActionEvent event) {
        Utilizador u = gestorTable.getSelectionModel().getSelectedItem();
        if(u == null){
            return;
        }

        try {
            GestorVenda gv = em.createQuery("SELECT gv FROM GestorVenda gv WHERE gv.utilizador = :id", GestorVenda.class)
                    .setParameter("id", u)
                    .getSingleResult();
            em.getTransaction().begin();
            em.remove(gv);
            em.getTransaction().commit();
        }catch (Exception ignored){}

        try {
            GestorProducao gp = em.createQuery("SELECT gp FROM GestorProducao gp WHERE gp.utilizador = :id", GestorProducao.class)
                    .setParameter("id", u)
                    .getSingleResult();
            GestorProdBLL.remove(gp);
        }catch (Exception ignored){}

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmação");
        confirmation.setHeaderText("Tem a certeza que deseja remover o gestor?");
        confirmation.showAndWait();
        if(confirmation.getResult().getText().equals("CANCELAR")){
            return;
        }

        try {
            em.getTransaction().begin();
            em.remove(u);
            em.getTransaction().commit();
            gestorTable.getItems().remove(u);
        } catch (Exception e) {
            em.getTransaction().rollback();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Não foi possível remover o gestor");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Gestor removido com sucesso");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableChoice.getItems().addAll("Gestor de Venda", "Gestor de Produção", "Todos");
        List<Utilizador> users = em.createQuery("FROM Utilizador", Utilizador.class).getResultList();
        tableChoice.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if(t1.equals("Gestor de Venda")){
                gestorTable.getItems().clear();
                GestorVenda gv;
                for(Utilizador u : users){
                    try {
                        gv = em.createQuery("SELECT gv FROM GestorVenda gv WHERE gv.utilizador = :id", GestorVenda.class)
                                .setParameter("id", u)
                                .getSingleResult();
                    }catch (Exception ignored){
                        continue;
                    }

                    if(gv != null){
                        gestorTable.getItems().add(u);
                        fillTable(u);
                    }
                }

            }else if(t1.equals("Gestor de Produção")){
                gestorTable.getItems().clear();
                GestorProducao gp;
                for(Utilizador u : users){
                    try {
                        gp = em.createQuery("SELECT gp FROM GestorProducao gp WHERE gp.utilizador = :id", GestorProducao.class)
                                .setParameter("id", u)
                                .getSingleResult();
                    }catch (Exception ignored){
                        continue;
                    }

                    if(gp != null){
                        gestorTable.getItems().add(u);
                        fillTable(u);
                    }
                }
            }else{
                gestorTable.getItems().clear();
                GestorProducao gp;
                GestorVenda gv;
                for(Utilizador u : users){
                    try {
                        gp = em.createQuery("SELECT gp FROM GestorProducao gp WHERE gp.utilizador = :id", GestorProducao.class)
                                .setParameter("id", u)
                                .getSingleResult();
                    }catch (Exception ignored){
                        gp = null;
                    }

                    try {
                        gv = em.createQuery("SELECT gv FROM GestorVenda gv WHERE gv.utilizador = :id", GestorVenda.class)
                                .setParameter("id", u)
                                .getSingleResult();
                    }catch (Exception ignored){
                        gv = null;
                    }

                    if(gp != null || gv != null){
                        gestorTable.getItems().add(u);
                        fillTable(u);
                    }
                }
            }
        });
    }

    private void fillTable(Utilizador u){
        id.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        rua.setCellValueFactory(new PropertyValueFactory<>("rua"));
        nporta.setCellValueFactory(new PropertyValueFactory<>("numporta"));
        cpostal.setCellValueFactory(c -> new SimpleStringProperty(u.getCodpostal().getCodpostal()));
    }

}
