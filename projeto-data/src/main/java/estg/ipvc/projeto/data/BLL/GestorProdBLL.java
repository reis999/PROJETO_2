package estg.ipvc.projeto.data.BLL;

import estg.ipvc.projeto.data.Entity.*;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class GestorProdBLL {

    public static void create(Utilizador user, GestorProducao gp) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        gp.setUtilizador(user);
        em.persist(user);
        em.persist(gp);
        em.getTransaction().commit();
    }

    public static void update(GestorProducao gp) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        em.merge(gp);
        em.getTransaction().commit();
    }


    public static void remove(GestorProducao gp) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        Utilizador user = gp.getUtilizador();
        em.remove(gp);
        em.remove(user);
        em.getTransaction().commit();
    }

    public static List<GestorProducao> listar() {
        return DBConnect.getEntityManager().createQuery("from GestorProducao").getResultList();
    }

    public static void addLote(GestorProducao gp, Lote lote) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        lote.setGestorProducao(gp);
        gp.getLotes().add(lote);
        em.persist(lote);
        em.getTransaction().commit();
    }

    public static void updateLote(Lote lote) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        em.merge(lote);
        em.getTransaction().commit();
    }


    public static void removeLote(GestorProducao gp, Lote lote) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        gp.getLotes().remove(lote);
        Collection<LoteCultivo> loteCultivos = lote.getLoteCultivos();
        for (LoteCultivo lc : loteCultivos) {
            em.remove(lc);
        }
        em.remove(lote);
        em.getTransaction().commit();
    }

    public static void addCultivo(Cultivo cultivo) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        em.persist(cultivo);
        em.getTransaction().commit();
    }

    public static void updateCultivo(Cultivo cultivo) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        em.merge(cultivo);
        em.getTransaction().commit();
    }

    public static void removeCultivo(Cultivo cultivo) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        em.remove(cultivo);
        em.getTransaction().commit();
    }

    public static void addTipoCereal(TipoCereal tipoCereal) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        em.persist(tipoCereal);
        em.getTransaction().commit();
    }

    public static void updateTipoCereal(TipoCereal tipoCereal) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        em.merge(tipoCereal);
        em.getTransaction().commit();
    }

    public static void removeTipoCereal(TipoCereal tipoCereal) {
        EntityManager em = DBConnect.getEntityManager();
        em.getTransaction().begin();
        em.remove(tipoCereal);
        em.getTransaction().commit();
    }

    public static void registerLoteCultivo(Lote lote, Cultivo cultivo, LoteCultivo lc) {
        try {
            EntityManager em = DBConnect.getEntityManager();

            lote.getLoteCultivos().add(lc);
            cultivo.getLoteCultivo().add(lc);

            lc.setQuantidade(lote.getQuantidade());
            lc.setIdLote(lote.getIdLote());
            lc.setIdCultivo(cultivo.getIdCultivo());
            lc.setLoteByIdLote(lote);
            lc.setCultivoByIdCultivo(cultivo);

            em.getTransaction().begin();
            em.persist(lc);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
