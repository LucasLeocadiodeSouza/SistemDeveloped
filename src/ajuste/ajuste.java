package ajuste;

public class ajuste {
	
	private Integer id;
	private String acao;
	private String descricao;
	
	public ajuste(Integer id, String acao, String descricao) {
		this.id = id;
		this.acao = acao;
		this.descricao = descricao;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
