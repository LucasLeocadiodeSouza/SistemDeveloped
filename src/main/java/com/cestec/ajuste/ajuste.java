package  com.cestec.ajuste;

import com.cestec.parametros.prm001;

public class ajuste {
	
	private Integer id;
	private Integer acao;
	private String descricao;
	
	public ajuste(Integer id, Integer acao, String descricao) {
		this.id = id;
		this.acao = acao;
		this.descricao = descricao;
	}

	public String getAcao() {
		return prm001.getAcaoAjuste(acao);
	}

	public void setAcao(Integer acao) {
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
