package estg.ipvc.projeto.data.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

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
    @Column(name = "quantidade", nullable = true, precision = 2)
    private BigDecimal quantidade;
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

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoteCultivo that = (LoteCultivo) o;

        if (idLote != that.idLote) return false;
        if (idCultivo != that.idCultivo) return false;
        if (quantidade != null ? !quantidade.equals(that.quantidade) : that.quantidade != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idLote;
        result = 31 * result + idCultivo;
        result = 31 * result + (quantidade != null ? quantidade.hashCode() : 0);
        return result;
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
