package estg.ipvc.projeto.data.BLL;

import estg.ipvc.projeto.data.Entity.Cliente;
import estg.ipvc.projeto.data.Entity.Utilizador;
import estg.ipvc.projeto.data.Entity.Venda;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Objects;

public class ClientBLL {

    public static void create(Cliente cli, Utilizador user) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        cli.setUtilizador(user);
        em.persist(user);
        em.persist(cli);
        em.getTransaction().commit();
    }

    public static void update(Cliente cli) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        em.merge(cli);
        em.getTransaction().commit();
    }

    public static void remove(Cliente cli) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        Utilizador user = cli.getUtilizador();
        em.remove(cli);
        em.remove(user);
        em.getTransaction().commit();
    }

    public static List<Cliente> listarClientes() {
        return DBConnect.getEntityManager().createQuery("from Cliente").getResultList();
    }

    public  static List<Venda> listarVendas(Cliente cli){
        return DBConnect.getEntityManager().createQuery("SELECT v FROM Venda v WHERE v.cliente = :cliente")
                .setParameter("cliente", cli.getIdCliente()).getResultList();
    }


}
