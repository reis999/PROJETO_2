package estg.ipvc.projeto.data.Entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "tipo_cereal", schema = "public", catalog = "projeto1")
public class TipoCereal {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_tipo_cereal", nullable = false)
    private int idTipoCereal;
    @Basic
    @Column(name = "nome_cereal", nullable = false, length = 100)
    private String nomeCereal;
    @OneToMany(mappedBy = "tipoCereal")
    private Collection<Lote> lotes;

    public int getIdTipoCereal() {
        return idTipoCereal;
    }

    public void setIdTipoCereal(int idTipoCereal) {
        this.idTipoCereal = idTipoCereal;
    }

    public String getNomeCereal() {
        return nomeCereal;
    }

    public void setNomeCereal(String nomeCereal) {
        this.nomeCereal = nomeCereal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoCereal)) return false;
        TipoCereal that = (TipoCereal) o;
        return idTipoCereal == that.idTipoCereal && Objects.equals(nomeCereal, that.nomeCereal) && Objects.equals(lotes, that.lotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTipoCereal, nomeCereal, lotes);
    }

    public Collection<Lote> getLotes() {
        return lotes;
    }

    public void setLotes(Collection<Lote> lotes) {
        this.lotes = lotes;
    }
}
