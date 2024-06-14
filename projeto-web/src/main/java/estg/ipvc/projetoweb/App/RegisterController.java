package estg.ipvc.projetoweb.App;

import estg.ipvc.projeto.data.Entity.Cliente;
import estg.ipvc.projeto.data.Entity.Codpostal;
import estg.ipvc.projeto.data.Entity.Utilizador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class RegisterController {


    private final RegisterService userService;

    @Autowired
    public RegisterController(RegisterService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        Cliente cliente = new Cliente();
        Utilizador utilizador = new Utilizador();
        cliente.setUtilizador(utilizador);
        model.addAttribute("cliente", cliente);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        Codpostal codpostal = new Codpostal();
        codpostal.setCodpostal(cliente.getUtilizador().getCodpostal().getCodpostal());
        userService.registerClient(cliente, cliente.getUtilizador());
        return "redirect:/homeNotSignedIn";
    }
}

