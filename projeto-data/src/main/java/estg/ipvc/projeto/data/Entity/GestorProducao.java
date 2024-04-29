package estg.ipvc.projeto.data.Entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "gestor_producao", schema = "public", catalog = "projeto1")
public class GestorProducao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_gestor_prod", nullable = false)
    private int idGestorProd;
    @OneToMany(mappedBy = "gestorProducao")
    private Collection<Lote> lotes;
    @OneToOne
    @JoinColumn(name = "id_user")
    private Utilizador utilizador;

    public int getIdGestorProd() {
        return idGestorProd;
    }

    public void setIdGestorProd(int idGestorProd) {
        this.idGestorProd = idGestorProd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GestorProducao)) return false;
        GestorProducao that = (GestorProducao) o;
        return idGestorProd == that.idGestorProd && Objects.equals(lotes, that.lotes) && Objects.equals(utilizador, that.utilizador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGestorProd, lotes, utilizador);
    }

    public Collection<Lote> getLotes() {
        return lotes;
    }

    public void setLotes(Collection<Lote> lotes) {
        this.lotes = lotes;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }
}
