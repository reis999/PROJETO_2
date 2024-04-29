package estg.ipvc.projeto.data.Entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Cultivo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_cultivo", nullable = false, insertable = false, updatable = false)
    private int idCultivo;
    @Basic
    @Column(name = "tipo_cultivo", nullable = false, length = 100)
    private String tipoCultivo;
    @OneToMany(mappedBy = "cultivoByIdCultivo")
    private Collection<LoteCultivo> loteCultivo;

    public int getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(int idCultivo) {
        this.idCultivo = idCultivo;
    }

    public String getTipoCultivo() {
        return tipoCultivo;
    }

    public void setTipoCultivo(String tipoCultivo) {
        this.tipoCultivo = tipoCultivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cultivo cultivo = (Cultivo) o;

        if (idCultivo != cultivo.idCultivo) return false;
        return Objects.equals(tipoCultivo, cultivo.tipoCultivo);
    }

    @Override
    public int hashCode() {
        int result = idCultivo;
        result = 31 * result + (tipoCultivo != null ? tipoCultivo.hashCode() : 0);
        return result;
    }

    public Collection<LoteCultivo> getLoteCultivo() {
        return loteCultivo;
    }

    public void setLoteCultivo(Collection<LoteCultivo> loteCultivo) {
        this.loteCultivo = loteCultivo;
    }
}
