package estg.ipvc.projetodekstop.Controllers;

import estg.ipvc.projeto.data.BLL.AdminBLL;
import estg.ipvc.projeto.data.BLL.GestorProdBLL;
import estg.ipvc.projeto.data.BLL.GestorVendaBLL;
import estg.ipvc.projeto.data.Entity.Codpostal;
import estg.ipvc.projeto.data.Entity.Utilizador;
import estg.ipvc.projetodekstop.OtherClasses.LoadFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private TextField address;

    @FXML
    private TextField codpostal;

    @FXML
    private TextField door;

    @FXML
    private TextField email;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private TextField phone;

    @FXML
    private TextField username;

    @FXML
    private Button editBtn;

    @FXML
    private Button removeBtn;

    @FXML
    private Button confirmBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void back(MouseEvent event) {
        if(LoginController.gestorVenda != null){
            LoadFXML.getInstance().loadResource("gestorvendamenu.fxml", "Menu Gestor de Venda", event);
        } else if(LoginController.gestorProducao != null){
            LoadFXML.getInstance().loadResource("gestorprodmenu.fxml", "Menu Gestor de Produção", event);
        } else if(LoginController.admin != null){
            LoadFXML.getInstance().loadResource("adminmenu.fxml", "Menu Admin", event);
        }
    }

    @FXML
    void editData() {
        fieldsEditable(true);
        editBtn.setVisible(false);
        removeBtn.setVisible(false);
        confirmBtn.setVisible(true);
        cancelBtn.setVisible(true);
    }

    @FXML
    void removeAcc(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remover conta");
        alert.setHeaderText("Tem a certeza que deseja remover a sua conta?");
        alert.showAndWait();
        if(alert.getResult().getText().equals("OK")){
            if(LoginController.gestorVenda != null){
                try {
                    GestorVendaBLL.remove(LoginController.gestorVenda);
                } catch (Exception e) {
                    alert("Erro ao remover a conta");
                    return;
                }
            } else if(LoginController.gestorProducao != null){
                try {
                    GestorProdBLL.remove(LoginController.gestorProducao);
                } catch (Exception e) {
                    alert("Erro ao remover a conta");
                    return;
                }
            } else if(LoginController.admin != null){
                try {
                    AdminBLL.remove(LoginController.admin);
                } catch (Exception e) {
                    alert("Erro ao remover a conta");
                    return;
                }
            }
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Sucesso");
            alert1.setHeaderText("Conta removida com sucesso");
            alert1.showAndWait();
            LoadFXML.getInstance().loadResource("login.fxml", "Login", event);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancelBtn.setVisible(false);
        confirmBtn.setVisible(false);
        fillFields();
        fieldsEditable(false);
    }

    private void fillFields(){
        if(LoginController.gestorVenda != null){
            setFields(LoginController.gestorVenda.getUtilizador());
        } else if(LoginController.gestorProducao != null){
            setFields(LoginController.gestorProducao.getUtilizador());
        } else if(LoginController.admin != null){
            setFields(LoginController.admin.getUtilizador());
        }
    }

    private void setFields(Utilizador user){
        username.setText(user.getUsername());
        password.setText(user.getPassword());
        name.setText(user.getNome());
        email.setText(user.getEmail());
        phone.setText(user.getTelefone());
        address.setText(user.getRua());
        door.setText(String.valueOf(user.getNumporta()));
        codpostal.setText(user.getCodpostal().getCodpostal());
    }

    private void fieldsEditable(boolean editable){
        username.setEditable(editable);
        password.setEditable(editable);
        name.setEditable(editable);
        email.setEditable(editable);
        phone.setEditable(editable);
        address.setEditable(editable);
        door.setEditable(editable);
        codpostal.setEditable(editable);
    }


    private void alert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void cancelChanges() {
        fieldsEditable(false);
        editBtn.setVisible(true);
        removeBtn.setVisible(true);
        confirmBtn.setVisible(false);
        cancelBtn.setVisible(false);
        fillFields();
    }

    public void confirmChanges() {

        boolean success = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Guardar alterações");
        alert.setHeaderText("Tem a certeza que deseja guardar as alterações?");
        alert.showAndWait();

        if(alert.getResult().getText().equals("OK")){
            if(LoginController.gestorVenda != null){
                setData(LoginController.gestorVenda.getUtilizador());

                try {
                    GestorVendaBLL.update(LoginController.gestorVenda);
                }catch (Exception e) {
                    alert("Erro ao guardar as alterações");
                    return;
                }

                success = true;

            } else if(LoginController.gestorProducao != null){
                setData(LoginController.gestorProducao.getUtilizador());

                try {
                    GestorProdBLL.update(LoginController.gestorProducao);
                }catch (Exception e) {
                    alert("Erro ao guardar as alterações");
                    return;
                }

                success = true;

            } else if(LoginController.admin != null){
                setData(LoginController.admin.getUtilizador());

                try {
                    AdminBLL.update(LoginController.admin);
                } catch (Exception e) {
                    alert("Erro ao guardar as alterações");
                    return;
                }

                success = true;
            }

            if(success){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Sucesso");
                alert1.setHeaderText("Alterações guardadas com sucesso");
                alert1.showAndWait();
                fieldsEditable(false);
                editBtn.setVisible(true);
                removeBtn.setVisible(true);
                confirmBtn.setVisible(false);
                cancelBtn.setVisible(false);
            }
        }

    }

    private void setData(Utilizador user){
        user.setUsername(username.getText());
        user.setPassword(password.getText());
        user.setNome(name.getText());
        user.setEmail(email.getText());
        user.setTelefone(phone.getText());
        user.setRua(address.getText());
        user.setNumporta(Integer.parseInt(door.getText()));
        Codpostal cp = new Codpostal();
        cp.setCodpostal(codpostal.getText());
        user.setCodpostal(cp);
    }
}
