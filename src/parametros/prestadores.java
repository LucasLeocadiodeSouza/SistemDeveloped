package parametros;

public class prestadores {
	private String nomePrestador;
	private Integer codPrestador;
	
	public prestadores(String nomePrestador, Integer codPrestador) {
		this.nomePrestador = nomePrestador;
		this.codPrestador = codPrestador;
	}
	public prestadores() {
	}


	public String getnomePrestador() {
		return nomePrestador;
	}
	public void setnomePrestador(String nomePrestador) {
		this.nomePrestador = nomePrestador;
	}
	public Integer getcodPrestador() {
		return codPrestador;
	}
	public void setcodPrestador(Integer codPrestador) {
		this.codPrestador = codPrestador;
	}
}
