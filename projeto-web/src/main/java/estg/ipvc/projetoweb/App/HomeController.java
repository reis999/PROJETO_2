package estg.ipvc.projetoweb.App;

import org.apache.juli.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    String index() {
        return "homeNotSignedIn";
    }

    @GetMapping("/homeSignedIn")
    public String homeSignedIn(Model model) {
        if (LoginService.currentClient != null) {
            model.addAttribute("clientName", LoginService.currentClient.getUtilizador().getNome());
            return "homeSignedIn";
        }
        return "redirect:/login";
    }
}