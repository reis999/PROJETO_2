package estg.ipvc.projetodekstop.Controllers;

import estg.ipvc.projeto.data.BLL.DBConnect;
import estg.ipvc.projeto.data.BLL.GestorProdBLL;
import estg.ipvc.projeto.data.BLL.GestorVendaBLL;
import estg.ipvc.projeto.data.Entity.Codpostal;
import estg.ipvc.projeto.data.Entity.GestorProducao;
import estg.ipvc.projeto.data.Entity.GestorVenda;
import estg.ipvc.projeto.data.Entity.Utilizador;
import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import jakarta.persistence.EntityManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminRegisterManagerController implements Initializable {

    @FXML
    private TextField codpostal;

    @FXML
    private TextField email;

    @FXML
    private Text matchPassTxt;

    @FXML
    private TextField nome;

    @FXML
    private TextField nporta;

    @FXML
    private PasswordField pass;

    @FXML
    private PasswordField pass2;

    @FXML
    private TextField rua;

    @FXML
    private TextField telefone;

    @FXML
    private ChoiceBox<String> userType;

    @FXML
    private TextField username;

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("adminregisteruser.fxml", "Registar Utilizador", event);
    }

    @FXML
    void createAcc(ActionEvent event) {
        EntityManager em = DBConnect.getEntityManager();

        if(!verifyPass()){
            alert("Passwords diferentes.");
            return;
        }
        if(username.getText().isEmpty() ||
                pass.getText().isEmpty() ||
                pass2.getText().isEmpty() ||
                nome.getText().isEmpty() ||
                userType.getValue() == null){
            alert("Preencha todos os campos obrigatórios.");
            return;
        }

        if(username.getText().equals(em.createQuery("SELECT u.username FROM Utilizador u WHERE u.username = :username", String.class).setParameter("username", username.getText()).getResultList().stream().findFirst().orElse(null))){
            alert("Username já existe, tente outro.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de registo");
        alert.setHeaderText("Tem a certeza que deseja criar a conta?");
        alert.showAndWait();

        if(alert.getResult().getText().equals("Cancelar")){
            return;
        }

        if(userType.getValue().equals("Gestor Venda")){
            Utilizador u = new Utilizador();
            setUserData(u);
            GestorVenda gv = new GestorVenda();
            try {
                GestorVendaBLL.create(gv, u);
            } catch (Exception e){
                alert("Erro ao criar conta");
                em.close();
                LoadFXML.getInstance().loadResource("adminregisteruser.fxml", "Registar Utilizador", event);
                return;
            }
        } else {
            Utilizador u = new Utilizador();
            setUserData(u);
            GestorProducao gp = new GestorProducao();
            try {
                GestorProdBLL.create(u, gp);
            } catch (Exception e){
                alert("Erro ao criar conta");
                em.close();
                LoadFXML.getInstance().loadResource("adminregisteruser.fxml", "Registar Utilizador", event);
                return;
            }
        }

        em.close();
        LoadFXML.getInstance().loadResource("adminregisteruser.fxml", "Registar Utilizador", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userType.getItems().addAll("Gestor Venda", "Gestor Produção");
    }

    public boolean verifyPass(){
        matchPassTxt.setVisible(true);
        if(pass.getText().equals(pass2.getText())){
            matchPassTxt.setText("Passwords iguais");
            matchPassTxt.setStyle("-fx-fill: green");
            return true;
        } else {
            matchPassTxt.setText("Passwords diferentes");
            return false;
        }
    }

    private void alert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro de registo");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void setUserData(Utilizador u){
        Codpostal cp = new Codpostal();
        cp.setCodpostal(codpostal.getText());
        u.setCodpostal(cp);
        u.setEmail(email.getText());
        u.setRua(rua.getText());
        if(nporta.getText().isEmpty()){
            nporta.setText("0");
        }
        u.setNumporta(Integer.parseInt(nporta.getText()));
        u.setNome(nome.getText());
        u.setPassword(pass.getText());
        u.setTelefone(telefone.getText());
        u.setUsername(username.getText());
    }
}
