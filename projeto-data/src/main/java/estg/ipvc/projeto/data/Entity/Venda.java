package estg.ipvc.projeto.data.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Venda {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_venda", nullable = false)
    private int idVenda;
    @Basic
    @Column(name = "data", nullable = false)
    private Date data;
    @Basic
    @Column(name = "estado", nullable = false, length = 50)
    private String estado;
    @Basic
    @Column(name = "valor", precision = 2)
    private BigDecimal valor;
    @OneToMany(mappedBy = "vendaByIdVenda", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Collection<LinhaVenda> linhaVendas;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "id_gestor_venda")
    private GestorVenda gestorVenda;
    @ManyToOne
    @JoinColumn(name = "id_transporte")
    private Transporte transporte;

    public Venda() {
        linhaVendas = new ArrayList<>();
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Venda venda)) return false;
        return idVenda == venda.idVenda &&
                Objects.equals(data, venda.data) &&
                Objects.equals(estado, venda.estado) &&
                Objects.equals(linhaVendas, venda.linhaVendas) &&
                Objects.equals(cliente, venda.cliente) &&
                Objects.equals(gestorVenda, venda.gestorVenda) &&
                Objects.equals(transporte, venda.transporte);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVenda, data, estado, linhaVendas, cliente, gestorVenda, transporte);
    }

    public Collection<LinhaVenda> getLinhaVendas() {
        return linhaVendas;
    }

    public void setLinhaVendas(Collection<LinhaVenda> linhaVendas) {
        this.linhaVendas = linhaVendas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public GestorVenda getGestorVenda() {
        return gestorVenda;
    }

    public void setGestorVenda(GestorVenda gestorVenda) {
        this.gestorVenda = gestorVenda;
    }

    public Transporte getTransporte() {
        return transporte;
    }


    public void setTransporteByIdTransporte(Transporte transporte) {
        this.transporte = transporte;
    }
}
