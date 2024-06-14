package estg.ipvc.projetoweb.App;

import estg.ipvc.projeto.data.BLL.ClientBLL;
import estg.ipvc.projeto.data.Entity.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    public List<Cliente> getAllClients() {
        return ClientBLL.listarClientes();
    }
}
