package medicamentos;

import java.sql.Date;

public class medicamentos {
	
	private Integer idMed;
	private String nomeMed;
	private String medida;
	private Integer quantidade;
	private Date validade;
    private classificacao classificacao;
    private lote lote;
    private marca marca;
	
	public medicamentos(Integer idMed, Integer quantidade, String nomeMed, Date validade, String nomeClassificacao, String codLote, String nomeMarca) {

		this.idMed = idMed;
		this.quantidade = quantidade;
		this.nomeMed = nomeMed;
		this.validade = validade;
		
	    this.classificacao = new classificacao();
	    this.classificacao.setNomeClassificacao(nomeClassificacao);
	    
	    this.lote = new lote();
	    this.lote.setCodLote(codLote);
	    
	    this.marca = new marca();
	    this.marca.setNomeMarca(nomeMarca);
	}
	public medicamentos(Integer idMed, String nomeMed, Integer quantidade, Date validade, String nomeClassificacao, String codLote) {

		this.idMed = idMed;
		this.nomeMed = nomeMed;
		this.quantidade = quantidade;
		this.validade = validade;
		
	    this.classificacao = new classificacao();
	    this.classificacao.setNomeClassificacao(nomeClassificacao);
	    
	    this.lote = new lote();
	    this.lote.setCodLote(codLote);
	}
	public medicamentos(Integer idMed,  Integer quantidade, Date validade, String nomeClassificacao, String codLote) {

		this.idMed = idMed;
		this.quantidade = quantidade;
		this.validade = validade;
		
	    this.classificacao = new classificacao();
	    this.classificacao.setNomeClassificacao(nomeClassificacao);
	    
	    this.lote = new lote();
	    this.lote.setCodLote(codLote);
	}
	public medicamentos(String nomeMed, Integer quantidade, String medida, Integer idMed, String nomeClassificacao) {

		this.nomeMed = nomeMed;
		this.idMed = idMed;
		this.quantidade = quantidade;
		this.medida = medida;
		
	    this.classificacao = new classificacao();
	    this.classificacao.setNomeClassificacao(nomeClassificacao);
	}

    public String getNomeClassificacao() {
        return classificacao.getNomeClassificacao();
    }
    public void setNomeClassificacao(String classificacao) {
		this.classificacao = new classificacao();
	    this.classificacao.setNomeClassificacao(classificacao);
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
	public String getMedida() {
		return medida;
	}
	public void setMedida(String medida) {
		this.medida = medida;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Date getValidade() {
		return validade;
	}
	public void setValidade(Date validade) {
		this.validade = validade;
	}
	public String getCodLote() {
        return lote.getCodLote();
    }
	public void setLote(String codLote) {
		this.lote = new lote();
	    this.lote.setCodLote(codLote);
	}
	public Integer getIdMed() {
		return idMed;
	}

	public void setIdMed(Integer idMed) {
		this.idMed = idMed;
	}
}