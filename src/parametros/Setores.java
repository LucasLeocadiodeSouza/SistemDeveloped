package parametros;

public class Setores {
	private String nomeSetor;
	private Integer codSetor;
	
	public Setores(String nomeSetor, Integer codSetor) {
		this.nomeSetor = nomeSetor;
		this.codSetor = codSetor;
	}
	public Setores() {
	}

	public String getNomeSetor() {
		return nomeSetor;
	}
	public void setNomeSetor(String nomeSetor) {
		this.nomeSetor = nomeSetor;
	}
	public Integer getCodSetor() {
		return codSetor;
	}
	public void setCodSetor(Integer codSetor) {
		this.codSetor = codSetor;
	}
}
