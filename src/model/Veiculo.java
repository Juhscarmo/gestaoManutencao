package model;

public class Veiculo {

	//Atributos
	private String tipoVeiculo;
	private String placaVeiculo;
	
	//Getters e Setters
	public String getTipoVeiculo() {
		return tipoVeiculo;
	}
	public void setTipoVeiculo(String tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}
	public String getPlaca() {
		return placaVeiculo;
	}
	public void setPlaca(String placa) {
		this.placaVeiculo = placa;
	}
	
	//Para listar no JComboBox
	public String toString() {
		return placaVeiculo + " - " + tipoVeiculo;
	}
	
}
