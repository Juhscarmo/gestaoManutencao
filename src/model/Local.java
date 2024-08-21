package model;

public class Local {
	
	//Atributos
	private String nomeLocal;
	private String equipeLocal;
	
	//Getters e Setters
	public String getLocal() {
		return nomeLocal;
	}
	public void setLocal(String local) {
		this.nomeLocal = local;
	}
	public String getEquipe() {
		return equipeLocal;
	}
	public void setEquipe(String equipe) {
		this.equipeLocal = equipe;
	}
	
	//Para listar no JComboBox
	public String toString() {
		return nomeLocal + " - " + equipeLocal;
	}
	
}
