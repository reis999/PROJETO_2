package estg.ipvc.projetoweb.App;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Lógica personalizada pode ser adicionada aqui
        return "error"; // Nome do template (error.html) sem a extensão
    }
}

