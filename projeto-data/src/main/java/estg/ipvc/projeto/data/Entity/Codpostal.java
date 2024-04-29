package estg.ipvc.projeto.data.Entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Codpostal {
    @Id
    @Column(name = "codpostal", nullable = false, length = 10)
    private String codpostal;
    @Basic
    @Column(name = "localidade", nullable = false, length = 50)
    private String localidade;

    public String getCodpostal() {
        return codpostal;
    }

    public void setCodpostal(String codpostal) {
        this.codpostal = codpostal;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Codpostal codpostal1 = (Codpostal) o;

        if (!Objects.equals(codpostal, codpostal1.codpostal)) return false;
        return Objects.equals(localidade, codpostal1.localidade);
    }

    @Override
    public int hashCode() {
        int result = codpostal != null ? codpostal.hashCode() : 0;
        result = 31 * result + (localidade != null ? localidade.hashCode() : 0);
        return result;
    }

}
