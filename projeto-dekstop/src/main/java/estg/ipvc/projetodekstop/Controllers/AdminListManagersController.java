package estg.ipvc.projetodekstop.Controllers;

import estg.ipvc.projeto.data.BLL.DBConnect;
import estg.ipvc.projeto.data.Entity.Codpostal;
import estg.ipvc.projeto.data.Entity.GestorProducao;
import estg.ipvc.projeto.data.Entity.GestorVenda;
import estg.ipvc.projeto.data.Entity.Utilizador;
import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import jakarta.persistence.EntityManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private TableColumn<Utilizador, Codpostal> cpostal;

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
                        fillTable();
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
                        fillTable();
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
                        fillTable();
                    }
                }
            }
        });
    }

    private void fillTable(){
        id.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        rua.setCellValueFactory(new PropertyValueFactory<>("rua"));
        nporta.setCellValueFactory(new PropertyValueFactory<>("numporta"));
        cpostal.setCellValueFactory(new PropertyValueFactory<>("codpostal"));
    }

}
