package model;

public class Manutencao {
	
	//Atributos
	private String descricaoManutencao;
	private String dataManutencao;
	private String horaManutencao;
	private Veiculo veiculo;
	private Local local;
	private Usuario usuario;
	private boolean status;
	private int idManutencao;
	
	//Getters e Setters
	public String getDescricao() {
		return descricaoManutencao;
	}
	public void setDescricao(String descricao) {
		this.descricaoManutencao = descricao;
	}
	public String getDataManutencao() {
		return dataManutencao;
	}
	public void setDataManutencao(String dataManutencao) {
		this.dataManutencao = dataManutencao;
	}
	public String getHoraManutencao() {
		return horaManutencao;
	}
	public void setHoraManutencao(String horaManutencao) {
		this.horaManutencao = horaManutencao;
	}
	public Veiculo getVeiculo() {
		return veiculo;
	}
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	public Local getLocal() {
		return local;
	}
	public void setLocal(Local local) {
		this.local = local;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getIdManutencao() {
		return idManutencao;
	}
	public void setIdManutencao(int idManutencao) {
		this.idManutencao = idManutencao;
	}

}
