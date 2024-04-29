package estg.ipvc.projeto.data.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Transporte {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_transporte", nullable = false)
    private int idTransporte;
    @Basic
    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;
    @Basic
    @Column(name = "custo", nullable = false, precision = 2)
    private BigDecimal custo;
    @Basic
    @Column(name = "tempo_entrega", nullable = false, length = 10)
    private String tempoEntrega;
    @OneToMany(mappedBy = "transporte")
    private Collection<Venda> vendas;

    public int getIdTransporte() {
        return idTransporte;
    }

    public void setIdTransporte(int idTransporte) {
        this.idTransporte = idTransporte;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getCusto() {
        return custo;
    }

    public void setCusto(BigDecimal custo) {
        this.custo = custo;
    }

    public String getTempoEntrega() {
        return tempoEntrega;
    }

    public void setTempoEntrega(String tempoEntrega) {
        this.tempoEntrega = tempoEntrega;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transporte)) return false;
        Transporte that = (Transporte) o;
        return idTransporte == that.idTransporte && Objects.equals(tipo, that.tipo) && Objects.equals(custo, that.custo) && Objects.equals(tempoEntrega, that.tempoEntrega) && Objects.equals(vendas, that.vendas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransporte, tipo, custo, tempoEntrega, vendas);
    }

    public Collection<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(Collection<Venda> vendas) {
        this.vendas = vendas;
    }
}
