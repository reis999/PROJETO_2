package estg.ipvc.projetoweb.App;

import estg.ipvc.projeto.data.Entity.TipoCereal;
import java.math.BigDecimal;


public class LoteDTO {
    private int idLote;
    private TipoCereal tipoCereal;
    private int quantidade;
    private BigDecimal precoUnidade;


    public TipoCereal getTipoCereal() {
        return tipoCereal;
    }

    public void setTipoCereal(TipoCereal tipoCereal) {
        this.tipoCereal = tipoCereal;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnidade() {
        return precoUnidade;
    }

    public void setPrecoUnidade(BigDecimal precoUnidade) {
        this.precoUnidade = precoUnidade;
    }



    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

}
