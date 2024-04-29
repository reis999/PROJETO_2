package estg.ipvc.projeto.data.Entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "gestor_venda", schema = "public", catalog = "projeto1")
public class GestorVenda{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_gestor_venda", nullable = false)
    private int idGestorVenda;
    @OneToMany(mappedBy = "gestorVenda")
    private Collection<Venda> vendas;
    @OneToOne
    @JoinColumn(name = "id_user")
    private Utilizador utilizador;

    public int getIdGestorVenda() {
        return idGestorVenda;
    }

    public void setIdGestorVenda(int idGestorVenda) {
        this.idGestorVenda = idGestorVenda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GestorVenda)) return false;
        GestorVenda that = (GestorVenda) o;
        return idGestorVenda == that.idGestorVenda && Objects.equals(vendas, that.vendas) && Objects.equals(utilizador, that.utilizador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGestorVenda, vendas, utilizador);
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
