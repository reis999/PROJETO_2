package estg.ipvc.projeto.data.Entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Admin {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_admin", nullable = false)
    private int idAdmin;
    @OneToOne
    @JoinColumn(name = "id_user")
    private Utilizador utilizador;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admin)) return false;
        Admin admin = (Admin) o;
        return idAdmin == admin.idAdmin && Objects.equals(utilizador, admin.utilizador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAdmin, utilizador);
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }
}
