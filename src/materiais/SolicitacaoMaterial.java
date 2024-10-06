package materiais;

import java.util.ArrayList;

public class SolicitacaoMaterial{

	private String setorSolicitante;
	private String nomeSolicitante;
	private String setorRequerente;
	private int quantidadeSolicitada;
	private EstoqueMateriais estoqueMaterial;
	
	private ArrayList<Material> materiais;
	
	public SolicitacaoMaterial() {
		materiais = new ArrayList<Material>();
	}
	
	// Metodos para private
	public String getSetorSolicitante() {
		return setorSolicitante;
	}
	public void setSetorSolicitante(String setorSolicitante) {
		this.setorSolicitante = setorSolicitante;
	}
	public String getNomeSolicitante() {
		return nomeSolicitante;
	}
	public void setNomeSolicitante(String nomeSolicitante) {
		this.nomeSolicitante = nomeSolicitante;
	}
	public String getSetorRequerente() {
		return setorRequerente;
	}
	public void setSetorRequerente(String setorRequerente) {
		this.setorRequerente = setorRequerente;
	}
	public int getQuantidadeSolicitada() {
		return quantidadeSolicitada;
	}
	public void setQuantidadeSolicitada(int quantidadeSolicitada) {
		this.quantidadeSolicitada = quantidadeSolicitada;
	}
	public EstoqueMateriais getEstoqueMaterial() {
		return estoqueMaterial;
	}
	public void setEstoqueMaterial(EstoqueMateriais estoqueMaterial) {
		this.estoqueMaterial = estoqueMaterial;
	}

	
	// Metodos para classe SolcitacaoMat
	public void fazerSolicitacao() {
		
	}
	
}
