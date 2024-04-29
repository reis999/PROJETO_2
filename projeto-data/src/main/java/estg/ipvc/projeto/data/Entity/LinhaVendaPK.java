package estg.ipvc.projeto.data.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.io.Serializable;

public class LinhaVendaPK implements Serializable {
    @Column(name = "id_venda", nullable = false)
    @Id
    private int idVenda;
    @Column(name = "id_lote", nullable = false)
    @Id
    private int idLote;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinhaVendaPK that = (LinhaVendaPK) o;

        if (idVenda != that.idVenda) return false;
        return idLote == that.idLote;
    }

    @Override
    public int hashCode() {
        int result = idVenda;
        result = 31 * result + idLote;
        return result;
    }
}
