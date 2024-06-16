package estg.ipvc.projetoweb.App;

import estg.ipvc.projeto.data.BLL.DBConnect;
import estg.ipvc.projeto.data.BLL.GestorVendaBLL;
import estg.ipvc.projeto.data.Entity.*;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class OrderController {

    private final EntityManager em = DBConnect.getEntityManager();

    @GetMapping("/orders")
    public String showOrders(Model model, @RequestParam(value = "message", required = false) String message,
                             @RequestParam(value = "success", required = false) String success) {
        List<Lote> lotes = em.createQuery("SELECT l FROM Lote l", Lote.class).getResultList();
        List<LoteDTO> loteDTOs = lotes.stream().map(l -> {
            LoteDTO dto = new LoteDTO();
            dto.setIdLote(l.getIdLote());
            dto.setTipoCereal(l.getTipoCereal());
            dto.setQuantidade(l.getQuantidade());
            dto.setPrecoUnidade(l.getPrecoUnidade());
            return dto;
        }).collect(Collectors.toList());

        List<Transporte> transportes = em.createQuery("SELECT t FROM Transporte t", Transporte.class).getResultList();
        model.addAttribute("lotes", loteDTOs);
        model.addAttribute("transportes", transportes);
        model.addAttribute("venda", new Venda());
        model.addAttribute("message", message);
        model.addAttribute("success", success);
        return "orders";
    }

    @PostMapping("/orders")
    @Transactional
    public String placeOrder(@ModelAttribute("venda") Venda venda, @RequestParam Long loteId, @RequestParam int quantidade, @RequestParam String tipoTransporte) {
        try {
            Lote lote = em.createQuery("SELECT l FROM Lote l WHERE l.id = :id", Lote.class)
                    .setParameter("id", loteId)
                    .getSingleResult();

            Transporte transporte = em.createQuery("SELECT t FROM Transporte t WHERE t.tipo = :tipo", Transporte.class)
                    .setParameter("tipo", tipoTransporte)
                    .getSingleResult();

            if (quantidade > 0 && quantidade <= lote.getQuantidade()) {

                Cliente cliente = LoginService.currentClient;

                BigDecimal precoTotal = lote.getPrecoUnidade().multiply(new BigDecimal(quantidade));

                // Obter todos os gestores de venda
                List<GestorVenda> gestores = em.createQuery("SELECT g FROM GestorVenda g", GestorVenda.class).getResultList();
                Random rand = new Random();
                GestorVenda gestorAleatorio = gestores.get(rand.nextInt(gestores.size()));

                venda.setCliente(cliente);
                venda.setTransporteByIdTransporte(transporte);
                venda.setValor(precoTotal);
                venda.setGestorVenda(gestorAleatorio);
                venda.setEstado("PENDENTE");
                venda.setData(new Date(System.currentTimeMillis()));

                try {
                    GestorVendaBLL.addVenda(gestorAleatorio, venda, cliente);
                } catch (Exception e) {
                    return "redirect:/orders?message=Ocorreu um erro ao processar a encomenda!&success=false";
                }

                try {
                    LinhaVenda linhaVenda = new LinhaVenda();
                    linhaVenda.setQuantidade(quantidade);
                    GestorVendaBLL.registerLinhaVenda(venda, lote, linhaVenda);
                } catch (Exception e) {
                    return "redirect:/orders?message=Ocorreu um erro ao processar a encomenda!&success=false";
                }

                return "redirect:/orders?message=Encomenda realizada com sucesso!&success=true";

            } else {
                return "redirect:/orders?message=Quantidade invalida!&success=false";
            }
        } catch (Exception e) {
            return "redirect:/orders?message=Ocorreu um erro ao processar a encomenda!&success=false";
        }
    }
}

