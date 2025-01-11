package consolidacao;
import java.sql.Date;
import parametros.Setores;
import parametros.prestadores;

public class Consolidacao {
	private Integer id;
	private String situacao;
	private String descricao;	
	private Setores setor;
	private Date dataReq;
	private prestadores prest;
	
	public Consolidacao(Integer id, String situacao, String descricao, String nomeSetor, Date dataReq) {
		this.id        = id;
		this.situacao  = situacao;
		this.descricao = descricao;
		this.setor     = new Setores();
		setNomeSetor(nomeSetor);
		this.dataReq   = dataReq;
	}
	public Consolidacao(String situacao, Integer id, String descricao, String nomePrest, Date dataReq) {
		this.id        = id;
		this.situacao  = situacao;
		this.descricao = descricao;
		this.prest = new prestadores();
		setNomePrest(nomePrest);
		this.dataReq   = dataReq;
	}

	public String getNomeSetor() {
		return this.setor.getNomeSetor();
	}
	
	public void setNomeSetor(String setor) {
	    this.setor.setNomeSetor(setor);
	}

	public String getNomePrest() {
		return this.prest.getnomePrestador();
	}

    public void setNomePrest(String prestador){
		this.prest.setnomePrestador(prestador);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataReq() {
		return dataReq;
	}

	public void setDataReq(Date dataReq) {
		this.dataReq = dataReq;
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
	
}
