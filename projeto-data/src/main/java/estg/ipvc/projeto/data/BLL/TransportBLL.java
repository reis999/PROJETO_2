package estg.ipvc.projeto.data.BLL;

import estg.ipvc.projeto.data.Entity.Transporte;
import jakarta.persistence.EntityManager;

public class TransportBLL {

    public static void create(Transporte t) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
    }

    public static void remove(Transporte t) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        em.remove(t);
        em.getTransaction().commit();
    }

    public static void update(Transporte t) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        em.merge(t);
        em.getTransaction().commit();
    }

    public static Transporte getTransporte(int id) {
        EntityManager em = DBConnect.getEntityManager();
        return em.find(Transporte.class, id);
    }
}
