package estg.ipvc.projeto.data.BLL;

import estg.ipvc.projeto.data.Entity.*;
import jakarta.persistence.EntityManager;

import java.util.List;

public class GestorVendaBLL {

    public static void create(GestorVenda gv, Utilizador user) {

        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        gv.setUtilizador(user);
        em.persist(user);
        em.persist(gv);
        em.getTransaction().commit();
    }

    public static void update(GestorVenda gv) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        em.merge(gv);
        em.getTransaction().commit();
    }

    public static void remove(GestorVenda gv) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        Utilizador user = gv.getUtilizador();
        em.remove(gv);
        em.remove(user);
        em.getTransaction().commit();
    }

    public static void addVenda(GestorVenda gv, Venda venda, Cliente cli) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        venda.setGestorVenda(gv);
        venda.setCliente(cli);
        gv.getVendas().add(venda);
        em.persist(venda);
        em.getTransaction().commit();
    }

    public static void updateVenda(Venda venda) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        em.merge(venda);
        em.getTransaction().commit();
    }


    public static void removeVenda(GestorVenda gv, Venda venda, Cliente cli) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        Venda managedVenda = em.find(Venda.class, venda.getCliente());
        if (managedVenda != null) {
            gv.getVendas().remove(managedVenda);
            cli.getVendas().remove(managedVenda);
            em.remove(managedVenda);
        }
        em.getTransaction().commit();
    }

    public static void registerLinhaVenda(Venda venda, Lote lote, LinhaVenda lv) {
        venda.getLinhaVendas().add(lv);
        lote.getLinhaVendas().add(lv);
        lote.setQuantidade(lote.getQuantidade() - lv.getQuantidade());
        lv.setIdLote(lote.getIdLote());
        lv.setIdVenda(venda.getIdVenda());
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        em.persist(lv);
        em.getTransaction().commit();
    }

    public static List<Venda> listarVendas(GestorVenda gv) {
        return DBConnect.getEntityManager().createQuery("SELECT v FROM Venda v WHERE v.gestorVenda = :gestorVenda")
                .setParameter("gestorVenda", gv.getIdGestorVenda()).getResultList();
    }

    public static List<Cliente> listarClientes() {
        return DBConnect.getEntityManager().createQuery("SELECT c FROM Cliente c").getResultList();
    }

    public static List<Venda> listarVendasByCliente(Cliente cli) {
        return DBConnect.getEntityManager().createQuery("SELECT v FROM Venda v WHERE v.cliente = :cliente")
                .setParameter("cliente", cli.getIdCliente()).getResultList();
    }

}
