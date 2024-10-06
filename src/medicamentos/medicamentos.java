package medicamentos;

import java.sql.Date;

public class medicamentos {
	
	private int idMed;
	private String nomeMed;
	private Date validade;
    private classificacao classificacao;
    private lote lote;
    private marca marca;
	
	public medicamentos(int idMed, String nomeMed, Date validade, String nomeClassificacao, String codLote, String nomeMarca) {
		this.idMed = idMed;
		this.nomeMed = nomeMed;
		this.validade = validade;
		classificacao classificacao = new classificacao();
		classificacao.setNomeClassificacao(nomeClassificacao);
		lote lote = new lote();
		lote.setCodLote(codLote);
		marca marca = new marca();
		marca.setNomeMarca(nomeMarca);
	}
	
    public String getCodLote() {
        return lote.getCodLote();
    }

    public String getNomeClassificacao() {
        return classificacao.getNomeClassificacao();
    }

    public String getNomeMarca() {
        return marca.getNomeMarca();
    }
	
	public String getNomeMed() {
		return nomeMed;
	}
	public void setNomeMed(String nomeMed) {
		this.nomeMed = nomeMed;
	}
	public Date getValidade() {
		return validade;
	}
	public void setValidade(Date validade) {
		this.validade = validade;
	}

	public int getIdmed() {
		return idMed;
	}

	public void setIdmed(int idmed) {
		this.idMed = idmed;
	}
}