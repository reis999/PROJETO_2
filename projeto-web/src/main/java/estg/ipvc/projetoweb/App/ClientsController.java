package estg.ipvc.projetoweb.App;

import estg.ipvc.projeto.data.Entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ClientsController {


    private final ClientService clientService;

    @Autowired
    public ClientsController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public String getAllClients(Model model) {
        List<Cliente> clients = clientService.getAllClients();
        model.addAttribute("clientes", clients);
        return "clients";
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "test"; // Esta view deve existir no diretório templates
    }
}

