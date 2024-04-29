package estg.ipvc.projeto.data.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class LoteCultivoPK implements Serializable {

    @Id
    @Column(name = "id_lote", nullable = false)
    private int idLote;

    @Id
    @Column(name = "id_cultivo", nullable = false)
    private int idCultivo;

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public int getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(int idCultivo) {
        this.idCultivo = idCultivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoteCultivoPK)) return false;
        LoteCultivoPK that = (LoteCultivoPK) o;
        return idLote == that.idLote && idCultivo == that.idCultivo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLote, idCultivo);
    }
}
