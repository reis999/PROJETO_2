package estg.ipvc.projeto.data.BLL;

import estg.ipvc.projeto.data.Entity.Admin;
import estg.ipvc.projeto.data.Entity.Utilizador;
import jakarta.persistence.EntityManager;

public class AdminBLL {
    public static void create(Admin admin, Utilizador user){
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        admin.setUtilizador(user);
        em.persist(user);
        em.persist(admin);
        em.getTransaction().commit();
    }

    public static void update(Admin admin){
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        em.merge(admin);
        em.getTransaction().commit();
    }

    public static void remove(Admin admin){
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        Utilizador user = admin.getUtilizador();
        em.remove(admin);
        em.remove(user);
        em.getTransaction().commit();
    }


    public static Admin getAdmin(int id){
        EntityManager em = DBConnect.getEntityManager();
        return em.find(Admin.class, id);
    }



}
