package estg.ipvc.projetoweb.App;

import estg.ipvc.projeto.data.BLL.ClientBLL;
import estg.ipvc.projeto.data.Entity.Cliente;
import estg.ipvc.projeto.data.Entity.Utilizador;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    public void registerClient(Cliente cli, Utilizador user){
        ClientBLL.create(cli, user);
    }

}
