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

	
	public Consolidacao(Integer id, String situacao, String descricao, Integer idSetor, Date dataReq) {
		this.id        = id;
		this.situacao  = situacao;
		this.descricao = descricao;
		this.setor     = new Setores(idSetor);
		this.dataReq   = dataReq;
	}
	public Consolidacao(String situacao, Integer id, String descricao, Integer idPrest, Date dataReq) {
		this.id        = id;
		this.situacao  = situacao;
		this.descricao = descricao;
		this.prest     = new prestadores(idPrest);
		this.dataReq   = dataReq;
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

	public String getSetor() {
		return setor.getNomeSetor();
	}
	
    public void setSetores(String Setores) {
		this.setor = new Setores(Setores);
	    this.setor.setNomeSetor(Setores);
	}
	public String getPrest() {
		return prest.getnomePrestador();
	}
	
    public void setPrestador(String prestadores) {
		this.prest = new prestadores(prestadores);
	    this.prest.setnomePrestador(prestadores);
	}
}
