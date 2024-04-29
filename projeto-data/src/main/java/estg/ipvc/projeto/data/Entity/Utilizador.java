package estg.ipvc.projeto.data.Entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Utilizador {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user", nullable = false)
    private int idUser;
    @Basic
    @Column(name = "username", nullable = false, length = 100)
    private String username;
    @Basic
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    @Basic
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    @Basic
    @Column(name = "email", nullable = true, length = 100)
    private String email;
    @Basic
    @Column(name = "telefone", nullable = true, length = 9)
    private String telefone;
    @Basic
    @Column(name = "rua", nullable = true, length = 100)
    private String rua;
    @Basic
    @Column(name = "numporta", nullable = true, precision = 0)
    private Integer numporta;
    @ManyToOne
    @JoinColumn(name = "codpostal")
    private Codpostal codpostal;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumporta() {
        return numporta;
    }

    public void setNumporta(Integer numporta) {
        this.numporta = numporta;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizador)) return false;
        Utilizador that = (Utilizador) o;
        return idUser == that.idUser &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(email, that.email) &&
                Objects.equals(telefone, that.telefone) &&
                Objects.equals(rua, that.rua) &&
                Objects.equals(numporta, that.numporta) &&
                Objects.equals(codpostal, that.codpostal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, username, password, nome, email, telefone, rua, numporta, codpostal);
    }

    public Codpostal getCodpostal() {
        return codpostal;
    }

    public void setCodpostal(Codpostal codpostal) {
        this.codpostal = codpostal;
    }

}
