package materiais;

import java.util.ArrayList;

public class Material {
	
	private String nome;
	private Tipo tipo;
	private EstoqueMateriais estoqueMateriais;
	
	private ArrayList<SolicitacaoMaterial> solicitacaoMateriais;
	
	public Material() {
		solicitacaoMateriais = new ArrayList<SolicitacaoMaterial>();
	}
		
	// Metodos para o private
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}	
	public EstoqueMateriais getEstoqueMateriais() {
		return estoqueMateriais;
	}
	public void setEstoqueMateriais(EstoqueMateriais estoqueMateriais) {
		this.estoqueMateriais = estoqueMateriais;
	}


	// Metodos para classe Material
	public void addMat(SolicitacaoMaterial solicitacao) {
		solicitacaoMateriais.add(solicitacao);
	}
	public void removerMat(SolicitacaoMaterial solicitacao) {
		solicitacaoMateriais.remove(solicitacao);
	}
	
}