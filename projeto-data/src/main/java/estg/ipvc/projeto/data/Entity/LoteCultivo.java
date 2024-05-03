package estg.ipvc.projeto.data.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "lote_cultivo", schema = "public", catalog = "projeto1")
@IdClass(LoteCultivoPK.class)
public class LoteCultivo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_lote", nullable = false)
    private int idLote;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_cultivo", nullable = false)
    private int idCultivo;
    @Basic
    @Column(name = "quantidade", precision = 2)
    private int quantidade;
    @ManyToOne
    @JoinColumn(name = "id_lote", referencedColumnName = "id_lote", nullable = false, insertable = false, updatable = false)
    private Lote loteByIdLote;
    @ManyToOne
    @JoinColumn(name = "id_cultivo", referencedColumnName = "id_cultivo", nullable = false, insertable = false, updatable = false)
    private Cultivo cultivoByIdCultivo;

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

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoteCultivo that = (LoteCultivo) o;

        if (idLote != that.idLote) return false;
        if (idCultivo != that.idCultivo) return false;
        return Objects.equals(quantidade, that.quantidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLote, idCultivo, quantidade, loteByIdLote, cultivoByIdCultivo);
    }

    public Lote getLoteByIdLote() {
        return loteByIdLote;
    }

    public void setLoteByIdLote(Lote loteByIdLote) {
        this.loteByIdLote = loteByIdLote;
    }

    public Cultivo getCultivoByIdCultivo() {
        return cultivoByIdCultivo;
    }

    public void setCultivoByIdCultivo(Cultivo cultivoByIdCultivo) {
        this.cultivoByIdCultivo = cultivoByIdCultivo;
    }
}
