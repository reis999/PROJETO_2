package estg.ipvc.projeto.data.BLL;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DBConnect {
    private final static EntityManagerFactory factory = Persistence.createEntityManagerFactory("projeto");
    private final static EntityManager em = factory.createEntityManager();
    public static EntityManager getEntityManager() {return em;}

}
