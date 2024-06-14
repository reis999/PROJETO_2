package estg.ipvc.projetoweb.App;


import estg.ipvc.projeto.data.BLL.ClientBLL;
import estg.ipvc.projeto.data.Entity.Cliente;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public static Cliente currentClient;


    public boolean authenticateUser(String username, String password) {
        for(int i = 0; i < ClientBLL.listarClientes().size(); i++) {
            if(ClientBLL.listarClientes().get(i).getUtilizador().getUsername().equals(username) && ClientBLL.listarClientes().get(i).getUtilizador().getPassword().equals(password)) {
                currentClient = ClientBLL.listarClientes().get(i);
                return true;
            }
        }
        return false;
    }

}
