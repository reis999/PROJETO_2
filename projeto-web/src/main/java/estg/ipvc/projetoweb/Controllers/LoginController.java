package estg.ipvc.projetoweb.Controllers;


import estg.ipvc.projeto.data.Entity.Cliente;
import estg.ipvc.projetoweb.Services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LoginController {

    private ClientService clientService;

    @Autowired
    public LoginController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        List<Cliente> clientes = clientService.getAllClients();
        model.addAttribute("clientes", clientes);
        return "clientes-list";
    }


}

