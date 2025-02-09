package  com.cestec.materiais;

import java.util.ArrayList;

public class Tipo {
	
	private String nome;
	private ArrayList<Material> materiais;
	
	public Tipo() {
		materiais = new ArrayList<Material>();
	}

	// Metodos para o private
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	public void addMaterial(Material material) {
		materiais.add(material);
	}
}
