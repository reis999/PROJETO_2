package estg.ipvc.projeto.data.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "linha_venda", schema = "public", catalog = "projeto1")
@IdClass(LinhaVendaPK.class)
public class LinhaVenda {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_venda", nullable = false)
    private int idVenda;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_lote", nullable = false)
    private int idLote;
    @Basic
    @Column(name = "quantidade", nullable = false, precision = 0)
    private int quantidade;
    @ManyToOne
    @JoinColumn(name = "id_venda", referencedColumnName = "id_venda", nullable = false, insertable = false, updatable = false)
    private Venda vendaByIdVenda;
    @ManyToOne
    @JoinColumn(name = "id_lote", referencedColumnName = "id_lote", nullable = false, insertable = false, updatable = false)
    private Lote loteByIdLote;

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
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

        LinhaVenda that = (LinhaVenda) o;

        if (idVenda != that.idVenda) return false;
        if (idLote != that.idLote) return false;
        return quantidade == that.quantidade;
    }

    @Override
    public int hashCode() {
        int result = idVenda;
        result = 31 * result + idLote;
        result = 31 * result + quantidade;
        return result;
    }

    public Venda getVendaByIdVenda() {
        return vendaByIdVenda;
    }

    public void setVendaByIdVenda(Venda vendaByIdVenda) {
        this.vendaByIdVenda = vendaByIdVenda;
    }

    public Lote getLoteByIdLote() {
        return loteByIdLote;
    }

    public void setLoteByIdLote(Lote loteByIdLote) {
        this.loteByIdLote = loteByIdLote;
    }


}
