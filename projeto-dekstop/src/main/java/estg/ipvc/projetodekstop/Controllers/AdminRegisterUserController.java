package estg.ipvc.projetodekstop.Controllers;

import estg.ipvc.projeto.data.BLL.DBConnect;
import estg.ipvc.projeto.data.Entity.*;
import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import jakarta.persistence.EntityManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminRegisterUserController implements Initializable {

    @FXML
    private TableColumn<Utilizador, Integer> id;

    @FXML
    private TableColumn<Utilizador, String> nome;

    @FXML
    private TableColumn<Utilizador, String> password;

    @FXML
    private TableColumn<Utilizador, String> morada;

    @FXML
    private TableView<Utilizador> userTable;

    @FXML
    private TableColumn<Utilizador, String> username;

    private final EntityManager em = DBConnect.getEntityManager();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userTable.getItems().clear();
        ObservableList<Utilizador> usersT = userTable.getItems();
        List<Utilizador> users = em.createQuery("SELECT u FROM Utilizador u", Utilizador.class).getResultList();
        for(Utilizador u : users){
            fillTable();
            usersT.add(u);
        }
        userTable.setItems(usersT);
    }

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("adminmenu.fxml", "Menu Admin", event);
    }

    @FXML
    void registerClient(ActionEvent event) {
        LoadFXML.getInstance().loadResource("adminregisterclient.fxml", "Registar Cliente", event);
    }

    @FXML
    void registerManager(ActionEvent event) {
        LoadFXML.getInstance().loadResource("adminregistermanager.fxml", "Registar Gestor", event);
    }

    private void fillTable(){
        id.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        morada.setCellValueFactory(new PropertyValueFactory<>("rua"));
    }
}
