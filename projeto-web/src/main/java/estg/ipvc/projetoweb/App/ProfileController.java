package estg.ipvc.projetoweb.App;

import estg.ipvc.projeto.data.BLL.ClientBLL;
import estg.ipvc.projeto.data.BLL.DBConnect;
import estg.ipvc.projeto.data.Entity.Cliente;
import estg.ipvc.projeto.data.Entity.Utilizador;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

    private final EntityManager em = DBConnect.getEntityManager();

    @GetMapping("/profile")
    public String profile(Model model) {

        Cliente cli;
        if(em.createQuery("SELECT c FROM Cliente c WHERE c.utilizador.id = :id", Cliente.class)
                .setParameter("id", LoginService.currentClient.getUtilizador().getIdUser())
                .getResultList().isEmpty()) {
            cli = new Cliente();
            cli.setUtilizador(LoginService.currentClient.getUtilizador());
        } else {
            cli = em.createQuery("SELECT c FROM Cliente c WHERE c.utilizador.id = :id", Cliente.class)
                    .setParameter("id", LoginService.currentClient.getUtilizador().getIdUser())
                    .getSingleResult();
        }
        model.addAttribute("user", cli);
        return "profile";
    }

    @PostMapping("/profile")
    @Transactional
    public String updateProfile(@ModelAttribute("cliente") Cliente cliente) {
        Cliente cli = em.createQuery("SELECT c FROM Cliente c WHERE c.utilizador.id = :id", Cliente.class)
                .setParameter("id", LoginService.currentClient.getUtilizador().getIdUser())
                .getSingleResult();


        if (cli == null) {
            return "redirect:/profile";
        }

        Utilizador user = cli.getUtilizador();
        user.setNome(cliente.getUtilizador().getNome());
        user.setEmail(cliente.getUtilizador().getEmail());
        user.setTelefone(cliente.getUtilizador().getTelefone());
        user.setRua(cliente.getUtilizador().getRua());
        user.setNumporta(cliente.getUtilizador().getNumporta());
        user.setCodpostal(cliente.getUtilizador().getCodpostal());
        cli.setNif(cliente.getNif());
        cli.setNomeEmpresa(cliente.getNomeEmpresa());

        cli.getUtilizador().setUsername(cliente.getUtilizador().getUsername());
        cli.getUtilizador().setPassword(cliente.getUtilizador().getPassword());

        ClientBLL.update(cli);

        return "redirect:/profile";
    }

}
