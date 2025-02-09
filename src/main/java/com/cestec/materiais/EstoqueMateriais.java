package  com.cestec.materiais;
import java.util.ArrayList;

public final class EstoqueMateriais {
	
	private Integer quantidade;
	private ArrayList<Material> materiais;
	private ArrayList<SolicitacaoMaterial> solicitacoes;
    
	public EstoqueMateriais() {
		materiais    = new ArrayList<Material>();
		solicitacoes = new ArrayList<SolicitacaoMaterial>();
	}
	
	// metodos private
	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}


	// metodos para classe EstoqueMat
	public void addMatEst(Material material) {
		materiais.add(material);
	}
	public void removeMatEst(Material material) {
		materiais.remove(material);
	}
	public Material verMat(int posicao) {
		return materiais.get(posicao);
	}
	public int lenghtMat() {
		return materiais.size();
	}
	public void addSolicitacao(SolicitacaoMaterial SolicitacaoMaterial) {
		solicitacoes.add(SolicitacaoMaterial);
	}
	public void removeSolicitacao(SolicitacaoMaterial SolicitacaoMaterial) {
		solicitacoes.remove(SolicitacaoMaterial);
	}
	
}
