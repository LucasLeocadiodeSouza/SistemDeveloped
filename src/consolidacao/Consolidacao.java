package consolidacao;

import parametros.Setores;

public class Consolidacao {
	private Integer id;
	private String situacao;
	private String descricao;	
	private Setores setor;

	
	public Consolidacao(Integer id, String situacao, String descricao, String nomeSetor) {
		this.id = id;
		this.situacao = situacao;
		this.descricao = descricao;
		this.setor = new Setores(nomeSetor);
		this.setor.setNomeSetor(nomeSetor);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSetor() {
		return setor.getNomeSetor();
	}
	
    public void setSetores(String Setores) {
		this.setor = new Setores(Setores);
	    this.setor.setNomeSetor(Setores);
	}
}
