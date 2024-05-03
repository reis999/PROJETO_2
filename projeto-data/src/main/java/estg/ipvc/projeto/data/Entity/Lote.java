package estg.ipvc.projeto.data.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Lote {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_lote", nullable = false)
    private int idLote;
    @Basic
    @Column(name = "preco_unidade", nullable = false, precision = 2)
    private BigDecimal precoUnidade;
    @Basic
    @Column(name = "quantidade", nullable = false, precision = 0)
    private int quantidade;
    @Basic
    @Column(name = "data_inicio", nullable = false)
    private Date dataInicio;
    @Basic
    @Column(name = "data_recolha", nullable = false)
    private Date dataRecolha;
    @OneToMany(mappedBy = "loteByIdLote")
    private Collection<LinhaVenda> linhaVendas;
    @ManyToOne
    @JoinColumn(name = "id_gestor_prod")
    private GestorProducao gestorProducao;
    @ManyToOne
    @JoinColumn(name = "id_tipo_cereal")
    private TipoCereal tipoCereal;
    @OneToMany(mappedBy = "loteByIdLote")
    private Collection<LoteCultivo> loteCultivos;

    public Lote() {
        linhaVendas = new ArrayList<>();
        loteCultivos = new ArrayList<>();
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public BigDecimal getPrecoUnidade() {
        return precoUnidade;
    }

    public void setPrecoUnidade(BigDecimal precoUnidade) {
        this.precoUnidade = precoUnidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataRecolha() {
        return dataRecolha;
    }

    public void setDataRecolha(Date dataRecolha) {
        this.dataRecolha = dataRecolha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lote)) return false;
        Lote lote = (Lote) o;
        return idLote == lote.idLote &&
                quantidade == lote.quantidade &&
                Objects.equals(precoUnidade, lote.precoUnidade) &&
                Objects.equals(dataInicio, lote.dataInicio) &&
                Objects.equals(dataRecolha, lote.dataRecolha) &&
                Objects.equals(linhaVendas, lote.linhaVendas) &&
                Objects.equals(gestorProducao, lote.gestorProducao) &&
                Objects.equals(tipoCereal, lote.tipoCereal) &&
                Objects.equals(loteCultivos, lote.loteCultivos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLote, precoUnidade, quantidade, dataInicio, dataRecolha, linhaVendas, gestorProducao, tipoCereal, loteCultivos);
    }

    public Collection<LinhaVenda> getLinhaVendas() {
        return linhaVendas;
    }

    public void setLinhaVendas(Collection<LinhaVenda> linhaVendas) {
        this.linhaVendas = linhaVendas;
    }

    public GestorProducao getGestorProducao() {
        return gestorProducao;
    }

    public void setGestorProducao(GestorProducao gestorProducao) {
        this.gestorProducao = gestorProducao;
    }

    public TipoCereal getTipoCereal() {
        return tipoCereal;
    }

    public void setTipoCereal(TipoCereal tipoCereal) {
        this.tipoCereal = tipoCereal;
    }

    public Collection<LoteCultivo> getLoteCultivos() {
        return loteCultivos;
    }



    public void setLoteCultivos(Collection<LoteCultivo> loteCultivos) {
        this.loteCultivos = loteCultivos;
    }
}
