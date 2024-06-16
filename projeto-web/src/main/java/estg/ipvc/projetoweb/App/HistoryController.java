package estg.ipvc.projetoweb.App;

import estg.ipvc.projeto.data.BLL.DBConnect;
import estg.ipvc.projeto.data.Entity.Cliente;
import estg.ipvc.projeto.data.Entity.Venda;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HistoryController {

    private final EntityManager em = DBConnect.getEntityManager();

    @GetMapping("/history")
    public String showHistory(Model model) {
        Cliente cliente = LoginService.currentClient;
        List<Venda> vendas = em.createQuery("SELECT v FROM Venda v WHERE v.cliente.id = :clienteId", Venda.class)
                .setParameter("clienteId", cliente.getIdCliente())
                .getResultList();

        List<VendaDTO> vendaDTOs = vendas.stream().map(v -> {
            VendaDTO dto = new VendaDTO();
            dto.setIdVenda(v.getIdVenda());
            dto.setData(v.getData());
            dto.setEstado(v.getEstado());
            dto.setValor(v.getValor());
            return dto;
        }).collect(Collectors.toList());

        for(int i = 0; i < vendaDTOs.size(); i++) {
            VendaDTO vendaDTO = vendaDTOs.get(i);
            System.out.println("Venda " + i + ": " + vendaDTO.getIdVenda() + " " + vendaDTO.getData() + " " + vendaDTO.getEstado() + " " + vendaDTO.getValor());
        }

        model.addAttribute("vendas", vendaDTOs);
        return "history";
    }
}
