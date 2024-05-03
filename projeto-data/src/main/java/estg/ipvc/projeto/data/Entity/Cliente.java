package estg.ipvc.projeto.data.Entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Cliente{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_cliente", nullable = false)
    private int idCliente;
    @Basic
    @Column(name = "nome_empresa", length = 100)
    private String nomeEmpresa;
    @Basic
    @Column(name = "nif", precision = 0)
    private int nif;
    @OneToMany(mappedBy = "cliente")
    private Collection<Venda> vendas;

    @OneToOne
    @JoinColumn(name = "id_user")
    private Utilizador utilizador;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        if (!super.equals(o)) return false;
        Cliente cliente = (Cliente) o;
        return idCliente == cliente.idCliente && nif == cliente.nif && Objects.equals(nomeEmpresa, cliente.nomeEmpresa) && Objects.equals(vendas, cliente.vendas) && Objects.equals(utilizador, cliente.utilizador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idCliente, nomeEmpresa, nif, vendas, utilizador);
    }

    public Collection<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(Collection<Venda> vendas) {
        this.vendas = vendas;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }
}
